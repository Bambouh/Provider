package provider.model.sql;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import provider.uti.DeepCopy;
import provider.uti.FileHelper;
import provider.uti.IFileHelper;

public class SqlEngine implements ISqlEngine {
	
	private Map<String, IQuery> queriesMap = new HashMap<String, IQuery>();
	
	public SqlEngine(SqlEngine sqlEngine) {
		queriesMap = DeepCopy.copy(sqlEngine.getQueriesMap());
		queriesMap = Collections.unmodifiableMap(queriesMap);
	}
	
	public SqlEngine() {
		//Init engine
		URL url = this.getClass().getResource("Object.class");
		File baseDir = new File(url.getPath());
		if(!baseDir.isDirectory()) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "THe provided path where there shoud " +
					"be the SQL files is wrong : " + baseDir);
			return;
		}
		
		//Retrieving files
		List<File> files = new ArrayList<File>();
		for(File file : baseDir.listFiles()) {
			String[] nameArr = file.getName().split(".");
			if(file.isFile() && nameArr[nameArr.length-1].equals(SQL_FILE_SUFFIX)) {
				files.add(file);			
			}
		}
		if(files.size() == 0) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "No sql file found");
			return;
		}
		
		//Retrieving lines from files
		List<String> lines = new ArrayList<String>();
		IFileHelper fileHelper = FileHelper.INSTANCE;
		for(File file : files) {
			lines.addAll(fileHelper.getLines(file));
		}
		
		//Retrieving IQueries from lines and feed the queriesMap
		Pattern nameLinePattern = Pattern.compile("^" + NAME_LINE_START);
		StringBuilder queryStr = new StringBuilder();
		String queryName = null;
		lines.add(NAME_LINE_START); //last query is forgotten, so we add a last useless one at the end, so it does not matter  :)
		for(String line : lines) {
			Matcher nameLineMatcher = nameLinePattern.matcher(line);
			if(nameLineMatcher.find()) { //its a query name line, store previous query if exists and prepare new one
				if(queryStr.length() > 0) {
					IQuery query = null;
					query = new Query(queryName, queryStr.toString());
					if(query.getNbArgs() < 0) {
						Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "The query " + 
								query.getName() + " did not instanciate correctly");
					}
					if(queriesMap.containsKey(query.getName())) {
						Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "A query called " + 
								query.getName() + " is not unique. The first query is going to be erased");
					}
					queriesMap.put(query.getName(), query);
				}
				queryStr = new StringBuilder();
				queryName = line.replace("^" + NAME_LINE_START, "");
				
			} else { 
				queryStr.append(line);
			}
		}
		
		//Make it read only
		queriesMap = Collections.unmodifiableMap(queriesMap);
	}
	
	@Override
	public String getQuery(String queryName, String... parameters) {
		//Init and checks
		if(!queriesMap.containsKey(queryName)) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "The query " +
					queryName + " does not exist");
			return null;
		}
		IQuery query = queriesMap.get(queryName);
		if(query.getNbArgs() != parameters.length) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "The query " +
					queryName + " needs " + query.getNbArgs() + " arguments, you provide " +
					parameters.length);
			return null;
		}
		
		//placing arguments
		String queryStr = query.getQuery();
		for(int i=0 ; i<parameters.length ; i++) {
			String argName = ISqlEngine.QUERY_ARGUMENT_START + i;
			if(!queryStr.contains(argName)) {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "The query " +
						queryName + " does not contains the argument : " + argName);
				return null;
			}
			queryStr = queryStr.replace(argName, parameters[i]);
		}
		return queryStr;
	}
 
	private Map<String, IQuery> getQueriesMap() {
		return queriesMap;
	}
	
}

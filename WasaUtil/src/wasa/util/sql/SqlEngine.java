package wasa.util.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import wasa.util.date.DateFormat;
import wasa.util.date.IDateFormat;
import wasa.util.file.DeepCopy;
import wasa.util.file.FileHelper;
import wasa.util.file.IFileHelper;
import wasa.util.file.ILineFilter;

public class SqlEngine implements ISqlEngine {
	
	private static final String DATE_FORMAT_SQL = "YYYYMMDDHH24MISS";
	
	private static IDateFormat dateFormat = DateFormat.FORMAT_2;
	
	private Map<String, IQuery> queriesMap = new HashMap<String, IQuery>();
	private String sqlDirectoryPath;
	
	public SqlEngine(SqlEngine sqlEngine) {
		queriesMap = DeepCopy.INSTANCE.copy(sqlEngine.getQueriesMap());
		queriesMap = Collections.unmodifiableMap(queriesMap);
		sqlDirectoryPath = sqlEngine.getSqlDirectoryPath();
	}
	
	public SqlEngine(File sqlDirectory) {
		//Init engine
		if(!sqlDirectory.isDirectory()) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "THe provided path where there shoud " +
					"be the SQL files is wrong : " + sqlDirectory);
			return;
		}
		sqlDirectoryPath = sqlDirectory.getAbsolutePath();
		
		//Retrieving files
		List<File> files = new ArrayList<File>();
		for(File file : sqlDirectory.listFiles()) {
			String fileName = file.getName();
			String[] nameArr = fileName.split("\\.");
			if(file.isFile() && nameArr[nameArr.length-1].equals(SQL_FILE_SUFFIX)) {
				files.add(file);
			}
		}
		if(files.size() == 0) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "No sql file found");
			return;
		}
		
		//Creates a filter for comments line
		ILineFilter commentFilter = new ILineFilter() {
			@Override
			public String filter(String line) {
				if(line.startsWith(COMMENT_LINE_START))
					return null;
				return line;
			}
		};
		
		//Retrieving lines from files (comment lines are ignored)
		List<String> lines = new ArrayList<String>();
		IFileHelper fileHelper = FileHelper.INSTANCE;
		for(File file : files) {
			lines.addAll(fileHelper.getLines(file, commentFilter));
		}
		
		//Retrieving IQueries from lines and feed the queriesMap
		StringBuilder queryStr = new StringBuilder();
		String queryName = null;
		lines.add(NAME_LINE_START); //last query is forgotten, so we add a last useless one at the end, so it does not matter  :)
		for(String line : lines) {
			if(line.contains(NAME_LINE_START)) { //its a query name line, store previous query if exists and prepare new one
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
				queryName = line.replace(NAME_LINE_START, "");
				queryStr = new StringBuilder();
				
			} else { 
				queryStr.append(line);
			}
		}
		
		//Make it read only
		queriesMap = Collections.unmodifiableMap(queriesMap);
	}
	
	@Override
	public String getQuery(String name, Object... parameters) {
		//Init and checks
		String queryName = name.trim().toUpperCase();
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
			queryStr = queryStr.replace(argName, asString(parameters[i]));
		}
		return queryStr;
	}
 
	private Map<String, IQuery> getQueriesMap() {
		return queriesMap;
	}

	@Override
	public String getSqlDirectoryPath() {
		return sqlDirectoryPath;
	}
	
	private static String asString(Object obj) {
		if(obj instanceof Object[]) {
			StringBuilder strB = new StringBuilder();
			for(Object subObj : (Object[])obj) {
				strB.append(asString(subObj));
				strB.append(",");
			}
			return strB.substring(0, strB.length()-1);
		
		} else if(obj instanceof Date) {
			return "TO_DATE('"+dateFormat.getString((Date)obj)+"', '"
					+DATE_FORMAT_SQL+"')";
		
		} else if(obj instanceof String) {
			return "'"+(String)obj+"'";
		
		} else if(obj instanceof Integer) {
			return String.valueOf((Integer)obj);
		
		} else if(obj instanceof Double) {
			return String.valueOf((Double)obj);
		
		} else if(obj instanceof Long) {
			return String.valueOf((Long)obj);
		
		} else if(obj instanceof Float) {
			return String.valueOf((Float)obj);
		}
		return null;
	}
}

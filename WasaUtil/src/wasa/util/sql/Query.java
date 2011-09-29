package wasa.util.sql;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query implements IQuery {

	private static final String ARG_PATTERN = ISqlEngine.QUERY_ARGUMENT_START + "[0-9]+";
	
	String name, query;
	int nbArgs;
	
	public Query(String name, String query, int nbArgs) {
		this.name = name.trim().toUpperCase();
		this.query = query.trim().toUpperCase();
		this.nbArgs = nbArgs;
	}
	
	public Query(String name, String query) {
		this.name = name.trim().toUpperCase();
		this.query = query.trim().toUpperCase();
		this.nbArgs = getNbArgs(query);
		if(this.nbArgs == -1) { //the query is wrong
			Logger.getAnonymousLogger().log(Level.SEVERE, "Wrong query : " + name);
			name = null;
			query = null;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public int getNbArgs() {
		return nbArgs;
	}

	void setName(String name) {
		this.name = name.trim().toUpperCase();
	}

	void setQuery(String query) {
		this.query = query.trim().toUpperCase();
	}

	void setNbArgs(int nbArgs) {
		this.nbArgs = nbArgs;
	}
	
	/**
	 * Calculates number of arguments from the query str
	 * and makes minimum checks:
	 * arguments must follow each other in the right order:
	 * :0 , :1 , :2 ...
	 * @return number of args in the query, or -1 if problem
	 */
	private static int getNbArgs(String query) {
		Pattern argPattern = Pattern.compile(ARG_PATTERN);
		Matcher argMatcher = argPattern.matcher(query);
		int argCount = 0;
		while(argMatcher.find()) {
			String expectedName = ISqlEngine.QUERY_ARGUMENT_START+argCount;
			if(!argMatcher.group().equals(expectedName)) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "Wrong argument " + 
						"in the query : " + query);
				return -1;
			}
			argCount++;
		}
		return argCount;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

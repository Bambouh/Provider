package wasa.util.sql;

/**
 * Simple String container, when used as parameter in a SqlEngine.getQuery, it
 * is not processed, it will appear as it is in the query
 */
public class SQL {

	private String str;
	
	public SQL(String str) {
		this.str = str;
	}

	public String getString() {
		return str;
	}
}

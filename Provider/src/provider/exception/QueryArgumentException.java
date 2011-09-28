package provider.exception;

public class QueryArgumentException extends Exception {

	private static final long serialVersionUID = 9166255716526618503L;
	
	public QueryArgumentException(String query) {
		super("This query is funcked up : " + query);
	}
}

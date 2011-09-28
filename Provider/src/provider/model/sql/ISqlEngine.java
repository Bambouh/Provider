package provider.model.sql;

/**
 * Entry point to get the sql query stored in .SQL files
 * To access a query, you need to provide query name.
 * SQL files are automatically retrieved: all the .sql in 
 * the engine package will be processed
 * All names have to be unique through all the sql files.
 * 
 * A query looks like that:
 * '#Nom_requête  \n  SELECT * FROM DUAL WHERE ID = :0  \n'
 * it starts with 1 line for name declaration, then query goes, 
 * an empty line separates till the next query.
 * Parameters are specified using ':N', N starting from 0 (:0)
 * and incrementing one by one. 
 */
public interface ISqlEngine {

	static final String NAME_LINE_START = "#";
	static final String QUERY_ARGUMENT_START = ":";
	static final String SQL_FILE_SUFFIX = "sql";
	
	/**
	 * Retrieve and process a query from specified parameters. 
	 * @param queryName Name of the query
	 * @param parameters it has to match the number of parameters in the query
	 * @return the retrieved and processed query. Null if not found
	 */
	String getQuery(String queryName, String... parameters);
	
}

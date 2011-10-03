package provider.model.dao;

/**
 * Creation of tables, index etc if it does not exist yet
 */
public interface IInitSchemaDao {

	boolean initSchema();
	
}

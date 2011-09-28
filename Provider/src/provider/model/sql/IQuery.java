package provider.model.sql;

public interface IQuery {

	String getName();
	
	String getQuery();
	
	int getNbArgs();
	
}

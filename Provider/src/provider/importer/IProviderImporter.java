package provider.importer;

public interface IProviderImporter {
	
	boolean saveProvider();
	
	String getProviderName();
	
	String getProviderPath();
	
	int getProviderId();
	
}

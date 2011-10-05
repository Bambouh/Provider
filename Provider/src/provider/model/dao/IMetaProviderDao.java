package provider.model.dao;

import java.util.List;
import java.util.Map;

import provider.model.pojo.MetaProviderPojo;
import provider.model.pojo.ProviderPojo;

public interface IMetaProviderDao {
	
	List<String> getMetaProviderNames();
	
	Map<Integer, String> getMetaProviderNamesPerId();
	
	MetaProviderPojo getMetaProvider(int metaProviderId);

	boolean createMetaProvider(MetaProviderPojo metaProvider);
	
	boolean addChild(int metaProviderId, ProviderPojo childToAdd);

	boolean addChild(MetaProviderPojo metaProvider, ProviderPojo childToAdd);
	
	boolean removeChild(int metaProviderId, ProviderPojo childToRemove);
		
	boolean removeChild(MetaProviderPojo metaProvider, ProviderPojo childToRemove);
	
}

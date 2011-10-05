package provider.model.dao;

import java.util.List;
import java.util.Map;

import provider.model.pojo.ProviderPojo;

public interface IProviderDao {

	ProviderPojo getProvider(int providerId);
	
	ProviderPojo getProvider(String providerName);
	
	List<String> getProviderNames();
	
	Map<Integer, String> getProviderNamesPerId();
	
	List<ProviderPojo> getProviders();
	
	boolean createProvider(ProviderPojo provider);
	
	boolean updateProvider(int providerId, ProviderPojo provider);
	
}

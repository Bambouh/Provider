package provider.model.dao;

import java.util.List;

import provider.model.pojo.BrokerPojo;
import provider.model.pojo.ProviderPojo;

public interface IBrokerDao {

	BrokerPojo getBroker(int providerId);
	
	Integer getBrokerId(String name);
	
	BrokerPojo getBroker(ProviderPojo provider);
	
	List<String> getBrokerNames();
	
	List<BrokerPojo> getBrokers();
	
	/**
	 * Saves the broker if it does not exist. Then make update broker parameter
	 * ID with newly created broker ID or existing broker with name = broker.name. 
	 * @param broker
	 * @return
	 */
	boolean save(BrokerPojo broker);
	
}

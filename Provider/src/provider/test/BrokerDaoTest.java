package provider.test;

import java.util.List;

import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.IBrokerDao;
import provider.model.dao.InitSchemaDao;
import provider.model.dao.impl.BrokerDao;
import provider.model.pojo.BrokerPojo;
import junit.framework.TestCase;

public class BrokerDaoTest extends TestCase {
	
	private IProviderManager providerManager;
	private IBrokerDao brokerDao;
	
	public BrokerDaoTest() {
		providerManager = new ProviderManagerForTestPurpose();
		InitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
		brokerDao = new BrokerDao(providerManager);
	}
	
	@Test
	public void testBroker() {
		boolean ok = true;
		
		//test insert
		BrokerPojo aaaFx = new BrokerPojo(-1, "AAAFX");
		BrokerPojo aaaFx2 = new BrokerPojo(-1, "AAAFX");
		BrokerPojo alpariRu = new BrokerPojo(-1, "Alpari RU");
		
		ok = brokerDao.saveBroker(aaaFx);
		ok = ok && brokerDao.saveBroker(alpariRu);
		ok = ok && brokerDao.saveBroker(aaaFx2);
		
		assertTrue("brokerDao did not manage to save brokers properly", ok);
		
		assertTrue("brokerDao did not update ID after saving", 
				aaaFx.getId() > 0 && alpariRu.getId() > 0);
		
		assertTrue("brokerDao did not manage to make broker name unique", 
				aaaFx.getId() == aaaFx2.getId());
	}
	
	@Test
	public void testSelect() {
		
		//getBrokerNames
		List<String> brokerNames = brokerDao.getBrokerNames();
		assertTrue("Names of all brokers retrieval failed", brokerNames.size() > 0);
		assertTrue("Names of all brokers retrieval failed", brokerNames.contains("AAAFX"));
		assertTrue("Names of all brokers retrieval failed", brokerNames.contains("Alpari RU"));
		
		//getBrokers
		List<BrokerPojo> brokers = brokerDao.getBrokers();
		assertTrue("All brokers retrieval failed", brokers.size() > 0);
		BrokerPojo broker1 = brokers.get(0);
		assertTrue("All brokers retrieval failed", broker1.getId() == 1);
		assertTrue("All brokers retrieval failed", broker1.getName().equals("AAAFX"));
		
	}
	
}

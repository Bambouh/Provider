package provider.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.IInitSchemaDao;
import provider.model.dao.IProviderDao;
import provider.model.dao.impl.InitSchemaDao;
import provider.model.dao.impl.ProviderDao;
import provider.model.pojo.ProviderPojo;
import junit.framework.TestCase;

public class ProviderDaoTest extends TestCase {

	private IProviderDao providerDao;
	
	public ProviderDaoTest() {
		IProviderManager providerManager = new ProviderManagerForTestPurpose();
		providerDao = new ProviderDao(providerManager);
		IInitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
	}
	
	@Test
	public void testInsertRetrieveSingle() {
		ProviderPojo provider = new ProviderPojo(4, 1, "CocaCola_provider", AllTests.date("2011/04/01 09:04:06:669"), 
				AllTests.date("2011/10/08 18:00:06:481"), AllTests.date("2011/10/08 20:45:33:217"));
		
		assertTrue(providerDao.createProvider(provider));
		
		ProviderPojo sameProvider = providerDao.getProvider(provider.getId());
		
		assertTrue(sameProvider.getName().equals("CocaCola_provider"));
		assertTrue(DateUtils.isSameInstant(sameProvider.getStartDate(), AllTests.date("2011/04/01 09:04:06:669")));
		assertTrue(DateUtils.isSameInstant(sameProvider.getEndDate(), AllTests.date("2011/10/08 18:00:06:481")));
		assertTrue(DateUtils.isSameInstant(sameProvider.getLastUpdate(), AllTests.date("2011/10/08 20:45:33:217")));
		
		//Testing lastUpdate modification
		sameProvider.setLastUpdate(AllTests.date("2011/11/11 11:11:11:241"));
		providerDao.updateProviderLastUpdate(sameProvider.getId(), sameProvider);
		assertTrue(DateUtils.isSameInstant(sameProvider.getLastUpdate(), AllTests.date("2011/11/11 11:11:11:241")));
		ProviderPojo againProvider = providerDao.getProvider(sameProvider.getId());
		assertTrue(DateUtils.isSameInstant(againProvider.getLastUpdate(), AllTests.date("2011/11/11 11:11:11:241")));
	}
	
	@Test
	public void testInsertRetrievalMulti() {
		List<ProviderPojo> providers = new ArrayList<ProviderPojo>();
		providers.add(new ProviderPojo(1, 1, "Super scoot 47", AllTests.date("2010/04/01 09:04:06:669"), 
				AllTests.date("2011/10/07 18:00:45:481"), AllTests.date("2011/10/8 17:45:33:217")));
		providers.add(new ProviderPojo(4, 1, "CocaCola_provider", AllTests.date("2011/04/01 09:04:06:669"), 
					AllTests.date("2011/10/08 18:00:06:481"), AllTests.date("2011/10/08 20:45:33:217")));
		
		assertTrue(providerDao.createProvider(providers.get(0)));
		assertTrue(providerDao.createProvider(providers.get(1)));
		
		//Test get provider names
		List<String> names = providerDao.getProviderNames();
		assertTrue(names.get(0).equals("CocaCola_provider"));
		assertTrue(names.get(1).equals("Super scoot 47"));
		
		//Test 1 by name
		ProviderPojo sameProvider = providerDao.getProvider(providers.get(0).getName());
		assertTrue(sameProvider.getName().equals("Super scoot 47"));
		
		//Names per ID
		Map<Integer, String> providerNamesPerId = providerDao.getProviderNamesPerId();
		assertTrue(providerNamesPerId.get(2).equals("Super scoot 47"));
		assertTrue(providerNamesPerId.get(1).equals("CocaCola_provider"));
		
		//Get providers
		List<ProviderPojo> providers1 = providerDao.getProviders();
		Map<String, ProviderPojo> providerPerNameSame = new HashMap<String, ProviderPojo>();
		for(ProviderPojo prov : providers1) {
			providerPerNameSame.put(prov.getName(), prov);
		}
		Map<String, ProviderPojo> providerPerName = new HashMap<String, ProviderPojo>();
		for(ProviderPojo prov : providers) {
			providerPerName.put(prov.getName(), prov);
		}
		
		for(String name : providerDao.getProviderNames()) {
			assertTrue(isEqual(providerPerName.get(name), providerPerNameSame.get(name)));
		}
		
		//Test provider update
		ProviderPojo provider2 = providerDao.getProvider("CocaCola_provider");
		provider2.setBrokerId(7);
		provider2.setLinkId(9);
		provider2.setStartDate(AllTests.date("2010/04/14 19:24:09:053"));
		provider2.setEndDate(AllTests.date("2011/07/14 19:24:09:053"));
		provider2.setLastUpdate(AllTests.date("2011/10/09 14:55:55:001"));
		providerDao.updateProvider(provider2);
		
		ProviderPojo provider3 = providerDao.getProvider("CocaCola_provider");
		assertTrue(isEqual(provider2, provider3));
		
		assertTrue(provider3.getId() == provider2.getId());
		assertTrue(provider3.getBrokerId() == 7);
		assertTrue(provider3.getLinkId() == 9);
		assertTrue(DateUtils.isSameInstant(provider3.getStartDate(), AllTests.date("2010/04/14 19:24:09:053")));
		assertTrue(DateUtils.isSameInstant(provider3.getEndDate(), AllTests.date("2011/07/14 19:24:09:053")));
		assertTrue(DateUtils.isSameInstant(provider3.getLastUpdate(), AllTests.date("2011/10/09 14:55:55:001")));
		
		//test provider delete
		providerDao.deleteProvider(provider3.getId());
		Integer deletedProviderId = providerDao.getProviderId("CocaCola_provider");
		assertTrue(deletedProviderId == null);
		
	}
	
	public boolean isEqual(ProviderPojo first, ProviderPojo second) {
		boolean same = true;
		
		same = same && (first.getName().equals(second.getName()));
		same = same && (DateUtils.isSameInstant(first.getStartDate(), second.getStartDate()));	// 1301641446669 -> 1301641446669
		same = same && (DateUtils.isSameInstant(first.getEndDate(), second.getEndDate()));		
		//same = same && (DateUtils.isSameInstant(first.getLastUpdate(), second.getLastUpdate()));
		same = same && (first.getLinkId() == second.getLinkId());
		same = same && (first.getBrokerId() == second.getBrokerId());
		
		return same;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

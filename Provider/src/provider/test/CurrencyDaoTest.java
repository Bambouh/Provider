package provider.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.ICurrencyDao;
import provider.model.dao.InitSchemaDao;
import provider.model.dao.impl.CurrencyDao;
import provider.model.pojo.CurrencyPojo;
import junit.framework.TestCase;

public class CurrencyDaoTest extends TestCase {

	private IProviderManager providerManager;
	private ICurrencyDao currencyDao;
	
	public CurrencyDaoTest() {
		providerManager = new ProviderManagerForTestPurpose();
		InitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
		currencyDao = new CurrencyDao(providerManager);
	}
	
	@Test
	public void testSaveCurrency() {
		CurrencyPojo eurUsd = new CurrencyPojo(-1, "EUR", "USD");
		CurrencyPojo gbpJpy = new CurrencyPojo(-1, "GBP/JPY");
		CurrencyPojo usdCad = new CurrencyPojo(-1, "USD", "CAD");
		
		currencyDao.saveCurrency(eurUsd);
		assertTrue(eurUsd.getId() == 1);
		
		List<CurrencyPojo> currencies = new ArrayList<CurrencyPojo>();
		currencies.add(eurUsd);
		currencies.add(gbpJpy);
		currencies.add(usdCad);
		
		boolean ok = currencyDao.saveCurrencies(currencies);
		assertTrue(ok);
		assertTrue(eurUsd.getId() == 1);
		assertTrue(gbpJpy.getId() == 2);
		assertTrue(usdCad.getId() == 3);
		
	}
}

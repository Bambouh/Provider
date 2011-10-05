package provider.test;

import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.IInitSchemaDao;
import provider.model.dao.InitSchemaDao;

import junit.framework.TestCase;

public class InitSchemaDaoTest extends TestCase {

	@Test
	public void testInitSchema() {
		IProviderManager providerManager = new ProviderManagerForTestPurpose();
		IInitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		assertTrue("Initializing empty database failed", initSchemaDao.initSchema());
		assertTrue("Initializing already initialized database failed", initSchemaDao.initSchema());
	}
}

package provider.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		InitSchemaDaoTest.class,
		BrokerDaoTest.class,
		CurrencyDaoTest.class
})
public class AllTests {

}

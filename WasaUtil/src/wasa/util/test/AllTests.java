package wasa.util.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		DeepCopyTest.class,
		UniqueDateFactoryTest.class,
		FileHelperTest.class,
		SqlEngineTest.class,
		CacheHelperTest.class
})
public class AllTests {
	
}

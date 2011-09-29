package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		DeepCopyTest.class,
		UniqueDateFactoryTest.class,
		FileHelperTest.class,
		SqlEngineTest.class
})
public class AllTests {
	
}

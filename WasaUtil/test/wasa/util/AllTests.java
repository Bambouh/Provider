package wasa.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		DeepCopyTest.class,
		UniqueDateFactoryTest.class,
})
public class AllTests {
	
}

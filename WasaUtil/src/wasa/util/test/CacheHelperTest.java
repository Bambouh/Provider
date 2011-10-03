package wasa.util.test;

import java.util.Map;

import org.junit.Test;

import wasa.util.cache.CacheHelper;
import junit.framework.TestCase;

public class CacheHelperTest extends TestCase {

	@Test
	public void testLruCache() {
		CacheHelper cacheHelper = CacheHelper.LRU;
		Map<Integer, String> cache = cacheHelper.getNewCacheMap(
				new Integer(0), new String(), 10);
		
		//we fill it up with 100 records, it should keep only the 10 least recently used
		for(int i=0 ; i<100 ; i++) {
			cache.put(i, "Record number "+i);
		}
		assertTrue("the LRU cache does not fullfill size constraint", cache.size() == 10);
	}
	
}

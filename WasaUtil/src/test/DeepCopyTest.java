package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import wasa.util.file.DeepCopy;

import junit.framework.TestCase;

@SuppressWarnings("unused")
public class DeepCopyTest extends TestCase {
	
	@Test
	public void testDeepCopyForMap() {
		Map<String, Integer> sourceMap = new HashMap<String, Integer>();
		sourceMap.put("LOL", 4);
		sourceMap.put("TEST", 7);
		sourceMap.put("Gijou", 1);
		sourceMap.put("carrosse", 2);
		sourceMap.put("Frankfurt", 8);
		sourceMap.put("Paris", 11);
		sourceMap.put("abstract", 111);
		
		Map<String, Integer> destMap = DeepCopy.INSTANCE.copy(sourceMap);
		
		assertTrue("source and dest maps don't have same size", 
				sourceMap.size() == destMap.size());
		
		for(String key : sourceMap.keySet()) {
			assertTrue("a key returned wrong value", 
					sourceMap.get(key).equals(destMap.get(key)));
		}
		
		sourceMap.put("LOL", 44);
		sourceMap.put("TEST", 77);
		sourceMap.put("Gijou", 11);
		sourceMap.put("carrosse", 25);
		sourceMap.put("Frankfurt", 82);
		sourceMap.put("Paris", 1);
		sourceMap.put("abstract", -14);
		
		for(String key : sourceMap.keySet()) {
			assertFalse("not a deep copy, only shallow has been done", 
					sourceMap.get(key).equals(destMap.get(key)));
		}
	}

	@Test
	public void testDeepCopyForSortedSet() {
		SortedSet<Integer> sourceSet = new TreeSet<Integer>();
		sourceSet.add(1);
		sourceSet.add(2);
		sourceSet.add(3);
		sourceSet.add(4);
		sourceSet.add(5);
		sourceSet.add(6);
		sourceSet.add(7);
		
		SortedSet<Integer> destSet = DeepCopy.INSTANCE.copy(sourceSet);
		
		assertTrue("source and dest maps don't have same size", 
				sourceSet.size() == destSet.size());
		
		for(Integer key : sourceSet) {
			assertTrue("a key in has been lost", 
					destSet.contains(key));
		}
		
		sourceSet.clear();
		sourceSet.add(44);
		sourceSet.add(77);
		sourceSet.add(11);
		sourceSet.add(25);
		sourceSet.add(82);
		sourceSet.add(101);
		sourceSet.add(-14);
		
		for(Integer key : sourceSet) {
			assertFalse("not a deep copy, only shallow has been done", 
					destSet.contains(key));
		}
		
		int size1 = destSet.size();
		try {
			destSet.remove(1);
		} catch (Exception e) {
			assertTrue("cannot remove elements (" + e + ")", false);
		}
		int size2 = destSet.size();
		assertTrue("element removal failed", size2+1 == size1);
	}
}

package wasa.util.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings("unchecked")
public enum DeepCopy {

	INSTANCE;
	
	public <K,V> Map<K, V> copy(Map<K, V> map) {
		K[] keys = (K[])new Object[map.size()];
		System.arraycopy(map.keySet().toArray(), 0, keys, 0, map.size());
		V[] values = (V[])new Object[map.size()];
		System.arraycopy(map.values().toArray(), 0, values, 0, map.size());
		
		Map<K,V> deepCopy = new HashMap<K, V>(map.size());
		for(int i=0 ; i<map.size() ; i++) {
			deepCopy.put(keys[i], values[i]);
		}
        return deepCopy;
	}
	
	public <K> SortedSet<K> copy(SortedSet<K> set) {
		K[] destArr = (K[])new Object[set.size()];
		System.arraycopy(set.toArray((K[])new Object[set.size()]), 0, destArr, 0, set.size());
		
		SortedSet<K> dest = new TreeSet<K>(Arrays.asList(destArr));

		return dest;
	}
}

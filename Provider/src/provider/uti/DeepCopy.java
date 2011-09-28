package provider.uti;

import java.util.HashMap;
import java.util.Map;

public class DeepCopy {

	@SuppressWarnings("unchecked")
	public static <K,V> Map<K, V> copy(Map<K, V> map) {
		Object[] keys = new Object[map.size()];
		System.arraycopy(map.keySet().toArray(), 0, keys, 0, map.size());
		Object[] values = new Object[map.size()];
		System.arraycopy(map.values().toArray(), 0, values, 0, map.size());
		
		Map<K,V> deepCopy = new HashMap<K, V>(map.size());
		for(int i=0 ; i<map.size() ; i++) {
			deepCopy.put((K)keys[i], (V)values[i]);
		}
        return deepCopy;
	}
}

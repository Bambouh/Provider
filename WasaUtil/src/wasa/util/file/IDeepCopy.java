package wasa.util.file;

import java.util.Map;
import java.util.SortedSet;

public interface IDeepCopy {

	<K,V> Map<K, V> copy(Map<K, V> map);
	
	<K> SortedSet<K> copy(SortedSet<K> set);
}

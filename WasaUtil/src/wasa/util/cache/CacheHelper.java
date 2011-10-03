package wasa.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides different collections that are useful as cache 
 */
public enum CacheHelper {

	/**
	 * Least recently used cache
	 */
	LRU;
	
	public <K, V> Map<K, V> getNewCacheMap(K k, V v, int maxSize) {
		switch (this) {
			case LRU : return new LruCacheMap<K, V>(maxSize);
		}
		return null;
	}
	
	private class LruCacheMap<K, V> extends LinkedHashMap<K, V> {
		
		private static final long serialVersionUID = 7624970222588663849L;
		private int maxSize;
		
		public LruCacheMap(int maxSize) {
			super(maxSize);
			this.maxSize = maxSize;
		}
		
		@Override
		protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		    return size() > maxSize;
		}
	}
}

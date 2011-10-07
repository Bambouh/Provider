package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface IResultsetHelper {

	Integer extractInt(ResultSet res);
	
	Float extractFloat(ResultSet res);
	
	String extractString(ResultSet res);
	
	<K, V> Map<K, V> extractMap(ResultSet res, K k, V v);
	
	<K> List<K> extractList(ResultSet res, K k);
	
	<K> SortedSet<K> extractSortedSet(ResultSet res, K k);
	
	<K> SortedSet<K> extractSortedSet(ResultSet res, K k, Comparator<K> comparator);
	
}

package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.util.Map;

public interface IResultsetHelper {

	Integer extractInt(ResultSet res);
	
	Float extractFloat(ResultSet res);
	
	String extractString(ResultSet res);
	
	<K, V> Map<K, V> extractMap(ResultSet res, K k, V v);
}

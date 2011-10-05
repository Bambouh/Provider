package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum ResultsetHelper implements IResultsetHelper {
	
	INSTANCE;

	@Override
	public Integer extractInt(ResultSet res) {
		Integer ret = null;
		try {
			if(res.next()) {
				ret = res.getInt(1);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building Integer", e);
		}
		return ret;
	}

	@Override
	public Float extractFloat(ResultSet res) {
		Float ret = null;
		try {
			if(res.next()) {
				ret = res.getFloat(1);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building Float", e);
		}
		return ret;
	}

	@Override
	public String extractString(ResultSet res) {
		String ret = null;
		try {
			if(res.next()) {
				ret = res.getString(1);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building String", e);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Map<K, V> extractMap(ResultSet res, K key, V val) {
		Map<K, V> map = new HashMap<K, V>();
		try {
			while(res.next()) {
				key = (K)res.getObject(1);
				val = (V)res.getObject(2);
				map.put(key, val);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building Map using generics", e);
		}
		
		return map;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

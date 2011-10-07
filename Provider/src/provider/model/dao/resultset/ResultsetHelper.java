package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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

	@SuppressWarnings("unchecked")
	@Override
	public <K> List<K> extractList(ResultSet res, K val) {
		List<K> list = new ArrayList<K>();
		try {
			while(res.next()) {
				val = (K)res.getObject(1);
				list.add(val);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building List using generics", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> SortedSet<K> extractSortedSet(ResultSet res, K val) {
		SortedSet<K> set = new TreeSet<K>();
		try {
			while(res.next()) {
				val = (K)res.getObject(1);
				set.add(val);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building List using generics", e);
		}
		return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> SortedSet<K> extractSortedSet(ResultSet res, K val, Comparator<K> comparator) {
		SortedSet<K> set = new TreeSet<K>(comparator);
		try {
			while(res.next()) {
				val = (K)res.getObject(1);
				set.add(val);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"error occured while building List using generics", e);
		}
		return set;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

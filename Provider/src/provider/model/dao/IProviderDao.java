package provider.model.dao;

import java.util.SortedMap;
import java.util.SortedSet;

import provider.model.pojo.TradePojo;

public interface IProviderDao {

	TradePojo getTrade(int id);
	
	SortedSet<TradePojo> getTrades();
	
	SortedMap<Object, Object> getTradeFieldPair();
	
}

package provider.model.dao;

import java.util.List;
import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeExtPojo;

public interface ITradeExtDao {

	TradeExtPojo getTrade(int tradeId);
	
	SortedSet<TradeExtPojo> getTrades(int providerId);
	
	SortedSet<TradeExtPojo> getTrades(ProviderPojo provider);
	
	boolean create(TradeExtPojo tradeExt);
	
	boolean create(List<TradeExtPojo> tradesExt);
	
	boolean update(int tradeId, TradeExtPojo tradeExt);
	
}

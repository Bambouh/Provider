package provider.model.dao;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeExtPojo;

public interface ITradeExtDao {

	TradeExtPojo getTradeExt(int tradeId);
	
	Integer getTradeId(Date startDate);
	
	SortedSet<TradeExtPojo> getTradesExt(ProviderPojo provider);
	
	SortedSet<TradeExtPojo> getTradesExt(int providerId);
	
	boolean save(TradeExtPojo tradeExt);
	
	boolean save(List<TradeExtPojo> tradesExt);
	
	boolean deleteByProvider(int providerId);
	
	boolean deleteByProvider(ProviderPojo provider);
	
}

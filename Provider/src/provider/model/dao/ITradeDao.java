package provider.model.dao;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradePojo;

/**
 * List of trades are always given sorted by startDate, from oldest to newest
 * @author David
 *
 */
public interface ITradeDao {

	TradePojo getTrade(int tradeId);
	
	int getTradeId(Date startDate);
	
	SortedSet<TradePojo> getTrades(int providerId);
	
	SortedSet<TradePojo> getTrades(ProviderPojo provider);
	
	boolean createTrade(TradePojo trade);
	
	boolean createTrades(SortedSet<TradePojo> trades);
	
	boolean createTrades(List<Integer> tradeIds);
	
	boolean updateTrade(int tradeId, TradePojo trade);

}

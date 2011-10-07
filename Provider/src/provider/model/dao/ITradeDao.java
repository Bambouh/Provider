package provider.model.dao;

import java.util.Date;
import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradePojo;

/**
 * List of trades are always given sorted by startDate, from oldest to newest
 * No Write methods here, as trade is linked to trade_ext: tradeExtDao is going
 * to make the inserts.
 * The read are faster here, as there is no join table and only light datas are
 * stored into trade table. trade_ext table contains heavier and less useful things
 *
 */
public interface ITradeDao {

	TradePojo getTrade(int tradeId);
	
	int getTradeId(Date startDate);
	
	SortedSet<TradePojo> getTradesPerProvider(int providerId);
	
	SortedSet<TradePojo> getTradesPerProvider(ProviderPojo provider);
	
}

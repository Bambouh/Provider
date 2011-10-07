package provider.model.dao;

import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeMovementPojo;
import provider.model.pojo.TradePojo;

public interface ITradeMovementDao {

	TradeMovementPojo getMovement(int movementId);
	
	SortedSet<TradeMovementPojo> getMovementsPerTrade(int tradeId);
	
	SortedSet<TradeMovementPojo> getMovementsPerTrade(TradePojo tade);
	
	SortedSet<TradeMovementPojo> getMovementsPerProvider(int providerId);
	
	SortedSet<TradeMovementPojo> getMovementsPerProvider(ProviderPojo provider);
	
	boolean createTradeMovement(TradeMovementPojo tradeMovement);
	
	boolean createTradeMovements(SortedSet<TradeMovementPojo> tradeMovements);
	
	boolean createTradeMovementFromTrade(TradePojo trade);
	
	boolean createTradeMovementsFromTrades(SortedSet<TradePojo> trades);
	
	boolean updateTradeMovement(int tradeMovementId, TradeMovementPojo tradeMovement);
	
}

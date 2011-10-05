package provider.model.dao;

import java.util.SortedSet;

import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeMovementPojo;

public interface ITradeMovementDao {

	TradeMovementPojo getMovement(int movementId);
	
	SortedSet<TradeMovementPojo> getMovements(int providerId);
	
	SortedSet<TradeMovementPojo> getMovements(ProviderPojo provider);
	
	boolean createTradeMovement(TradeMovementPojo tradeMovement);
	
	boolean createTradeMovements(SortedSet<TradeMovementPojo> tradeMovements);
	
	boolean updateTradeMovement(int tradeMovementId, TradeMovementPojo tradeMovement);
	
}

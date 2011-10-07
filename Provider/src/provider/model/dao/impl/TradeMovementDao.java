package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.ITradeMovementDao;
import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeMovementPojo;
import provider.model.pojo.TradePojo;

public class TradeMovementDao extends Dao implements ITradeMovementDao {

	private Statement statement;
	
	protected TradeMovementDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error while creating TradeMovementDao");
		}
	}

	@Override
	public TradeMovementPojo getMovement(int movementId) {
		String query = getFilledQuery("provider.trade_movement.select_one_per_id", movementId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error retrieving one movement from it's ID");
		}
		return getProviderResultsetHelper().extractTradeMovement(res);
	}
	
	@Override
	public SortedSet<TradeMovementPojo> getMovementsPerTrade(int tradeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<TradeMovementPojo> getMovementsPerTrade(TradePojo tade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<TradeMovementPojo> getMovementsPerProvider(int providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<TradeMovementPojo> getMovementsPerProvider(
			ProviderPojo provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createTradeMovement(TradeMovementPojo tradeMovement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTradeMovements(
			SortedSet<TradeMovementPojo> tradeMovements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTradeMovementFromTrade(TradePojo trade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTradeMovementsFromTrades(SortedSet<TradePojo> trades) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTradeMovement(int tradeMovementId,
			TradeMovementPojo tradeMovement) {
		// TODO Auto-generated method stub
		return false;
	}



}

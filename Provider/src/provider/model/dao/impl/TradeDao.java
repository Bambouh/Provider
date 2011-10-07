package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.ITradeDao;
import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradePojo;

public class TradeDao extends Dao implements ITradeDao {

	protected Statement statement;
	
	protected TradeDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error while creating TradeDao");
		}
	}

	@Override
	public TradePojo getTrade(int tradeId) {
		String query = getFilledQuery("provider.trade.select_one", tradeId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error while retreaving trade with id = " + tradeId);
		}
		return getProviderResultsetHelper().extractTrade(res);
	}

	@Override
	public int getTradeId(Date startDate) {
		String query = getFilledQuery("provider.trade.select_one_id_from_startdate", startDate);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error while retreaving trade from a startdate");
		}
		return getResultsetHelper().extractInt(res);
	}

	@Override
	public SortedSet<TradePojo> getTradesPerProvider(ProviderPojo provider) {
		return getTradesPerProvider(provider.getId());
	}
	
	@Override
	public SortedSet<TradePojo> getTradesPerProvider(int providerId) {
		String query = getFilledQuery("provider.trade.select_all_per_provider", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error while retreaving all trades from a provider (provider_id="+providerId+")");
		}
		SortedSet<TradePojo> trades = new TreeSet<TradePojo>();
		TradePojo trade = null;
		while((trade = getProviderResultsetHelper().extractTrade(res)) != null)
			trades.add(trade);
		
		return trades;
	}	
		
}

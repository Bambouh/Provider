package provider.model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.ITradeExtDao;
import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeExtPojo;
import wasa.util.date.IUniqueDateFactory;

public class TradeExtDao extends Dao implements ITradeExtDao {

	private Statement statement;
	private IUniqueDateFactory uniqueDateFactory;
	
	public TradeExtDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while creating TradeExtDao", e);
		}
		uniqueDateFactory = providerManager.getUniqueDateFactory();
	}

	@Override
	public TradeExtPojo getTradeExt(int tradeId) {
		String query = getFilledQuery("provider.trade_ext.select_one_per_id", tradeId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving TradeExt", e);
		}
		return getProviderResultsetHelper().extractTradeExt(res);
	}

	@Override
	public SortedSet<TradeExtPojo> getTradesExt(ProviderPojo provider) {
		return getTradesExt(provider.getId());
	}
	
	@Override
	public SortedSet<TradeExtPojo> getTradesExt(int providerId) {
		String query = getFilledQuery("provider.trade_ext.select_per_provider", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving TradesExt for providerid = " + providerId, e);
		}
		SortedSet<TradeExtPojo> tradesExt = new TreeSet<TradeExtPojo>();
		TradeExtPojo tradeExt = null;
		while((tradeExt = getProviderResultsetHelper().extractTradeExt(res)) != null)
			tradesExt.add(tradeExt);
		
		return tradesExt;
	}
	
	@Override
	public boolean save(TradeExtPojo tradeExt) {
		//Making sure dates are unique among all trades of all providers
		Date startDate = uniqueDateFactory.build(tradeExt.getStartDate());
		Date endDate = uniqueDateFactory.build(tradeExt.getEndDate());
		tradeExt.setStartDate(startDate);
		tradeExt.setEndDate(endDate);
		
		//Making trade query
		String queryTrade = getFilledQuery("provider.trade.insert_one", 
				tradeExt.getStartDate(), tradeExt.getEndDate(),
				tradeExt.getBestPips(), tradeExt.getWorstPips(),
				tradeExt.getNetPips(), tradeExt.getBestDollarLot(),
				tradeExt.getWorstDollarLot(), tradeExt.getNetDollarLot(),
				tradeExt.getCurrencyId(), tradeExt.getProviderId());
		
		int tradeId = -1;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			statement.execute(queryTrade);
			tradeId = getTradeId(tradeExt.getStartDate());
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving trade", e);
			rollback();
			close(statement);
			return false;
		}
		tradeExt.setId(tradeId);
		if(tradeExt.getId() <= 0) {
			close(statement);
			return false;
		}
		
		//Making tradeExt query
		String queryExt = getFilledQuery("provider.trade_ext.save_one", tradeExt.getId(), tradeExt.getTicket());
		try {
			statement.execute(queryExt);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving trade Extension", e);
			return false;
		}
		
		close(statement);
		return true;
	}

	@Override
	public boolean save(List<TradeExtPojo> tradesExt) {
		
		if(tradesExt.size() == 0)
			return true;
		
		//Making sure dates are unique among all trades of all providers
		for(int i=0; i<tradesExt.size() ; i++) {
			TradeExtPojo tradeExt = tradesExt.get(i);
			Date startDate = uniqueDateFactory.build(tradeExt.getStartDate());
			Date endDate = uniqueDateFactory.build(tradeExt.getEndDate());
			tradeExt.setStartDate(startDate);
			tradeExt.setEndDate(endDate);
		}
		
		//Making trade query
		String queryTrade = getQuery("provider.trade.insert_one");
		try {
			PreparedStatement statement = getConnection().prepareStatement(queryTrade);
			getConnection().setAutoCommit(false);
			
			for(TradeExtPojo tradeExt : tradesExt) {
				statement.setLong(1, tradeExt.getStartDate().getTime());
				statement.setLong(2, tradeExt.getEndDate().getTime());
				statement.setFloat(3, tradeExt.getBestPips());
				statement.setFloat(4, tradeExt.getWorstPips());
				statement.setFloat(5, tradeExt.getNetPips());
				statement.setFloat(6, tradeExt.getBestDollarLot());
				statement.setFloat(7, tradeExt.getWorstDollarLot());
				statement.setFloat(8, tradeExt.getNetDollarLot());
				statement.setInt(9, tradeExt.getCurrencyId());
				statement.setInt(10, tradeExt.getProviderId());	
				
				statement.addBatch();
			}
			statement.executeBatch();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to save trades");
			rollback();
			return false;
		}
		
		//Making tradeExt query
		String queryExt = getQuery("provider.trade_ext.insert_one_using_startdate");
		try {
			PreparedStatement statement = getConnection().prepareStatement(queryExt);
			getConnection().setAutoCommit(false);
			
			for(TradeExtPojo tradeExt : tradesExt) {
				statement.setString(1, tradeExt.getTicket());
				statement.setLong(2, tradeExt.getStartDate().getTime());
				
				statement.addBatch();
			}
			statement.executeBatch();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to save trades extensions");
			rollback();
			return false;
		}
		
		//Retrieving newly created trades ID

		//First, we get the first and last insered startDates, and look for corresponding ID
		Object[] startDates = new Date[2];
		startDates[0] = tradesExt.get(0).getStartDate();					//First start Date
		startDates[1] = tradesExt.get(tradesExt.size()-1).getStartDate();	//Last start Date
		
		String retrieveIdPerStartDate = getFilledQuery("provider.trade.select_all_id_per_startdate", startDates);
		Map<Long, Integer> resMap = null;
		try {
			ResultSet res = statement.executeQuery(retrieveIdPerStartDate);
			resMap = getResultsetHelper().extractMap(res, new Long(0), new Integer(0));
		
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to retrieved newly created trades Ids");
		}
		//Checking if first and last created ID are correctly retrieved
		if(resMap == null || resMap.size() != 2 || 
				!resMap.containsKey(((Date)startDates[0]).getTime()) || 
				!resMap.containsKey(((Date)startDates[1]).getTime())) {
			
			Logger.getLogger(this.getClass().getName())
			.log(Level.SEVERE, "Unable to retrieved newly created trades Ids");
			return false;
		}
		Integer firstCreatedId = resMap.get(((Date)startDates[0]).getTime());
		Integer lastCreatedId = resMap.get(((Date)startDates[1]).getTime());
		
		if(lastCreatedId - firstCreatedId != tradesExt.size()-1) {
			Logger.getLogger(this.getClass().getName())
			.log(Level.SEVERE, "Unable to retrieved newly created trades Ids");
			return false;
		}
		//Now we can retrieve all the IDs per startDate timestamp
		Map<Long, Integer> idPerStartTimestamp = new HashMap<Long, Integer>();
		String query = getFilledQuery("provider.trade.select_ids_in_id_interval", firstCreatedId, lastCreatedId);
		try {
			ResultSet res = statement.executeQuery(query);
			idPerStartTimestamp = getResultsetHelper().extractMap(res, new Long(0), new Integer(0));
		
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to retrieved newly created trades Ids");
		}
		//Checking if all newly created IDs are correctly retrieved
		if(		idPerStartTimestamp == null || 
				idPerStartTimestamp.size() != tradesExt.size() || 
				!idPerStartTimestamp.containsKey(((Date)startDates[0]).getTime()) || 
				!idPerStartTimestamp.containsKey(((Date)startDates[1]).getTime())) {
			
			Logger.getLogger(this.getClass().getName())
			.log(Level.SEVERE, "Unable to retrieved newly created trades Ids");
			return false;
		}
		
		//We have correspondance between startDate and ids for all newly created trades
		//We now set id in each saved POJO
		for(int i=0 ; i<tradesExt.size() ; i++) {
			TradeExtPojo tradeExt = tradesExt.get(i);
			long startDateTime = tradeExt.getStartDate().getTime();
			if(idPerStartTimestamp.containsKey(startDateTime)) {
				tradeExt.setId(idPerStartTimestamp.get(startDateTime));
				
			} else {
				Logger.getLogger(this.getClass().getName())
						.log(Level.SEVERE, "Unable to find ID from one newly saved trade");
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean deleteByProvider(int providerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByProvider(ProviderPojo provider) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getTradeId(Date startDate) {
		String query = getFilledQuery("provider.trade.select_id_by_startdate", startDate);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving trade ID", e);
		}
		return getResultsetHelper().extractInt(res);
	}



	
}

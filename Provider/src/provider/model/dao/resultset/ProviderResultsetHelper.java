package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.model.pojo.BrokerPojo;
import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.ETradeMovementType;
import provider.model.pojo.MetaProviderPojo;
import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeExtPojo;
import provider.model.pojo.TradeMovementPojo;
import provider.model.pojo.TradePojo;

public enum ProviderResultsetHelper implements IProviderResultsetHelper {

	INSTANCE;
	
	@Override
	public BrokerPojo extractBroker(ResultSet res) {
		Integer id = null;
		String name = null;
		BrokerPojo broker = null;
		try {
			if(res.next()) {
				id = res.getInt("ID");
				name = res.getString("NAME");
				broker = new BrokerPojo(id, name);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while building broker", e);
			return null;
		}
		return broker;
	}

	@Override
	public CurrencyPojo extractCurrency(ResultSet res) {
		Integer id = null;
		String cur1 = null, cur2 = null;
		CurrencyPojo currency = null;
		try {
			if(res.next()) {
				id = res.getInt("ID");
				cur1 = res.getString("CUR_1");
				cur2 = res.getString("CUR_2");
				currency = new CurrencyPojo(id, cur1, cur2);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while building currency", e);
			return null;
		}
		return currency;
	}

	@Override
	public ProviderPojo extractProvider(ResultSet res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MetaProviderPojo extractMetaProvider(ResultSet res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradePojo extractTrade(ResultSet res) {
		int id, currencyId, providerId;
		Date startDate, endDate;
		float 	bestPips, bestDollarLot, 
				worstPips, worstDollarLot,
				netPips, netDollarLot;
		
		TradePojo trade = null;
		try {
			if(res.next()) {
				id = res.getInt("ID");
				currencyId = res.getInt("CURRENCY_ID");
				providerId = res.getInt("PROVIDER_ID");
				startDate = new Date(res.getLong("START_DATE"));
				endDate = new Date(res.getLong("END_DATE"));
				bestPips = res.getFloat("BEST_PIPS");
				bestDollarLot = res.getFloat("BEST_DOLLAR_LOT");
				worstPips = res.getFloat("WORST_PIPS");
				worstDollarLot = res.getFloat("WORST_DOLLAR_LOT");
				netPips = res.getFloat("NET_PIPS");
				netDollarLot = res.getFloat("NET_DOLLAR_LOT");

				trade = new TradePojo(id, currencyId, providerId, 
						startDate, endDate, bestPips, bestDollarLot, worstPips, 
						worstDollarLot, netPips, netDollarLot);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while building trade extension", e);
			return null;
		}
		return trade;
	}

	@Override
	public TradeExtPojo extractTradeExt(ResultSet res) {
		int id, currencyId, providerId;
		Date startDate, endDate;
		float 	bestPips, bestDollarLot, 
				worstPips, worstDollarLot,
				netPips, netDollarLot;
		String ticket;
		
		TradeExtPojo tradeExt = null;
		try {
			if(res.next()) {
				id = res.getInt("ID");
				currencyId = res.getInt("CURRENCY_ID");
				providerId = res.getInt("PROVIDER_ID");
				startDate = new Date(res.getLong("START_DATE"));
				endDate = new Date(res.getLong("END_DATE"));
				bestPips = res.getFloat("BEST_PIPS");
				bestDollarLot = res.getFloat("BEST_DOLLAR_LOT");
				worstPips = res.getFloat("WORST_PIPS");
				worstDollarLot = res.getFloat("WORST_DOLLAR_LOT");
				netPips = res.getFloat("NET_PIPS");
				netDollarLot = res.getFloat("NET_DOLLAR_LOT");
				ticket = res.getString("TICKET");

				tradeExt = new TradeExtPojo(id, currencyId, providerId, 
						startDate, endDate, bestPips, bestDollarLot, worstPips, 
						worstDollarLot, netPips, netDollarLot, ticket);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while building trade extension", e);
			return null;
		}
		return tradeExt;
	}

	@Override
	public TradeMovementPojo extractTradeMovement(ResultSet res) {
		int id, tradeId, provider_id;
		ETradeMovementType type;
		Date date;
		float valuePips, valueDollarLot;
		
		TradeMovementPojo tradeMovement = null;
		try {
			if(res.next()) {
				id = res.getInt("ID");
				tradeId = res.getInt("TRADE_ID");
				provider_id = res.getInt("PROVIDER_ID");
				type = ETradeMovementType.get(res.getInt("MOVEMENT_TYPE_ID")); 
				date = new Date(res.getLong("MOVEMENT_DATE"));
				valuePips = res.getFloat("VALUE_PIPS");
				valueDollarLot = res.getFloat("VALUE_DOLLAR_LOT");
				
				tradeMovement = new TradeMovementPojo(id, provider_id, tradeId, 
						type, date, valuePips, valueDollarLot);
			}
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while building currency", e);
			return null;
		}
		return tradeMovement;
	}



}

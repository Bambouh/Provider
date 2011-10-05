package provider.model.dao.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.model.pojo.BrokerPojo;
import provider.model.pojo.CurrencyPojo;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradeExtPojo extractTradeExt(ResultSet res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TradeMovementPojo extractTradeMovement(ResultSet res) {
		// TODO Auto-generated method stub
		return null;
	}



}

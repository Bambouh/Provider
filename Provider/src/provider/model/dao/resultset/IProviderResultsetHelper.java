package provider.model.dao.resultset;

import java.sql.ResultSet;

import provider.model.pojo.BrokerPojo;
import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.MetaProviderPojo;
import provider.model.pojo.ProviderPojo;
import provider.model.pojo.TradeExtPojo;
import provider.model.pojo.TradeMovementPojo;
import provider.model.pojo.TradePojo;

public interface IProviderResultsetHelper {

	BrokerPojo extractBroker(ResultSet res);
	
	CurrencyPojo extractCurrency(ResultSet res);
	
	ProviderPojo extractProvider(ResultSet res);
	
	MetaProviderPojo extractMetaProvider(ResultSet res);
	
	TradePojo extractTrade(ResultSet res);
	
	TradeExtPojo extractTradeExt(ResultSet res);
	
	TradeMovementPojo extractTradeMovement(ResultSet res);
	
}

package provider;

import java.util.Date;
import java.util.SortedSet;

import provider.entity.IBroker;
import provider.entity.ITrade;
import provider.entity.ITradeMovement;
import provider.model.dao.IProviderDao;
import provider.model.pojo.EProviderType;

/**
 * Main interface of the library.
 * 
 */
public interface IProvider {
	
	/**
	 * @return name of the provider
	 */
	String getName();
	
	/**
	 * @return unique ID that links the provider to Zulutrade signal provider
	 */
	int getLinkId();
	
	/**
	 * @return when did the first trade opened
	 */
	Date getStartDate();
	
	/**
	 * @return when did the last trade closed
	 */
	Date getEndDate();
	
	/**
	 * Technical info not related to trades
	 * @return when did the provider has been updated last
	 */
	Date getLastUpdated();
	
	/**
	 * @return the type of provider
	 */
	EProviderType getType();
	
	/**
	 * @return the broker used
	 */
	IBroker getBroker();
	
	/**
	 * @return list of trades, ordered by trade.startDate
	 */
	SortedSet<ITrade> getTrades();
	
	/**
	 * @return list of trade movements, ordered by tradeMovement.date
	 */
	SortedSet<ITradeMovement> getTradeMovements();
	
	/**
	 * @return the dao which enables to access provider queries
	 * or to get the database connection to create new queries
	 */
	IProviderDao getDao();
	
	/**
	 * Creates or update the provider from what you gave through the constructor
	 * @return weather calculation went good or bad
	 */
	boolean calculate();
	
	
}

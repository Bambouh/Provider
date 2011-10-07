package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.ICurrencyDao;
import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.ProviderPojo;

public class CurrencyDao extends Dao implements ICurrencyDao {

	private Statement statement;
	
	public CurrencyDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while creating CurrencyDao", e);
		}
	}

	@Override
	public CurrencyPojo getCurrency(int currencyId) {
		String query = getFilledQuery("provider.currency.select_one", currencyId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving Currency", e);
		}
		return getProviderResultsetHelper().extractCurrency(res);
	}
	
	@Override
	public Integer getCurrencyId(String cur1, String cur2) {
		String query = getFilledQuery("provider.currency.select_id_by_cur", cur1, cur2);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving Currency", e);
		}
		return getResultsetHelper().extractInt(res);
	}

	@Override
	public Map<String, Integer> getCurrencyIdPerPair() {
		String query = getFilledQuery("provider.currency.select_all_id_per_pair", CurrencyPojo.CURRENCY_SEPARATOR);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving currencies ID per pair", e);
		}
		return getResultsetHelper().extractMap(res, new String(), new Integer(0));
	}
	
	@Override
	public Map<Integer, String> getCurrencyPairPerId() {
		String query = getFilledQuery("provider.currency.select_all_pair_per_id", CurrencyPojo.CURRENCY_SEPARATOR);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving currencies pair per ID", e);
		}
		return getResultsetHelper().extractMap(res, new Integer(0), new String());
	}
	
	@Override
	public List<String> getCurrencyPairs() {
		String query = getQuery("provider.currency.select_all_pairs");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving all currencies pair", e);
		}
		return getResultsetHelper().extractList(res, new String());
	}

	@Override
	public List<CurrencyPojo> getCurrencies() {
		String query = getQuery("provider.currency.select_all");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving all currencies", e);
		}
		List<CurrencyPojo> currencies = new ArrayList<CurrencyPojo>();
		CurrencyPojo currency = null;
		while((currency = getProviderResultsetHelper().extractCurrency(res)) != null)
			currencies.add(currency);
		
		return currencies;
	}

	@Override
	public List<CurrencyPojo> getCurrenciesPerProvider(ProviderPojo provider) {
		return getCurrenciesPerProvider(provider.getId());
	}
	
	@Override
	public List<CurrencyPojo> getCurrenciesPerProvider(int providerId) {
		String query = getFilledQuery("provider.currency.select_all_per_provider", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving currencies of a provider" , e);
		}
		List<CurrencyPojo> currencies = new ArrayList<CurrencyPojo>();
		CurrencyPojo currency = null;
		while((currency = getProviderResultsetHelper().extractCurrency(res)) != null)
			currencies.add(currency);
		
		return currencies;
	}

	@Override
	public boolean saveCurrency(CurrencyPojo currency) {
		currency.setId(-1);
		String query = getFilledQuery("provider.currency.upsert_one", 
				currency.getCur1(), currency.getCur2(),
				currency.getCur1(), currency.getCur2());
		int currencyId = -1;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			statement.execute(query);
			currencyId = getCurrencyId(currency.getCur1(), currency.getCur2());
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving currency", e);
			return false;
		}
		currency.setId(currencyId);
		close(statement);
		
		if(currency.getId() > 0)
			return true;
		
		return false;
	}

	@Override
	public boolean saveCurrencies(List<CurrencyPojo> currencies) {
		//Creating statement
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			getConnection().setAutoCommit(false);
		
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to create database statement");
		}
		//Inserting rows : addBatch
		for(CurrencyPojo currency : currencies) {
			String query = getFilledQuery("provider.currency.upsert_one",
					currency.getCur1(), currency.getCur2(),
					currency.getCur1(), currency.getCur2());
			try {
				statement.addBatch(query);
			} catch (SQLException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
						"Error occured while saving currencies", e);
				close(statement);
				return false;
			}
		}
		//Inserting rows : commit
		try {
			statement.executeBatch();
			getConnection().commit();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving currencies", e);
		} finally {
			close(statement);
		}
		
		//Retrieving currencies ID && updating currency POJOs with it
		Map<String, Integer> idPerCurrency = getCurrencyIdPerPair();
		//Saving IDs in newly created currencies POJOs
		for(int i=0 ; i<currencies.size() ; i++) {
			String pair = currencies.get(i).getPair();
			int id = -1;
			if((	idPerCurrency.containsKey(pair) 
				&& (id = idPerCurrency.get(pair)) > 0)) {
				currencies.get(i).setId(id);
			
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
						"error while retrieving currencies IDs");
				return false;
			}
		}
		return true;
	}
	
}

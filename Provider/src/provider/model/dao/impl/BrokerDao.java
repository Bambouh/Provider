package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.IBrokerDao;
import provider.model.pojo.BrokerPojo;
import provider.model.pojo.ProviderPojo;

public class BrokerDao extends Dao implements IBrokerDao {

	private Statement statement;
	
	public BrokerDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while creating BrokerDao", e);
		}
	}

	@Override
	public BrokerPojo getBroker(int providerId) {
		String query = getQuery("provider.broker.select_one", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving broker", e);
		}
		return getProviderResultsetHelper().extractBroker(res);
	}

	@Override
	public Integer getBrokerId(String name) {
		String query = getQuery("provider.broker.select_id_by_name", name);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving broker", e);
		}
		return getResultsetHelper().extractInt(res);
	}
	
	@Override
	public BrokerPojo getBroker(ProviderPojo provider) {
		return getBroker(provider.getBrokerId());
	}

	@Override
	public List<String> getBrokerNames() {
		String query = getQuery("provider.broker.select_all_names");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving broker", e);
		}
		List<String> names = new ArrayList<String>();
		String name = null;
		while((name = getResultsetHelper().extractString(res)) != null) {
			names.add(name);
		}
		return names;
	}

	@Override
	public List<BrokerPojo> getBrokers() {
		String query = getQuery("provider.broker.select_all");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving broker", e);
		}
		List<BrokerPojo> brokers = new ArrayList<BrokerPojo>();
		BrokerPojo broker = null;
		while((broker = getProviderResultsetHelper().extractBroker(res)) != null) {
			brokers.add(broker);
		}
		return brokers;
	}

	@Override
	public boolean saveBroker(BrokerPojo broker) {
		String query = getQuery("provider.broker.upsert_one", broker.getName(), broker.getName());
		int brokerId = -1;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			statement.execute(query);
			brokerId = getBrokerId(broker.getName());
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving broker", e);
			return false;
		}
		broker.setId(brokerId);
		close(statement);
		
		if(broker.getId() > 0)
			return true;
		
		return false;
	}

}

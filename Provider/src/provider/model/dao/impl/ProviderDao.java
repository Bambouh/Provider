package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.IProviderDao;
import provider.model.pojo.ProviderPojo;

public class ProviderDao extends Dao implements IProviderDao {

	private Statement statement;
	
	public ProviderDao(IProviderManager providerManager) {
		super(providerManager);
		try {
			statement = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while creating TradeExtDao", e);
		}
	}

	@Override
	public ProviderPojo getProvider(int providerId) {
		String query = getFilledQuery("provider.provider.select_one", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider with id=" + providerId, e);
			return null;
		}
		return getProviderResultsetHelper().extractProvider(res);
	}

	@Override
	public ProviderPojo getProvider(String providerName) {
		String query = getFilledQuery("provider.provider.select_one_by_name", providerName);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider with name=" + providerName, e);
			return null;
		}
		return getProviderResultsetHelper().extractProvider(res);
	}
	
	@Override
	public Integer getProviderId(String providerName) {
		String query = getFilledQuery("provider.provider.select_one_per_name", providerName);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider with name=" + providerName, e);
			return null;
		}
		//Returns null if no id found
		Integer providerId = getResultsetHelper().extractInt(res);
		if(providerId == null || providerId < 1) {
			return null;
		}
		return providerId;
	}
	
	@Override
	public Pair<Date, Date> getProviderStartAndEndDates(int providerId) {
		String query = getFilledQuery("provider.trade.select_first_start_date_and_last_end_date_from_provider", providerId);
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider start and end dates", e);
			return null;
		}
		Pair<Long, Long> pairLong = getResultsetHelper().extractPair(res, new Long(0), new Long(0));
		return new MutablePair<Date, Date>(new Date(pairLong.getLeft()), new Date(pairLong.getRight()));
	}

	@Override
	public List<String> getProviderNames() {
		String query = getQuery("Provider.provider.select_all_names");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider names", e);
			return null;
		}
		return getResultsetHelper().extractList(res, new String());
	}

	@Override
	public Map<Integer, String> getProviderNamesPerId() {
		String query = getQuery("Provider.provider.select_all_names_per_id");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving provider names", e);
			return null;
		}
		return getResultsetHelper().extractMap(res, new Integer(0), new String());
	}

	@Override
	public List<ProviderPojo> getProviders() {
		String query = getQuery("provider.provider.select_all");
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving all providers", e);
			return null;
		}
		List<ProviderPojo> providers = new ArrayList<ProviderPojo>();
		ProviderPojo provider = null;
		while((provider = getProviderResultsetHelper().extractProvider(res)) != null)
			providers.add(provider);
		
		return providers;
	}
	
	@Override
	public boolean createProvider(ProviderPojo provider) {
		String query = getFilledQuery("provider.provider.upsert_one", 
				provider.getName(), provider.getName(), 
				provider.getStartDate(), provider.getEndDate(), 
				provider.getLastUpdate(), provider.getLinkId(), 
				provider.getBrokerId());
		
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			statement.executeUpdate(query);
			commit();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while saving Provider", e);
			rollback();
			return false;
		
		} finally {
			close(statement);
		}
		//Retrieving id
		int id = getProviderId(provider.getName());
		if(id < 1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while retrieving saved provider");
			return false;
		}
		provider.setId(id);
		
		return true;
	}

	@Override
	public boolean updateProviderLastUpdate(int providerId,	ProviderPojo provider) {
		if(provider.getId() < 1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Cannot update provider lastUpdate if provider not created");
			return false;
		}
		String query = getFilledQuery("provider.provider.update_last_update_from_id", 
				provider.getLastUpdate(), provider.getId());
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			int res = statement.executeUpdate(query);
			if(res != 1)		throw new SQLException("no update has been done. " +
					"Probably no provider with id=" + provider.getId() + " exists in the database");
			commit();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while updateing Provider lastUpdate field", e);
			rollback();
			return false;
			
		} finally {
			close(statement); 
		}
		return true;
	}

	@Override
	public boolean updateProvider(ProviderPojo provider) {
		if(provider.getId() < 1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Cannot update provider if provider not created");
			return false;
		}
		int previousId = getProviderId(provider.getName());
		if(previousId != provider.getId()) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"provider name is immutable, cannot update it");
			return false;
		}
		
		String query = getFilledQuery("provider.provider.update_all", 
				provider.getName(), provider.getStartDate(), provider.getEndDate(), 
				provider.getLastUpdate(), provider.getLinkId(), 
				provider.getBrokerId(), provider.getId());
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			int res = statement.executeUpdate(query);
			if(res != 1)		throw new SQLException("no update has been done. " +
					"Probably no provider with id=" + provider.getId() + " exists in the database");
			commit();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while updateing Provider " + provider.getName(), e);
			rollback();
			return false;
			
		} finally {
			close(statement); 
		}
		return true;
	}

	@Override
	public boolean deleteProvider(int providerId) {
		if(providerId < 1) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Cannot delete provider if provider not created");
			return false;
		}
		String query = getFilledQuery("provider.provider.delete_one", providerId);
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			int res = statement.executeUpdate(query);
			if(res != 1)		throw new SQLException("no delete has been done. " +
					"Probably no provider with id=" + providerId + " exists in the database");
			commit();
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while deleting a Provider", e);
			rollback();
			return false;
			
		} finally {
			close(statement); 
		}
		return true;
	}

	

	
	

}

package provider.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.resultset.IProviderResultsetHelper;
import provider.model.dao.resultset.IResultsetHelper;
import provider.model.dao.resultset.ProviderResultsetHelper;
import provider.model.dao.resultset.ResultsetHelper;
import wasa.util.sql.ISqlEngine;

public abstract class Dao {

	private ISqlEngine sqlEngine;
	private Connection connection;
	private IResultsetHelper resultsetHelper;
	private IProviderResultsetHelper providerResultsetHelper;
	
	protected Dao(IProviderManager providerManager) {
		sqlEngine = providerManager.getSqlEngine();
		connection = providerManager.getConnection();
		resultsetHelper = ResultsetHelper.INSTANCE;
		providerResultsetHelper = ProviderResultsetHelper.INSTANCE;
	}
	
	protected ISqlEngine getSqlEngine() {
		return sqlEngine;
	}
	
	protected Connection getConnection() {
		return connection;
	}
	
	protected String getFilledQuery(String queryName, Object...parameters) {
		return sqlEngine.getFilledQuery(queryName, parameters);
	}
	
	protected String getQuery(String queryName) {
		return sqlEngine.getQuery(queryName);
	}
	
	protected IResultsetHelper getResultsetHelper() {
		return resultsetHelper;
	}
	
	protected IProviderResultsetHelper getProviderResultsetHelper() {
		return providerResultsetHelper;
	}
	
	protected boolean close(Statement statement) {
		try {
			statement.close();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured while closing a statement", e);
			return false;
		}
		return true;
	}
	
	protected boolean rollback() {
		try {
			getConnection().rollback();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
					"Error occured during connection rollback", e);
			return false;
		}
		return true;
	}
}

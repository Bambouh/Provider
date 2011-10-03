package provider.model.dao;

import java.sql.Connection;

import provider.manager.IProviderManager;
import wasa.util.sql.ISqlEngine;

public abstract class Dao {

	private ISqlEngine sqlEngine;
	private Connection connection;
	
	public Dao(IProviderManager providerManager) {
		sqlEngine = providerManager.getSqlEngine();
		connection = providerManager.getConnection();
	}
	
	public ISqlEngine getSqlEngine() {
		return sqlEngine;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public String getQuery(String queryName, Object...parameters) {
		return sqlEngine.getQuery(queryName, parameters);
	}
	
}

package provider.manager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.IProvider;

import wasa.util.sql.ISqlEngine;
import wasa.util.sql.SqlEngine;

public final class ProviderManager implements IProviderManager {

	private static final File SQL_DIR_FILE = new File("bin\\provider\\model\\sql\\");
	
	private static ISqlEngine defaultSqlEngine = null;
	private static Connection defaultConnection = null;
	
	{
		defaultSqlEngine = new SqlEngine(SQL_DIR_FILE);
		defaultConnection = createConnection();
	}
	
	private static final Map<IProvider, ISqlEngine> sqlEnginePerProvider = new HashMap<IProvider, ISqlEngine>();
	private static final Map<IProvider, Connection> connectionPerProvider = new HashMap<IProvider, Connection>();
	
	private IProvider provider;
	
	//TODO: manage smartly connection and SQL engine so it does not share 1 instance among all the provider, so it does not create bottleneck
	private ProviderManager(IProvider provider) {
		this.provider = provider;
		sqlEnginePerProvider.put(provider, defaultSqlEngine);
		connectionPerProvider.put(provider, defaultConnection);
	}
	
	@Override
	public ISqlEngine getSqlEngine() {
		return sqlEnginePerProvider.get(provider);
	}

	@Override
	public Connection getConnection() {
		return connectionPerProvider.get(provider);
	}
	
	private static Connection createConnection() {
		Connection connection = null;
		try {
			//TODO: use configuration file to store configuration
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:file:provider", 
					"sa", 
					"");
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create database connection");
		}
		return connection;
	}

	@Override
	public IProvider getProvider() {
		return provider;
	}
	
}

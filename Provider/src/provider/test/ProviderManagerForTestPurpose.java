package provider.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.IProvider;
import provider.manager.IProviderManager;
import wasa.util.sql.ISqlEngine;
import wasa.util.sql.SqlEngine;

/**
 * ProviderManager for test purpose only
 */
public class ProviderManagerForTestPurpose implements IProviderManager {

	private static final File SQL_DIR_FILE = new File("bin\\provider\\model\\sql\\");
	
	@Override
	public ISqlEngine getSqlEngine() {
		return new SqlEngine(SQL_DIR_FILE);
	}

	/**
	 * Non persistent memory connection, for test purpose
	 */
	@Override
	public Connection getConnection() {
		Connection connection = null;
		try {
			//TODO: use configuration file to store configuration
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem:provider", 
					"sa", 
					"");
		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create database connection");
		}
		return connection;
	}

	@Override
	public IProvider getProvider() {
		return null;
	}

}

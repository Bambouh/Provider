package provider.manager;

import java.sql.Connection;

import provider.IProvider;

import wasa.util.sql.ISqlEngine;

/**
 * Manage provider resources amount all its instances.
 * will determinate each resource the provider will use
 * in order to neither create bottleneck nor having to
 * too much instances.
 */
public interface IProviderManager {

	/**
	 * @return interface that builds query
	 */
	ISqlEngine getSqlEngine();
	
	/**
	 * @return database connection
	 */
	Connection getConnection();
	
	/**
	 * @return the provider managed by the instance
	 */
	IProvider getProvider();

}

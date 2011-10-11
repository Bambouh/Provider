package provider.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;
import provider.model.dao.Dao;
import provider.model.dao.IInitSchemaDao;

public class InitSchemaDao extends Dao implements IInitSchemaDao {
	
	public InitSchemaDao(IProviderManager providerManager) {
		super(providerManager);
	}

	@Override
	public boolean initSchema() {
		Statement statement = null;
		try {
			statement = getConnection().createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName())
					.log(Level.SEVERE, "Unable to create database statement");
			return false;
		}
		
		//Database schema initialization, fails if already created
		try {
			//Tables creation
			statement.addBatch(getFilledQuery("provider.init.create_table_provider"));
			statement.addBatch(getFilledQuery("provider.init.create_table_broker"));
			statement.addBatch(getFilledQuery("provider.init.create_table_currency"));
			statement.addBatch(getFilledQuery("provider.init.create_table_trade"));
			statement.addBatch(getFilledQuery("provider.init.create_table_trade_ext"));
			statement.addBatch(getFilledQuery("provider.init.create_table_trade_movement"));
			statement.addBatch(getFilledQuery("provider.init.create_table_movement_type"));
			statement.addBatch(getFilledQuery("provider.init.create_table_dual"));
			statement.executeBatch();
			commit();
			
		} catch (SQLException e) {
			//Checking if crash is due to database already initialized
			try {
				statement.executeQuery(getFilledQuery("provider.init.check_existance"));

			} catch (SQLException e1) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Database initialisation failed", e);
				return false;
			}
			
		} finally {
			close(statement);
		}
		return true;
	}
	
}

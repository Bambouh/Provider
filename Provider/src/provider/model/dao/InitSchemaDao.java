package provider.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import provider.manager.IProviderManager;

public class InitSchemaDao extends Dao implements IInitSchemaDao {
	
	public InitSchemaDao(IProviderManager providerManager) {
		super(providerManager);
	}

	//TODO: TEST THIS METHOD
	@Override
	public boolean initSchema() {
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
		
		//Checking table existance
		String createTradeTable = getQuery("provider.init.create_table_trade");
		try {
			statement.executeUpdate(createTradeTable);
		} catch (SQLException e) {
			//TODO: verifier type erreur, dire init OK que si table existe déjà
			Logger.getLogger(this.getClass().getName()).log(Level.INFO,
					"Database already set, skipping initialisation");
			return true;
		}
		
		//Starting database schema initialisation
		try {
			getConnection().rollback();
			
			//Tables creation
			statement.addBatch(getQuery("provider.init.create_table_trade"));
			statement.addBatch(getQuery("provider.init.create_table_broker"));
			statement.addBatch(getQuery("provider.init.create_table_meta_provider_link"));
			statement.addBatch(getQuery("provider.init.create_table_currency"));
			statement.addBatch(getQuery("provider.init.create_table_trade"));
			statement.addBatch(getQuery("provider.init.create_table_trade_ext"));
			statement.addBatch(getQuery("provider.init.create_table_trade_movement"));
			statement.addBatch(getQuery("provider.init.create_table_movement_type"));
			statement.executeBatch();
			getConnection().commit();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					"Database initialisation failed", e);
			return false;
		}
		
		
		
		
		
		return true;
	}
	
	

}

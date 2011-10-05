package provider.importer.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;

import provider.importer.IProviderImporter;
import provider.importer.ITradeAdapter;

public class ZulutradeCsvImporter implements IProviderImporter {
	
	protected String path;
	//protected ProviderImporterDao importerDao = new ProviderImporterDao();
	protected int providerId;
	
	public ZulutradeCsvImporter(String path) {
		this.path = path;
	}
	
	@Override
	public boolean saveProvider() {
		//Read the file
		File file = new File(path);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedReader dis = null;
		List<String> lines = new ArrayList<String>();
		/*
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new BufferedReader(new InputStreamReader(bis));
			String line;
			while((line = dis.readLine()) != null) {
				lines.add(line);
			}
			fis.close();
			bis.close();
			dis.close();
			
		} catch (FileNotFoundException e) {
			Log.getLogger(this).log(Level.SEVERE, "Cannot import provider", e);
			return false;
		} catch (IOException e) {
			Log.getLogger(this).log(Level.SEVERE, "Cannot import provider", e);
			return false;
		}
		SortedSet<Trade> trades =  new TreeSet<Trade>();
		List<Currency> currencies = new ArrayList<Currency>();
		
		//Build provider, trade list and currencies
		String signature = lines.get(0);
		ITradeAdapter tradeAdapter = null;
		try {
			tradeAdapter = TradeAdapterFactory.INSTANCE.build(signature);
		} catch (NullPointerException e) {
			Log.getLogger(this).log(Level.SEVERE, "No TradeAdapter implementation found for the report at path : " + path, e);
			return false;
		}
		
		lines.remove(0);
		for(String line : lines) {
			Trade trade = tradeAdapter.get(line);
			trades.add(trade);
			if(!currencies.contains(trade.getCurrency()))
				currencies.add(trade.getCurrency());
		}
		Provider provider = new ProviderAdapter().get(trades, getProviderName());
		
		//Saving all
		if(!importerDao.saveAll(provider, trades, currencies)) {
			Log.getLogger(this).log(Level.SEVERE, "Cannot save the report");
			return false;
		}
		
		//Retrieving provider ID
		if((providerId = provider.getId()) == 0) {
			Log.getLogger(this).log(Level.SEVERE, "The id of the provider has not been initialized");
			return false;
		}
		
		Log.getLogger(this).log(Level.INFO, "Provider : " + provider.getName() + " Successfully loaded");
		return true;
		*/
		return false;
	}

	@Override
	public String getProviderName() {
		String[] pathArray = getProviderPath().split("/");
		return pathArray[pathArray.length-1].split("_")[2];
	}

	@Override
	public String getProviderPath() {
		return path;
	}

	@Override
	public int getProviderId() {
		/*if(providerId == 0) {
			Log.getLogger(this).log(Level.WARNING, "Trying to access providerId before saving it");
		}
		return providerId;*/
		return -1;
	}
	
}





















package provider.model.dao;

import java.util.List;
import java.util.Map;

import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.ProviderPojo;

public interface ICurrencyDao {

	Integer getCurrencyId(String cur1, String cur2);
	
	CurrencyPojo getCurrency(int currencyId);
		
	List<String> getCurrencyPairs();
	
	Map<String, Integer> getCurrencyIdPerPair();
	
	Map<Integer, String> getCurrencyPairPerId();
	
	List<CurrencyPojo> getCurrencies();
	
	List<CurrencyPojo> getCurrenciesPerProvider(int providerId);
	
	List<CurrencyPojo> getCurrenciesPerProvider(ProviderPojo provider);
	
	boolean saveCurrency(CurrencyPojo currency);
		
	boolean saveCurrencies(List<CurrencyPojo> currencies);
	
}

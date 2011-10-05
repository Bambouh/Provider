package provider.model.dao;

import java.util.List;
import java.util.Map;

import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.ProviderPojo;

public interface ICurrencyDao {

	Integer getCurrencyId(String cur1, String cur2);
	
	CurrencyPojo getCurrency(int currencyId);
	
	List<Integer> getCurrencieIds();
	
	List<String> getCurrencyPairs();
	
	Map<String, Integer> getCurrencyIdPerName();
	
	List<CurrencyPojo> getCurrencies();
	
	List<CurrencyPojo> getCurrencies(int providerId);
	
	List<CurrencyPojo> getCurrencies(ProviderPojo provider);
	
	boolean saveCurrency(CurrencyPojo currency);
		
	boolean saveCurrencies(List<CurrencyPojo> currencies);
	
}

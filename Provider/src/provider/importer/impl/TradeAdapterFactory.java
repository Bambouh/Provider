package provider.importer.impl;

import java.util.HashMap;
import java.util.Map;

import provider.importer.ITradeAdapter;

public enum TradeAdapterFactory {

	INSTANCE;

	@SuppressWarnings("serial")
	private final Map<String, ITradeAdapter> adapters = new HashMap<String, ITradeAdapter>() {
		{
			ITradeAdapter a = new TradeAdapter13Fields1();
			ITradeAdapter b = new TradeAdapter12Fields1();
			put(a.getSignature(), a);
			put(b.getSignature(), b);
		}
	};

	public ITradeAdapter build(String signature) throws NullPointerException {
		if (!adapters.containsKey(signature))
			throw new NullPointerException(
					"No TradeAdapter implementation available for following signature : "
							+ signature);
		
		return adapters.get(signature);
	}
}

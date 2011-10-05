package wasa.util.forex;

import java.util.HashMap;
import java.util.Map;

public class PipValue {

	//Used only when the trade has 0 pips and no pipValue has been recorded with trade's currency
	protected static final float DEFAULT_PIP_VALUE_IN_DOLLAR_PER_LOT = 10;
	
	protected Map<String, Float> pipValuePerCurrencyMap = new HashMap<String, Float>();
	
	public float getPipValueInDollarLot(float valPip, float valDollarLot, String currency) {
		float pipsNet = valPip;
		float dollarLotNet = valDollarLot;
		float pipValue = DEFAULT_PIP_VALUE_IN_DOLLAR_PER_LOT;
		if(Math.abs(pipsNet) >= 0.5 && Math.abs(dollarLotNet) >= 0.5) {
			pipValue = Math.abs(dollarLotNet / pipsNet);
			pipValuePerCurrencyMap.put(currency, pipValue);
		}
		else if(pipValuePerCurrencyMap.containsKey(currency)) {
			pipValue = pipValuePerCurrencyMap.get(currency);
		}
		return pipValue;
	}
	
}

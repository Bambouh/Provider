package provider.importer.impl;

import java.util.Date;

import provider.entity.ITrade;
import provider.entity.impl.Trade;
import provider.importer.ITradeAdapter;

import wasa.util.date.DateHelper;
import wasa.util.date.IDateHelper;
import wasa.util.forex.PipValue;

public class TradeAdapter12Fields1 implements ITradeAdapter {
	
	private static final String SIGNATURE = "Type,Currency,Standard Lots,Date Open,Date Close,Price Open,Price Close,Highest Profit (Pips),Worst Drawdown (Pips),Interest ($),Profit (Pips),Profit ($)";
	
	private static final String DATE_FORMAT = "yyyyMMddHHmmss";
	
	protected IDateHelper dateformat = new DateHelper();
	protected PipValue pipValue = new PipValue();
	
	@Override
	public String getSignature() {
		return SIGNATURE;
	}

	@Override
	public ITrade get(String line) {
		String[] lineArr = line.split(DEFAULT_SEPARATOR);
		
		Date startDate, endDate;
		float worstPips, netPips, bestPips, netDollarLot, worstDollarLot, 
				bestDollarLot, nbLots, pip;
		String ticket = "", currencyPair;
		
		currencyPair = lineArr[1];
		nbLots = Float.valueOf(lineArr[2]);
		startDate = dateformat.getDate(lineArr[3], DATE_FORMAT);
		endDate = dateformat.getDate(lineArr[4], DATE_FORMAT);
		bestPips = Float.valueOf(lineArr[7]);
		worstPips = Float.valueOf(lineArr[8]);
		netPips = Float.valueOf(lineArr[10]);
		netDollarLot = Float.valueOf(lineArr[11]) / nbLots;
		pip = pipValue.getPipValueInDollarLot(netPips, netDollarLot, currencyPair);
		bestDollarLot = bestPips * pip;
		worstDollarLot = worstPips * pip;
		
		return new Trade(ticket, startDate, endDate, worstPips, bestPips,  
				netPips, worstDollarLot, bestDollarLot, netDollarLot,
				currencyPair);
	}

}

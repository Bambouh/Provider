package provider.entity.impl;

import java.util.Date;

import provider.entity.ITrade;
import provider.model.pojo.CurrencyPojo;
import provider.model.pojo.TradeExtPojo;
import provider.model.pojo.TradePojo;

public class Trade implements ITrade {

	TradePojo tradePojo;
	TradeExtPojo tradeExtPojo;
	CurrencyPojo currencyPojo;
	Date startDate;
	
	public Trade(String ticket, Date startDate, Date endDate, float worstPips, 
			float bestPips, float netPips, float worstDollarLot, 
			float bestDollarLot, float netDollarLot, String currencyPair) {
		
		tradePojo = new TradePojo(-1, -1, -1, startDate, endDate, bestPips,
				bestDollarLot, worstPips, worstDollarLot, netPips, netDollarLot);
		tradeExtPojo = new TradeExtPojo(tradePojo, ticket);
		currencyPojo = new CurrencyPojo(-1, currencyPair);
		this.startDate = startDate;
	}
	
	@Override
	public int getId() {
		return tradeExtPojo.getId();
	}

	@Override
	public int getCurrencyId() {
		return tradeExtPojo.getProviderId();
	}

	@Override
	public int getProviderId() {
		return tradeExtPojo.getProviderId();
	}

	@Override
	public Date getStartDate() {
		return tradeExtPojo.getStartDate();
	}

	@Override
	public Date getEndDate() {
		return tradeExtPojo.getEndDate();
	}

	@Override
	public float getBestPips() {
		return tradeExtPojo.getBestPips();
	}

	@Override
	public float getBestDollarLot() {
		return tradeExtPojo.getBestDollarLot();
	}

	@Override
	public float getWorstPips() {
		return tradeExtPojo.getWorstPips();
	}

	@Override
	public float getWorstDollarLot() {
		return tradeExtPojo.getWorstDollarLot();
	}

	@Override
	public float getNetPips() {
		return tradeExtPojo.getNetPips();
	}

	@Override
	public float getNetDollarLot() {
		return tradeExtPojo.getNetDollarLot();
	}

	@Override
	public String getTicket() {
		return tradeExtPojo.getTicket();
	}

	@Override
	public String getCurrencyPair() {
		return currencyPojo.getPair();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	
}

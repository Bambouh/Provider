package provider.model.pojo;

import java.util.Date;

public class TradePojo implements Comparable<TradePojo> {

	int id, currencyId, providerId;
	Date startDate, endDate;
	float 	bestPips, bestDollarLot, 
			worstPips, worstDollarLot,
			netPips, NetDollarLot;
	
	public TradePojo(int id, int currencyId, int providerId, Date startDate,
			Date endDate, float bestPips, float bestDollarLot, float worstPips,
			float worstDollarLot, float netPips, float netDollarLot) {
		super();
		this.id = id;
		this.currencyId = currencyId;
		this.providerId = providerId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bestPips = bestPips;
		this.bestDollarLot = bestDollarLot;
		this.worstPips = worstPips;
		this.worstDollarLot = worstDollarLot;
		this.netPips = netPips;
		NetDollarLot = netDollarLot;
	}

	public int getId() {
		return id;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public int getProviderId() {
		return providerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public float getBestPips() {
		return bestPips;
	}

	public float getBestDollarLot() {
		return bestDollarLot;
	}

	public float getWorstPips() {
		return worstPips;
	}

	public float getWorstDollarLot() {
		return worstDollarLot;
	}

	public float getNetPips() {
		return netPips;
	}

	public float getNetDollarLot() {
		return NetDollarLot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		TradePojo other = (TradePojo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(TradePojo arg0) {
		return startDate.compareTo(arg0.startDate);
	}
	
}

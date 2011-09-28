package provider.model.pojo;

import java.util.Date;

import provider.ETradeMovementType;

/**
 * Represent a movement related to a trade. It's an event, can be trade opening,
 * trade updating or trade closing.
 */
public class TradeMovementPojo implements Comparable<TradeMovementPojo> {

	int id, tradeId;
	ETradeMovementType type;
	Date date;
	float valuePips, valueDollarLot;
	
	public TradeMovementPojo(int id, int tradeId, ETradeMovementType type, 
			Date date, float valuePips, float valueDollarLot) {
		super();
		this.id = id;
		this.tradeId = tradeId;
		this.type = type;
		this.date = date;
		this.valuePips = valuePips;
		this.valueDollarLot = valueDollarLot;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTradeId() {
		return tradeId;
	}
	
	public ETradeMovementType getType() {
		return type;
	}
	
	public Date getDate() {
		return date;
	}
	
	public float getValuePips() {
		return valuePips;
	}
	
	public float getValueDollarLot() {
		return valueDollarLot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Based on id
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeMovementPojo other = (TradeMovementPojo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TradeMovementPojo [id=" + id + ", type=" + type + ", date="
				+ date + ", valuePips=" + valuePips + ", valueDollarLot="
				+ valueDollarLot + "]";
	}

	@Override
	public int compareTo(TradeMovementPojo arg0) {
		return date.compareTo(arg0.date);
	}

}

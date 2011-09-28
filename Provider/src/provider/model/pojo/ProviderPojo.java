package provider.model.pojo;

import java.util.Date;

import provider.EProviderType;

public class ProviderPojo {

	int id, linkId, brokerId;
	String name;
	Date startDate, endDate, lastUpdate;
	EProviderType type;
	
	public ProviderPojo(int id, int linkId, int brokerId, String name,
			Date startDate, Date endDate, Date lastUpdate, EProviderType type) {
		super();
		this.id = id;
		this.linkId = linkId;
		this.brokerId = brokerId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdate = lastUpdate;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getLinkId() {
		return linkId;
	}

	public int getBrokerId() {
		return brokerId;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public EProviderType getType() {
		return type;
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
		ProviderPojo other = (ProviderPojo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

package provider.model.pojo;

/**
 * The broker linked to the provider
 */
public class BrokerPojo {

	int id;
	String name;
	
	public BrokerPojo(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
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
		BrokerPojo other = (BrokerPojo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}

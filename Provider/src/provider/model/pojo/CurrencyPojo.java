package provider.model.pojo;

public class CurrencyPojo {

	private static final String CURRENCY_SEPARATOR = "/";
	
	int id;
	String cur1, cur2;
	
	public CurrencyPojo(int id, String pair) {
		super();
		this.id = id;
		String[] pairArr = pair.split(CURRENCY_SEPARATOR);
		this.cur1 = pairArr[0];
		this.cur2 = pairArr[1];
	}
	
	public CurrencyPojo(int id, String cur1, String cur2) {
		super();
		this.id = id;
		this.cur1 = cur1;
		this.cur2 = cur2;
	}
	
	public int getId() {
		return id;
	}

	public String getCur1() {
		return cur1;
	}

	public String getCur2() {
		return cur2;
	}
	
	public String getPair() {
		return cur1 + CURRENCY_SEPARATOR + cur2;
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
		CurrencyPojo other = (CurrencyPojo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}

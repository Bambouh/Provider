package provider.model.pojo;

public class TradeExtPojo extends TradePojo {

	String ticket;
	
	public TradeExtPojo(TradePojo trade, String ticket) {
		super(trade);
		this.ticket = ticket;
	}
	
	public String getTicket() {
		return ticket;
	}
	
}

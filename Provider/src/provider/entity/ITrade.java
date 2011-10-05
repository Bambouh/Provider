package provider.entity;

import java.util.Date;

public interface ITrade {

	int getId();

	int getCurrencyId();
	
	String getCurrencyPair();

	int getProviderId();

	Date getStartDate();

	Date getEndDate();

	float getBestPips();

	float getBestDollarLot();

	float getWorstPips();

	float getWorstDollarLot();

	float getNetPips();

	float getNetDollarLot();

	String getTicket();
	
}

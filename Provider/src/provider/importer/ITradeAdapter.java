package provider.importer;

import provider.entity.ITrade;

/**
 * Adapts a list of string into a Trade object.
 */
public interface ITradeAdapter {

	static final String DEFAULT_SEPARATOR = ",";
	
	/**
	 * Signature making the TradeAdapterFactory able to choose the right implementation.
	 * It is based on the title line of the report.
	 * @return signature of the implementation.
	 */
	String getSignature();
	
	/**
	 * @param line list of String representing a line in the zulutrade CSV report.
	 * @return a Trade, built from the provided line.
	 */
	ITrade get(String line);

}

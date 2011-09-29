package wasa.util.date;


import java.util.Date;

/**
 * Garanties that all calls to this factory returns a unique date.
 * Multithread ready. 
 * Unique through all the instances. 
 * To make it unique, it adds n millisecondes to the provided date,
 * until the date gets unique.
 */
public interface IUniqueDateFactory {
	
	static final int MAX_OFFSET_AUTHORIZED = 1000;	//1 second
	
	Date build(Date nonUniqueDate);
	
	/**
	 * @return maximum difference recorded, in millisecond, between
	 * the non unique date submitted and the unique date generated.
	 * A warning is thrown repeatedly if this offset went bigger than
	 * MAX_OFFSET_AUTHORIZED specified here.
	 */
	int getMaxOffset();
}

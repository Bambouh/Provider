package provider.uti;

import java.util.Date;

/**
 * Garanties that all calls to this factory returns a unique date.
 * Multithread ready. 
 * Unique through all the instances. 
 * To make it unique, it adds n millisecondes to the provided date,
 * until the date gets unique.
 */
public interface IUniqueDateFactory {
	
	Date build(Date nonUniqueDate);
}

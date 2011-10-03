package wasa.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import wasa.util.cache.CacheHelper;

public class DateHelper implements IDateHelper {

	private static final int maxCacheSize = 4;
	
	private Map<String, SimpleDateFormat> dateFormats = CacheHelper.LRU.getNewCacheMap(new String(), new SimpleDateFormat(), maxCacheSize);
	
	@Override
	public Date getDate(String dateStr, String dateFormatStr) {
		//Retrieving parser for given dateFormat from cache or creating new parser
		SimpleDateFormat dateFormat = null;
		if(dateFormats.containsKey(dateFormatStr)) {
			dateFormat = dateFormats.get(dateFormatStr);
		
		} else {
			dateFormat = new SimpleDateFormat(dateFormatStr);
			dateFormats.put(dateFormatStr, dateFormat);
		}
		
		//Parsing the date
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			Logger.getLogger(this.getClass().getName()).log(
					Level.SEVERE, "Problem converting : " + dateStr + " into a " +
					"date using format : " + dateFormatStr, e);
		}
		
		return date;
	}

	@Override
	public String getString(Date date, String dateFormatStr) {
		//Retrieving parser for given dateFormat from cache or creating new parser
		SimpleDateFormat dateFormat = null;
		if(dateFormats.containsKey(dateFormatStr)) {
			dateFormat = dateFormats.get(dateFormatStr);
		
		} else {
			dateFormat = new SimpleDateFormat(dateFormatStr);
			dateFormats.put(dateFormatStr, dateFormat);
		}
		
		//Return formated String representing the date
		return dateFormat.format(date);
	}

}

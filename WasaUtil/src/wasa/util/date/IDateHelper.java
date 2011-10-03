package wasa.util.date;

import java.util.Date;

public interface IDateHelper {

	Date getDate(String dateStr, String dateFormat);
	
	String getString(Date date, String dateFormat);
	
}

package wasa.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides several instances of IDateFormat, already filled with date format and
 * associated validation expression.
 * Here is the list:
 * - FORMAT_1 = "yyyy/MM/dd HH:mm:ss"
 */
public enum DateFormat implements IDateFormat {

	FORMAT_1("yyyy/MM/dd HH:mm:ss", "[0-9]{4}/[0-1]{1}[0-9]{1}/[0-3]{1}[0-9]{1} [0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}"),
	FORMAT_2("yyyyMMddHHmmss", "[0-9]{4}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}[0-2]{1}[0-9]{1}[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}")
	;
	
	private String format, validationRegexp;
	SimpleDateFormat dateFormat;
		
	DateFormat(String format, String validationRegexp) {
		this.format = format;
		this.validationRegexp = validationRegexp;
		dateFormat = new SimpleDateFormat(format);
	}
	
	@Override
	public Date getDate(String date) throws IllegalDateFormatException {
		if(validate(date)) {
			try {
				return dateFormat.parse(date);
			} catch (ParseException e) {
				throw new IllegalDateFormatException(date, format);
			}
		}
		return null;
	}
	
	@Override
	public String getString(Date date) {
		return dateFormat.format(date);
	}
	
	@Override
	public String getFormat() {
		return format;
	}
	
	@Override
	public boolean validate(String date) throws IllegalDateFormatException {
		if(check(date))
			return true;
		throw new IllegalDateFormatException(date, format);
	}

	@Override
	public boolean check(String date) {
		if(date.matches(validationRegexp))
			return true;
		return false;
	}

	


	
}

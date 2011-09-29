package wasa.util.date;

import java.util.Date;

/**
 * Specify a date format, directly usable by simpleDateFormat implementation, which
 * generates a Date object out of a String. 
 * Provide also the associated logic to check if the date you enter is valid:
 * it does have this format. 
 */
public interface IDateFormat {
	
	String getString(Date date);
	
	/**
	 * Use the provided date to create a date instance.
	 * @param date String representing the date we want.
	 * @return the date instance
	 * @throws IllegalDateFormatException 
	 */
	Date getDate(String date) throws IllegalDateFormatException;

	/**
	 * A string representing the format. 
	 * @return
	 */
	String getFormat();
	
	/**
	 * Checks if the string you provide is following the date format. If not, it 
	 * throws an exception, use check(String date) if you don't want the exception
	 * to be raised. 
	 * @param date to be checked
	 * @return true if it matches date format, false otherwise
	 * @throws IllegalDateFormatException 
	 */
	boolean validate(String date) throws IllegalDateFormatException;
	
	/**
	 * Same as validate(String date), except that it doesn't throw the exception
	 * if the date doesn't match the format.
	 * @param date to be checked
	 * @return true if it matches date format, false otherwise
	 */
	boolean check(String date);
}

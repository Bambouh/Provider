package wasa.util.date;

public class IllegalDateFormatException extends Exception {

	static final long serialVersionUID = 5599004877251938682L;
	
	public IllegalDateFormatException(String date, String format) {
		super("The date " + date + " doesn't match the following format: " + format);
	}
}

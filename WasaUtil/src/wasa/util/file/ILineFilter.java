package wasa.util.file;

public interface ILineFilter {
	
	/**
	 * @param line before it has been filtered
	 * @return return the line after a filter has been applied. If returns
	 * null, then the line is not added
	 */
	String filter(String line);
}

package wasa.util;


import java.io.File;
import java.util.List;

public interface IFileHelper {

	/**
	 * Parse the file and returns all lines it contains
	 * @param file
	 * @return
	 */
	List<String> getLines(File file);
	
	/**
	 * Copy the file from source to dest
	 * @param source
	 * @param dest
	 * @return true if no problem
	 */
	boolean copyFile(File source, File dest);
	
}

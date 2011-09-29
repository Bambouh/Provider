package wasa.util.file;


import java.io.File;
import java.util.List;

public interface IFileHelper {

	/**
	 * Parse the file and returns all lines it contains
	 * @param file
	 * @param filters that are going to apply while retrieving the file
	 * @return
	 */
	List<String> getLines(File file, ILineFilter... filters);
	
	/**
	 * Copy the file from source to dest
	 * @param source
	 * @param dest
	 * @return true if no problem
	 */
	boolean copyFile(File source, File dest);
	
}

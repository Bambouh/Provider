package test;

import java.io.File;
import java.util.List;

import org.junit.Test;

import wasa.util.file.FileHelper;
import wasa.util.file.IFileHelper;
import wasa.util.file.ILineFilter;

import junit.framework.TestCase;

public class FileHelperTest extends TestCase {
	
	@Test
	public void testFileReadingNoFilter() {
		IFileHelper fileHelper = FileHelper.INSTANCE;
		File file = new File("src\\test\\FileHelperTest.txt");
		List<String> res = fileHelper.getLines(file);
		
		assertTrue("No lines were retrieved", res.size() > 0);
		assertTrue("Wrong number of lines", res.size() == 10);
		assertTrue("The first line has been wrongly retrieved", res.get(0).equals("#Je nécrit un commentaire"));
		assertTrue("The fourth line has been wrongly retrieved", res.get(3).equals(""));
	}
	
	@Test
	public void testFileReadingOneFilter() {
		IFileHelper fileHelper = FileHelper.INSTANCE;
		File file = new File("src\\test\\FileHelperTest.txt");
		
		ILineFilter commentfilter = new ILineFilter() {
			
			@Override
			public String filter(String line) {
				if(line.startsWith("#"))
					return null;
				return line;
			}
		};
		
		List<String> res = fileHelper.getLines(file, commentfilter);
		
		assertTrue("No lines were retrieved", res.size() > 0);
		assertTrue("Wrong number of lines", res.size() == 7);
		assertTrue("The first line has been wrongly retrieved", res.get(0).equals("Je mange une pomme"));
	}
	
	@Test
	public void testFIleReadingTwoFilters() {
		IFileHelper fileHelper = FileHelper.INSTANCE;
		File file = new File("src\\test\\FileHelperTest.txt");
		
		ILineFilter commentfilter = new ILineFilter() {
			
			@Override
			public String filter(String line) {
				if(line.startsWith("#"))
					return null;
				return line;
			}
		};
		
		ILineFilter emptyLinefilter = new ILineFilter() {
			
			@Override
			public String filter(String line) {
				if(line.equals(""))
					return null;
				return line;
			}
		};
		
		List<String> res = fileHelper.getLines(file, commentfilter, emptyLinefilter);
		assertTrue("No lines were retrieved", res.size() > 0);
		assertTrue("Wrong number of lines", res.size() == 4);
		assertTrue("The first line has been wrongly retrieved", res.get(0).equals("Je mange une pomme"));
		assertTrue("The last line has been wrongly retrieved", res.get(3).equals(":D"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

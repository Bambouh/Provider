package test;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import wasa.util.sql.ISqlEngine;
import wasa.util.sql.SqlEngine;

import junit.framework.TestCase;

public class SqlEngineTest extends TestCase {

	@Test
	public void testSqlEngineInit() {
		File file = new File("src\\test\\");
		ISqlEngine sqlEngine = new SqlEngine(file);
		
		assertTrue(sqlEngine.getSqlDirectoryPath() != null);
	}
	
	@Test
	public void testSqlEngineQuery() {
		File file = new File("src\\test\\");
		ISqlEngine sqlEngine = new SqlEngine(file);
		
		String query1 = sqlEngine.getQuery("SELECTALLTRADES");
		assertTrue("wrong query without argument", query1.equals("SELECT * FROM DOUDOU"));
		
		String query2 = sqlEngine.getQuery("select_All_Doudou_per_date", new Date(0), new Integer[] {1,2,3,4});
		assertTrue("wrong query with arguments", query2.equals("SELECT * FROM DOUDOU WHERE START = TO_DATE('19700101010000', 'YYYYMMDDHH24MISS') AND ID IN (1,2,3,4);"));
		
		String query3 = sqlEngine.getQuery("select_All_Doudou_per_date", new Date(0), new Double[] {100000000000.0});
		assertTrue("wrong query with arguments", query3.equals("SELECT * FROM DOUDOU WHERE START = TO_DATE('19700101010000', 'YYYYMMDDHH24MISS') AND ID IN (1151551.0515131005);"));
		
		//TODO: vérifier que les conversion de gros nombre ne pose pas de pb lorsqu'ils sont transformés en 1E11 par exemple 
	}
}

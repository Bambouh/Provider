package provider.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import provider.importer.IProviderImporter;
import provider.importer.ImportStatus;
import provider.importer.impl.ZulutradeCsvImporter;
import provider.manager.IProviderManager;
import provider.model.dao.ITradeExtDao;
import provider.model.dao.impl.InitSchemaDao;
import provider.model.dao.impl.TradeExtDao;
import provider.test.report.ProviderReportForTestPurpose;
import junit.framework.TestCase;

public class ZulutradeCsvImporterTest extends TestCase {

	private IProviderManager providerManager;
	private ITradeExtDao tradeExtDao;
	
	public ZulutradeCsvImporterTest() {
		providerManager = new ProviderManagerForTestPurpose();
		InitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
		tradeExtDao = new TradeExtDao(providerManager);
	}
	
	@Test
	public void testZulutradeProviderImportVeryBigReport() {
		ProviderReportForTestPurpose report = ProviderReportForTestPurpose.VERY_BIG; 
		IProviderImporter importer = new ZulutradeCsvImporter(providerManager);
		
		assertTrue(importer.saveProvider(report.getFile()));
		
		//Checking number of trades for newly created provider
		{
			int expected = report.getNbTrades();
			int actual = importer.getNbTradesImported();
			assertTrue(expected == actual);
		}
		
		//Checking status
		{
			ImportStatus status = importer.getImportStatus();
			assertEquals(status, ImportStatus.PROVIDER_CREATED_NEW);
		}
		
		//Reimporting
		importer.saveProvider(report.getFile());
		//Checking status
		{
			ImportStatus status = importer.getImportStatus();
			assertEquals(status, ImportStatus.PROVIDER_RECREATED);
		}
		
		//Checking for non unique tickets (1 has been added on purpose in the list)
		{
			List<String> nonUniqueTickets = tradeExtDao.getNonUniqueTickets();
			assertEquals(nonUniqueTickets.size(), 1);
		}
		
		//Checking some random trades among the one imported
		List<String> tickets = new ArrayList<String>();
		tickets.add("29.634526451057355345");	//29.634526451057355345,BUY,GBP/USD,1,2011/09/28 15:16:40,2011/09/28 15:30:15,1.56345,1.5655,22,-5,0,20.5,205
		tickets.add("30.634243157447359680");	//30.634243157447359680,BUY,GBP/JPY,1,2010/11/10 12:28:09,2010/11/10 12:45:52,132.329,132.464,14,-6,0,13.5,163.83
		tickets.add("10.633904141033816149");	//10.633904141033816149,BUY,GBP/JPY,1,2009/10/07 00:15:29,2009/10/07 12:56:11,141.435,141.71,28,-173,0,27.5,308.56
		tickets.add("10.633753278082093768");	//10.633753278082093768,SELL,GBP/JPY,1,2009/04/14 17:51:43,2009/04/14 21:09:28,147.41,147.076,33,-42,-3.5,33.4,334.77
		
		//TODO: check the random trades
		
	}
}

package provider.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.ITradeExtDao;
import provider.model.dao.ITradeMovementDao;
import provider.model.dao.impl.InitSchemaDao;
import provider.model.dao.impl.TradeExtDao;
import provider.model.dao.impl.TradeMovementDao;
import provider.model.pojo.EMovementType;
import provider.model.pojo.TradeExtPojo;
import provider.model.pojo.TradeMovementPojo;
import provider.model.pojo.TradePojo;
import junit.framework.TestCase;

public class TradeMovementDaoTest extends TestCase {

	private static final String DATE_FORMAT_JAVA = "yyyy/MM/dd HH:mm:ss:SSS";
	private static final int PROVIDER_ID = 7;
	
	private IProviderManager providerManager;
	private ITradeMovementDao tradeMovementDao;
	private ITradeExtDao tradeExtDao;
	
	@SuppressWarnings("serial")
	private List<TradeExtPojo> tradesExt = new ArrayList<TradeExtPojo>() {{
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/09/22 18:04:06:000"), date("2011/09/23 18:05:07:011"), 50, 500, -442, -4420, 33, 330, "545045340.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/09/23 17:18:45:000"), date("2011/09/23 18:05:07:011"), 100, 1000, -705, -7050, 11, 110, "510456450.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/09/24 14:04:06:000"), date("2011/09/24 18:06:07:011"), 400, 4000, -12, -120, 100, 1000, "514565650.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/09/27 16:04:06:000"), date("2011/10/01 18:05:07:011"), 2, 20, -100, -1, 0, 0, "545045340.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/01 16:04:06:000"), date("2011/10/01 18:05:07:011"), 72, 750, -402, -4020, 0, 0, "507863730.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/02 01:04:06:000"), date("2011/10/02 18:05:07:011"), 42, 420, -75, -750, 40, 400, "510397400.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/02 02:04:06:000"), date("2011/10/06 18:05:07:011"), 88, 880, -75, -750, 44, 440, "545370300.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/02 04:04:06:000"), date("2011/10/06 18:05:07:011"), 26, 260, -42, -420, 12, 120, "570307300.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/04 23:04:06:000"), date("2011/10/06 18:05:07:011"), 77, 770, -2, -20, new Float(12.1), new Float(121), "726003500.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/04 18:04:06:000"), date("2011/10/06 18:05:07:011"), 44, 440, -727, -7270, new Float(4.42), new Float(44.2), "807806303.020"));
		add(new TradeExtPojo(1, PROVIDER_ID, date("2011/10/06 19:04:06:000"), date("2011/10/06 19:05:07:011"), 111, 1110, -75, -750, 33, 330, "879703500.020"));
	}};
	
	public TradeMovementDaoTest() {
		providerManager = new ProviderManagerForTestPurpose();
		InitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
		tradeExtDao = new TradeExtDao(providerManager);
		assertTrue(tradeExtDao.save(tradesExt));
		
		tradeMovementDao = new TradeMovementDao(providerManager);
	}
	
	@Test
	public void testTradeMovementInsertAndSelectSingle() {
		TradePojo trade = tradesExt.get(0);
		
		//Insertion from trade ID
		assertTrue(tradeMovementDao.createTradeMovementsFromTrade(trade.getId()));
		
		//Retrieve newly created movements
		{
			SortedSet<TradeMovementPojo> movements = tradeMovementDao.getMovementsPerTrade(trade.getId());
			assertTrue(movements.size() == 2);
			TradeMovementPojo startMovement = movements.first();
			TradeMovementPojo endMovement = movements.last();
			
			//Checking result
			assertTrue(startMovement.getTradeId() == endMovement.getTradeId());
			assertTrue(startMovement.getProviderId() == endMovement.getProviderId());
			
			assertTrue(startMovement.getValuePips() == trade.getWorstPips());
			assertTrue(startMovement.getValueDollarLot() == trade.getWorstDollarLot());
			assertTrue(DateUtils.isSameInstant(startMovement.getDate(), trade.getStartDate()));
			assertTrue(startMovement.getType() == EMovementType.START);
			
			assertTrue(endMovement.getValuePips() == trade.getNetPips());
			assertTrue(endMovement.getValueDollarLot() == trade.getNetDollarLot());
			assertTrue(DateUtils.isSameInstant(endMovement.getDate(), trade.getEndDate()));
			assertTrue(endMovement.getType() == EMovementType.END);
		}
		
		//Deleting movements
		assertTrue(tradeMovementDao.deleteTradeMovementsOfTrade(trade.getId()));
		
		//Checking select is empty
		{
			SortedSet<TradeMovementPojo> movements = tradeMovementDao.getMovementsPerTrade(trade.getId());
			assertTrue(movements.size() == 0);
		}
		
		
	}
	
	@Test
	public void testTradeMovementInsertAndSelectMultiple() {
		int providerId = tradesExt.get(0).getProviderId();
		int tradeCount;
		
		{
			SortedSet<TradeExtPojo> trades = tradeExtDao.getTradesExtPerProvider(providerId);
			tradeCount =  trades.size();
		}
		
		assertTrue(tradeMovementDao.createTradeMovementsFromProvider(providerId));
		
		{
			SortedSet<TradeMovementPojo> movements = tradeMovementDao.getMovementsPerProvider(providerId);
			assertTrue(movements.size() == tradeCount * 2);
		}		
	}
	
	private Date date(String dateStr) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateStr, DATE_FORMAT_JAVA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

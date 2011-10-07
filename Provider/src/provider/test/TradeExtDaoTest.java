package provider.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import provider.manager.IProviderManager;
import provider.model.dao.ITradeExtDao;
import provider.model.dao.InitSchemaDao;
import provider.model.dao.impl.TradeExtDao;
import provider.model.pojo.TradeExtPojo;

import junit.framework.TestCase;

public class TradeExtDaoTest extends TestCase {

	private static final String DATE_FORMAT_JAVA = "yyyy/MM/dd HH:mm:ss:SSS";
	
	private IProviderManager providerManager;
	private ITradeExtDao tradeExtDao;
	
	public TradeExtDaoTest() {
		providerManager = new ProviderManagerForTestPurpose();
		InitSchemaDao initSchemaDao = new InitSchemaDao(providerManager);
		initSchemaDao.initSchema();
		tradeExtDao = new TradeExtDao(providerManager);
	}
	
	@Test
	public void testTradeExtSave() {
		Date startDate = null, endDate = null;
		try {
			startDate = DateUtils.parseDate("2011/10/06 18:04:06:011", DATE_FORMAT_JAVA);
			endDate   = DateUtils.parseDate("2011/10/06 18:05:07:011", DATE_FORMAT_JAVA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		TradeExtPojo tradeExt = new TradeExtPojo(1, 3, startDate, endDate, 50, 500, -100, -1000, 33, 330, "510303500.020");
		
		assertTrue(tradeExtDao.save(tradeExt));
		
		TradeExtPojo sameTradeExt = tradeExtDao.getTradeExt(tradeExt.getId());

		assertTrue(tradeExt.getId() == sameTradeExt.getId());
		assertTrue(tradeExt.getTicket().equals(sameTradeExt.getTicket()));
		assertTrue(tradeExt.getStartDate().equals(sameTradeExt.getStartDate()));
		assertTrue(tradeExt.getEndDate().equals(sameTradeExt.getEndDate()));
		assertTrue(tradeExt.getBestPips() == sameTradeExt.getBestPips());
		assertTrue(tradeExt.getWorstPips() == sameTradeExt.getWorstPips());
		assertTrue(tradeExt.getNetPips() == sameTradeExt.getNetPips());
		assertTrue(tradeExt.getBestDollarLot() == sameTradeExt.getBestDollarLot());
		assertTrue(tradeExt.getWorstDollarLot() == sameTradeExt.getWorstDollarLot());
		assertTrue(tradeExt.getNetDollarLot() == sameTradeExt.getNetDollarLot());
		assertTrue(tradeExt.getCurrencyId() == sameTradeExt.getCurrencyId());
		assertTrue(tradeExt.getProviderId() == sameTradeExt.getProviderId());

	}
	
	@Test
	public void testTradeExtSaveAll() {
		List<TradeExtPojo> tradesExt = new ArrayList<TradeExtPojo>();
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/09/22 18:04:06:000"), date("2011/09/23 18:05:07:011"), 50, 500, -442, -4420, 33, 330, "545045340.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/09/23 17:18:45:000"), date("2011/09/23 18:05:07:011"), 100, 1000, -705, -7050, 11, 110, "510456450.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/09/24 14:04:06:000"), date("2011/09/24 18:06:07:011"), 400, 4000, -12, -120, 100, 1000, "514565650.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/09/27 16:04:06:000"), date("2011/10/01 18:05:07:011"), 2, 20, -100, -1, 0, 0, "545045340.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/01 16:04:06:000"), date("2011/10/01 18:05:07:011"), 72, 750, -402, -4020, 0, 0, "507863730.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/02 01:04:06:000"), date("2011/10/02 18:05:07:011"), 42, 420, -75, -750, 40, 400, "510397400.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/02 02:04:06:000"), date("2011/10/06 18:05:07:011"), 88, 880, -75, -750, 44, 440, "545370300.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/02 04:04:06:000"), date("2011/10/06 18:05:07:011"), 26, 260, -42, -420, 12, 120, "570307300.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/04 23:04:06:000"), date("2011/10/06 18:05:07:011"), 77, 770, -2, -20, new Float(12.1), new Float(121), "726003500.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/04 18:04:06:000"), date("2011/10/06 18:05:07:011"), 44, 440, -727, -7270, new Float(4.42), new Float(44.2), "807806303.020"));
		tradesExt.add(new TradeExtPojo(1, 2, date("2011/10/06 19:04:06:000"), date("2011/10/06 19:05:07:011"), 111, 1110, -75, -750, 33, 330, "879703500.020"));
		
		//Testing tradexExt save
		boolean ok = tradeExtDao.save(tradesExt);
		assertTrue(ok);
		
		//Getting ids of the newly added trades 
		List<Integer> ids = getIds(tradesExt);
		
		//Retrieving all trades
		List<TradeExtPojo> sameTradesExt = new ArrayList<TradeExtPojo>();
		for(int id : ids) {
			sameTradesExt.add(tradeExtDao.getTradeExt(id));
		}
		
		{
			//Testing if trades created here are equals to the same trades retrieved from database
			Iterator<TradeExtPojo> firstIter = tradesExt.iterator();
			Iterator<TradeExtPojo> secondIter = sameTradesExt.iterator();
			while(firstIter.hasNext()) {
				TradeExtPojo first = firstIter.next();
				TradeExtPojo second = secondIter.next();
				boolean isEqual = isEqual(first, second);
				assertTrue("Trades saved in the database are different from the same trades retrieved from the database", isEqual);
			}
		}
			
		{
			//retrieving trades another way and checking again
			SortedSet<TradeExtPojo> tmp = tradeExtDao.getTradesExt(2);
			
			Comparator<TradeExtPojo> tradeExtIdComparator = new Comparator<TradeExtPojo>() {
				@Override
				public int compare(TradeExtPojo o1, TradeExtPojo o2) {
					return o1.getId() - o2.getId();
				}
			};
			SortedSet<TradeExtPojo> sameTradesExtAgain = new TreeSet<TradeExtPojo>(tradeExtIdComparator);
			sameTradesExtAgain.addAll(tmp);
			
			Iterator<TradeExtPojo> firstIter = tradesExt.iterator();
			Iterator<TradeExtPojo> secondIter = sameTradesExtAgain.iterator();
			while(firstIter.hasNext()) {
				TradeExtPojo first = firstIter.next();
				TradeExtPojo second = secondIter.next();
				boolean isEqual = isEqual(first, second);
				assertTrue("Trades saved in the database are different from the same trades retrieved from the database", isEqual);
			}
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
	
	/**
	 * 
	 * @param first
	 * @param second
	 * @return true if the first and second have same caracteristics
	 */
	private boolean isEqual(TradeExtPojo first, TradeExtPojo second) {
		boolean isSame = true;
		isSame = isSame && first.getId() == second.getId();
		isSame = isSame && first.getTicket().equals(second.getTicket());
		isSame = isSame && first.getStartDate().equals(second.getStartDate());
		isSame = isSame && first.getEndDate().equals(second.getEndDate());
		isSame = isSame && first.getBestPips() == second.getBestPips();
		isSame = isSame && first.getWorstPips() == second.getWorstPips();
		isSame = isSame && first.getNetPips() == second.getNetPips();
		isSame = isSame && first.getBestDollarLot() == second.getBestDollarLot();
		isSame = isSame && first.getWorstDollarLot() == second.getWorstDollarLot();
		isSame = isSame && first.getNetDollarLot() == second.getNetDollarLot();
		isSame = isSame && first.getCurrencyId() == second.getCurrencyId();
		isSame = isSame && first.getProviderId() == second.getProviderId();
		
		return isSame;
	}
	
	private List<Integer> getIds(List<TradeExtPojo> tradesExt) {
		List<Integer> ids = new ArrayList<Integer>();
		for(TradeExtPojo tradeExt : tradesExt) {
			ids.add(tradeExt.getId());
		}
		return ids;
	}

}

package wasa.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public final class UniqueDateFactory implements IUniqueDateFactory {

	private static volatile int maxOffset = 0;
	
	private static SortedSet<Long> timeHour0 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour1 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour2 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour3 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour4 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour5 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour6 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour7 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour8 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour9 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour10 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour11 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour12 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour13 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour14 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour15 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour16 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour17 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour18 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour19 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour20 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour21 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour22 = new TreeSet<Long>();
	private static SortedSet<Long> timeHour23 = new TreeSet<Long>();
	
	@SuppressWarnings("serial")
	private static final Map<Integer, UniqueDate> uniqueDateInstancePerHour = new HashMap<Integer, UniqueDate>() {{
		put(0, new UniqueDate(0));
		put(1, new UniqueDate(1));
		put(2, new UniqueDate(2));
		put(3, new UniqueDate(3));
		put(4, new UniqueDate(4));
		put(5, new UniqueDate(5));
		put(6, new UniqueDate(6));
		put(7, new UniqueDate(7));
		put(8, new UniqueDate(8));
		put(9, new UniqueDate(9));
		put(10, new UniqueDate(10));
		put(11, new UniqueDate(11));
		put(12, new UniqueDate(12));
		put(13, new UniqueDate(13));
		put(14, new UniqueDate(14));
		put(15, new UniqueDate(15));
		put(16, new UniqueDate(16));
		put(17, new UniqueDate(17));
		put(18, new UniqueDate(18));
		put(19, new UniqueDate(19));
		put(20, new UniqueDate(20));
		put(21, new UniqueDate(21));
		put(22, new UniqueDate(22));
		put(23, new UniqueDate(23));
	}};
	
	@SuppressWarnings("serial")
	private final Map<Integer, SortedSet<Long>> ListTimesPerHour = new HashMap<Integer, SortedSet<Long>>() {{
		put(0, timeHour0);
		put(1, timeHour1);
		put(2, timeHour2);
		put(3, timeHour3);
		put(4, timeHour4);
		put(5, timeHour5);
		put(6, timeHour6);
		put(7, timeHour7);
		put(8, timeHour8);
		put(9, timeHour9);
		put(10, timeHour10);
		put(11, timeHour11);
		put(12, timeHour12);
		put(13, timeHour13);
		put(14, timeHour14);
		put(15, timeHour15);
		put(16, timeHour16);
		put(17, timeHour17);
		put(18, timeHour18);
		put(19, timeHour19);
		put(20, timeHour20);
		put(21, timeHour21);
		put(22, timeHour22);
		put(23, timeHour23);
	}};
		
	@Override
	public Date build(Date nonUniqueDate) {
		int hourOfDay = nonUniqueDate.getHours();
		Date uniqueDate = uniqueDateInstancePerHour.get(hourOfDay).build(nonUniqueDate, ListTimesPerHour);
		if(maxOffset > MAX_OFFSET_AUTHORIZED) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, 
					"unique date factory created a date that is more than " + 
					MAX_OFFSET_AUTHORIZED + " ms further than the original date");
		}
		return uniqueDate;
	}
	
	@Override
	public int getMaxOffset() {
		return maxOffset;
	}

	private final static class UniqueDate {
		
		private int hour;
		
		public UniqueDate(int hour) {
			this.hour = hour;
		}
		
		public synchronized Date build(final Date nonUniqueDate, final Map<Integer, SortedSet<Long>> listTimesPerHour) {
			return build(nonUniqueDate, listTimesPerHour, 0);
		}
		
		public Date build(Date nonUniqueDate, Map<Integer, SortedSet<Long>> listTimesPerHour, int offset) {
			SortedSet<Long> times = listTimesPerHour.get(hour);
			if(!times.contains(nonUniqueDate.getTime())) {
				times.add(nonUniqueDate.getTime());
				return nonUniqueDate;
			} else {
				long time = nonUniqueDate.getTime();
				while(times.contains(time)) {
					time++;
					offset++;
				}
				maxOffset = Math.max(offset, maxOffset);
				Date newDate = new Date(time);
				if(newDate.getHours() == hour) {
					times.add(time);
					return new Date(time);
				
				} else {
					return uniqueDateInstancePerHour.get(newDate.getHours()).build(newDate, listTimesPerHour, offset);
				}
			}
		}
	}


	
	
	
	
}

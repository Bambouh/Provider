package wasa.util;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class UniqueDateFactory implements IUniqueDateFactory {

	private static SortedSet<Long> timeHour0 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour1 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour2 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour3 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour4 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour5 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour6 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour7 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour8 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour9 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour10 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour11 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour12 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour13 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour14 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour15 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour16 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour17 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour18 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour19 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour20 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour21 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour22 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	private static SortedSet<Long> timeHour23 = Collections.synchronizedSortedSet(new TreeSet<Long>());
	
	@SuppressWarnings("serial")
	private Map<Integer, SortedSet<Long>> routingToSet = new HashMap<Integer, SortedSet<Long>>() {{
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
		@SuppressWarnings("deprecation")
		int hourOfDay = nonUniqueDate.getHours();
		SortedSet<Long> times = routingToSet.get(hourOfDay);
		if(!times.contains(nonUniqueDate)) {
			times.add(nonUniqueDate.getTime());
			return nonUniqueDate;
		} else {
			long time = nonUniqueDate.getTime();
			while(times.contains(time)) {
				time++;
			}
			times.add(time);
			return new Date(time);
		}
	}

}

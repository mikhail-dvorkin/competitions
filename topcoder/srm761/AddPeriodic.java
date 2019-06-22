package topcoder.srm761;
import java.util.*;

public class AddPeriodic {
	public String add(String s, String t) {
		long[] a = parsePeriodic(s);
		long[] b = parsePeriodic(t);
		return toPeriodic(a[0] * b[1] + a[1] * b[0], a[1] * b[1]);
	}
	
	long[] parsePeriodic(String s) {
		long floorLong = Long.parseLong(s.substring(0, s.indexOf(".")));
		String preperiod = s.substring(s.indexOf(".") + 1, s.indexOf("("));
		String period = s.substring(s.indexOf("(") + 1, s.length() - 1);
		long preperiodLong = Long.parseLong("0" + preperiod);
		long periodLong = Long.parseLong(period);
		long nines = Math.round(Math.pow(10, period.length())) - 1;
		long ten = Math.round(Math.pow(10, preperiod.length()));
		return new long[] {
				floorLong * nines * ten + preperiodLong * nines + periodLong,
				ten * nines
		};
	}
	
	String toPeriodic(long num, long den) {
		long floor = num / den;
		num %= den;
		Map<Long, Integer> seen = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; !seen.containsKey(num); i++) {
			seen.put(num, i);
			sb.append(num * 10 / den);
			num = (num * 10) % den;
		}
		int i = seen.get(num);
		return floor + "." + sb.substring(0, i) + "(" + sb.substring(i) + ")";
	}
}

package topcoder.srm374;
import java.util.*;

public class CommercialPlanner {
	public int bestMinute(int[] starts, int[] durations, int ourDuration, int dayl, int mem) {
		long day = dayl;
		int n = starts.length;
		long brem = -1;
		long btime = -1;
		kloop:
		for (int k = 0; k <= n; k++) {
			long start = 0;
			if (k < n)
				start = (starts[k] + (long) durations[k]) % day;
			ArrayList<Long> times = new ArrayList<Long>();
			for (int i = 0; i < n; i++) {
				long st = starts[i];
				while (st < start)
					st += day;
				if (st < start + ourDuration)
					continue kloop;
				if (st + durations[i] > start + day)
					continue kloop;
				times.add(st - start);
			}
			Collections.sort(times);
			long rtime;
			if (mem > n)
				rtime = day;
			else
				rtime = times.get(mem - 1);
			if ((rtime > brem) || ((rtime == brem) && (start < btime))) {
				brem = rtime;
				btime = start;
			}
		}
		return (int) btime;
	}

	public static void main(String[] args) {
//		int a = new CommercialPlanner().bestMinute(
//		new int[]{30, 6},
//		new int[]{10, 10},
//		4, 3600, 3
//		);
//		int b = new CommercialPlanner().bestMinute(
//				new int[]{1999999800, 1999999900},
//				new int[]{80, 239},
//				4, 2000000000, 1
//				);
		int a = new CommercialPlanner().bestMinute(
				new int[]{1999999800, 1999999900},
				new int[]{80, 200000239},
				4, 2000000000, 1
				);
		System.out.println(a);
	}
}

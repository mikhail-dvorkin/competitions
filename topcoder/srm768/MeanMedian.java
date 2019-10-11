package topcoder.srm768;

import java.util.Arrays;

public class MeanMedian {
	public int minEffort(int needMean, int needMedian, int[] d) {
		int n = d.length;
		Arrays.sort(d);
		int takeOverMedian = needMean * n - needMedian * (n / 2 + 1);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int grade = (i <= n / 2) ? needMedian : 0;
			int take = Math.max(Math.min(10 - grade, takeOverMedian), 0);
			grade += take;
			takeOverMedian -= take;
			ans += grade * d[i];
		}
		return ans;
	}
}

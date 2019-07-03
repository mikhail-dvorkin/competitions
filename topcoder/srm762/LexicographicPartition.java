package topcoder.srm762;

import java.util.*;

public class LexicographicPartition {
	private static final int CUT = 200;

	public int[] positiveSum(int n, int[] aPrefix, int seed, int aRange) {
		int[] a = Arrays.copyOf(aPrefix, n);
		long state = seed;
		for (int i = aPrefix.length; i < n; i++) {
			state = 1103515245 * state + 12345;
			a[i] = (int) (state % (2 * aRange + 1)) - aRange;
			state %= (1 << 31);
		}
		long[] cum = new long[n + 1];
		for (int i = 0; i < n; i++) {
			cum[i + 1] = cum[i] + a[i];
		}
		ArrayList<Integer> res = new ArrayList<>();
		main:
		for (int i = 0; i < n;) {
			for (int j = i + 1; j <= n; j++) {
				if (cum[j] > cum[i] && ((cum[n] > cum[j]) || (j == n))) {
					res.add(j - i);
					i = j;
					continue main;
				}
			}
			return new int[] {-1};
		}
		int take = Math.min(res.size(), CUT);
		int[] ans = new int[take + 1];
		ans[0] = res.size();
		for (int i = 0; i < take; i++) {
			ans[i + 1] = res.get(i);
		}
		return ans;
	}
}

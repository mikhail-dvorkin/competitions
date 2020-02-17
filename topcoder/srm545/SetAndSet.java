package topcoder.srm545;
import java.util.*;

public class SetAndSet {
	int n, m, mask;
	int[] a, na;
	boolean[] were;

	public long countandset(int[] A) {
		a = A;
		n = a.length;
		were = new boolean[n];
		na = new int[n];
		if (n == 1) {
			return 0;
		}
		int and = Integer.MAX_VALUE;
		for (int x : a) {
			and &= x;
		}
		for (int i = 0; i < n; i++) {
			a[i] ^= and;
			na[i] = ~a[i];
		}
		m = 20;
		long ans = 1L << n;
		for (mask = 1; mask < (1 << m); mask++) {
			Arrays.fill(were, false);
			int comps = 0;
			for (int i = 0; i < n; i++) {
				if (were[i]) {
					continue;
				}
				dfs(i);
				comps++;
			}
			long var = 1L << comps;
			int ones = 0;
			for (int i = 0; i < m; i++) {
				if (((mask >> i) & 1) != 0) {
					ones++;
				}
			}
			if ((ones & 1) == 0) {
				ans += var;
			} else {
				ans -= var;
			}
		}
		return ans;
	}

	private void dfs(int i) {
		were[i] = true;
		int t = mask & na[i];
		if (t == 0) {
			return;
		}
		for (int j = 0; j < n; j++) {
			if (!were[j] && ((t & na[j]) != 0)) {
				dfs(j);
			}
		}
	}

}

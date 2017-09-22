package topcoder;
import java.util.*;

public class PrefixFreeCode_slow {
	public int minCost(int n, int[] characterCosts) {
		int alphabet = characterCosts.length;
		int infty = Integer.MAX_VALUE;
		int[] a = new int[n + 1];
		for (int m = 2; m <= n; m++) {
			int[] b = new int[m + 1];
			int[] c = new int[m + 1];
			Arrays.fill(b, infty);
			b[0] = 0;
			for (int d = 0; d < alphabet; d++) {
				Arrays.fill(c, infty);
				for (int i = 0; i <= m; i++) {
					if (b[i] == infty) {
						continue;
					}
					for (int k = 0; (i + k <= m) && (k < m); k++) {
						int cur = b[i] + k * characterCosts[d] + a[k];
						c[i + k] = Math.min(c[i + k], cur);
					}
				}
				System.arraycopy(c, 0, b, 0, m + 1);
			}
			a[m] = c[m];
		}
		return a[n];
	}
}

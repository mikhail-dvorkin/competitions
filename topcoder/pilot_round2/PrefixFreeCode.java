package topcoder.pilot_round2;
import java.util.*;

public class PrefixFreeCode {
	public int minCost(int n, int[] characterCosts) {
		Arrays.sort(characterCosts);
		int alphabet = characterCosts.length;
		int infty = Integer.MAX_VALUE;
		int[] a = new int[n + 1];
		int[][] b = new int[alphabet + 1][n + 1];
		int[] kcost = new int[n + 1];
		for (int i = 0; i <= alphabet; i++) {
			Arrays.fill(b[i], infty);
		}
		b[0][0] = 0;
		for (int m = 2; m <= n; m++) {
			for (int d = 0; d < alphabet; d++) {
				int cost = characterCosts[alphabet - 1 - d];
				for (int k = 0; k < m; k++) {
					kcost[k] = k * cost + a[k];
				}
				for (int i = 0; i <= n; i++) {
					int bdi = b[d][i];
					if (bdi == infty) {
						continue;
					}
					for (int k = (m > 2) ? (m - 1) : 0; (i + k <= n) && (k < m); k++) {
						int cur = bdi + kcost[k];
						b[d + 1][i + k] = Math.min(b[d + 1][i + k], cur);
					}
				}
			}
			a[m] = b[alphabet][m];
		}
		return a[n];
	}
}

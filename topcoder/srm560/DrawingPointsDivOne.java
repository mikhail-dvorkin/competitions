package topcoder.srm560;
import java.util.*;

public class DrawingPointsDivOne {
	public int maxSteps(int[] x, int[] y) {
		int n = x.length;
		int s = 144;
		int m = 2 * s;
		int[][] d = new int[m + 1][m + 1];
		int[][] a = new int[m + 1][m + 1];
		for (int i = 0; i <= m; i++) {
			Arrays.fill(d[i], s);
		}
		for (int k = 0; k < n; k++) {
			x[k] += s / 2;
			y[k] += s / 2;
			for (int i = x[k]; i <= m; i++) {
				for (int j = y[k]; j <= m; j++) {
					d[i][j] = Math.min(d[i][j], Math.max(i - x[k], j - y[k]));
				}
			}
		}
		for (int k = 1; k < s; k++) {
			for (int i = m; i >= 0; i--) {
				for (int j = m; j >= 0; j--) {
					if (d[i][j] > k) {
						a[i][j] = 0;
					} else if (i == m || j == m) {
						a[i][j] = 1;
					} else {
						a[i][j] = Math.min(Math.min(a[i][j + 1], a[i + 1][j]), a[i + 1][j + 1]) + 1;
					}
					if (d[i][j] != 0 && a[i][j] >= k + 1) {
						return k - 1;
					}
				}
			}
		}
		return -1;
	}

}

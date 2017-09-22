package topcoder;
public class PolygonTraversal {
	public long count(int n, int[] points) {
		int init = 0;
		for (int i : points) {
			init |= 1 << (i - 1);
		}
		int MAX = (1 << n) - 1;
		long[][] a = new long[MAX + 1][n];
		a[init][points[points.length - 1] - 1] = 1;
		for (int m = 0; m <= MAX; m++) {
			for (int p = 0; p < n; p++) {
				long aa = a[m][p];
				if (aa == 0) {
					continue;
				}
				int q = p;
				for (;;) {
					q--;
					if (q < 0) {
						q = n - 1;
					}
					if (((m >> q) & 1) != 0) {
						break;
					}
				}
				int r = p;
				for (;;) {
					r++;
					if (r == n) {
						r = 0;
					}
					if (((m >> r) & 1) != 0) {
						break;
					}
				}
				while (r != q) {
					r++;
					if (r == n) {
						r = 0;
					}
					if (((m >> r) & 1) == 0) {
						a[m | (1 << r)][r] += aa;
					}
				}
			}
		}
		long ans = 0;
		int f = points[0] - 1;
		int f1 = (f + 1) % n;
		int f2 = (f + n - 1) % n;
		for (int p = 0; p < n; p++) {
			if (p == f1 || p == f2) {
				continue;
			}
			ans += a[MAX][p];
		}
		return ans;
	}

}

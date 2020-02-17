package topcoder.srm501;
import java.util.Arrays;

public class FoxAverageSequence {
	public int theCount(int[] seq) {
		int n = seq.length;
		int m = 40;
		int MODULO = 1000000007;
		int[][][] a = new int[(n - 1) * m + 1][m + 1][2];
		int[][][] b = new int[(n - 1) * m + 1][m + 1][2];
		a[0][0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int s = 0; s <= i * m; s++) {
				for (int x = 0; x <= m; x++) {
					for (int d = 0; d < 2; d++) {
						int v = a[s][x][d];
						if (v == 0) {
							continue;
						}
						int lo = 0;
						int hi = m;
						if (seq[i] >= 0) {
							lo = seq[i];
							hi = seq[i];
						}
						if (d > 0) {
							lo = Math.max(lo, x);
						}
						for (int y = lo; y <= hi; y++) {
							if (i * y > s + x) {
								break;
							}
							int dd = (x > y) ? 1 : 0;
							int ss = s + x;
							int u = b[ss][y][dd] + a[s][x][d];
							if (u >= MODULO) {
								u -= MODULO;
							}
							b[ss][y][dd] = u;
						}
					}
				}
			}
			int[][][] c = a;
			a = b;
			b = c;
			for (int s = 0; s < b.length; s++) {
				for (int x = 0; x <= m; x++) {
					Arrays.fill(b[s][x], 0);
				}
			}
		}
		int ans = 0;
		for (int s = 0; s <= (n - 1) * m; s++) {
			for (int x = 0; x <= m; x++) {
				for (int d = 0; d < 2; d++) {
					ans += a[s][x][d];
					if (ans >= MODULO) {
						ans -= MODULO;
					}
				}
			}
		}
		return ans;
	}

}

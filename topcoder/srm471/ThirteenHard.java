package topcoder;
public class ThirteenHard {
	int modulo = 13;
	
	public int calcTime(String[] city) {
		int n = city.length;
		int[][] edge = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				char c = city[i].charAt(j);
				if (c == '#') {
					edge[i][j] = -1;
				} else if (c >= 'a' && c <= 'z') {
					edge[i][j] = c - 'a' + 27;
				} else {
					edge[i][j] = c - 'A' + 1;
				}
			}
		}
		int MAX = 1 << (modulo - 1);
		int inf = Integer.MAX_VALUE / 2;
		int[][][] a = new int[MAX][modulo][n];
		for (int mask = 0; mask < MAX; mask++) {
			for (int mod = 0; mod < modulo; mod++) {
				for (int v = 0; v < n; v++) {
					a[mask][mod][v] = inf;
				}
			}
		}
		a[0][0][0] = 0;
		int ans = inf;
		for (int mask = 0; mask < MAX; mask++) {
			for (int mod = 0; mod < modulo; mod++) {
				for (int v = 0; v < n; v++) {
					if (a[mask][mod][v] == inf) {
						continue;
					}
					if (v == n - 1) {
						ans = Math.min(ans, a[mask][mod][v]);
					}
					for (int u = 0; u < n; u++) {
						if (edge[v][u] == -1) {
							continue;
						}
						int m = (mod + edge[v][u]) % modulo;
						if (m == 0) {
							continue;
						}
						if ((mask & (1 << (m - 1))) != 0) {
							continue;
						}
						int mask2 = mask | (1 << (m - 1));
						a[mask2][m][u] = Math.min(a[mask2][m][u], a[mask][mod][v] + edge[v][u]);
					}
				}
			}
		}
		if (ans == inf) {
			ans = -1;
		}
		return ans;
	}

}

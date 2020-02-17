package topcoder.tco2010.round1;
public class VacationTours {
	public int getIncome(String[] d1, String[] d2, int fee) {
		int n = d1.length;
		int[][] e = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				e[i][j] = get(d1[i].charAt(j)) * 64 + get(d2[i].charAt(j));
			}
		}
		int m = 2 * n;
		int[][] c = new int[m][m];
		int[][] f = new int[m][m];
		int[][] cost = new int[m][m];
		int[][] p = new int[m][m];
		for (int i = 1; i < n; i++) {
			c[2 * i - 1][2 * i] = 1;
			c[0][2 * i - 1] = 1;
			cost[0][2 * i - 1] = e[0][i];
			c[2 * i][m - 1] = 1;
			cost[2 * i][m - 1] = e[i][0];
			for (int j = 1; j < n; j++) {
				c[2 * i][2 * j - 1] = 1;
				cost[2 * i][2 * j - 1] = e[i][j];
			}
		}
		int ans = 0;
		int inf = Integer.MAX_VALUE / 3;
		e = new int[m][m];
		while (true) {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					p[i][j] = j;
					if (f[i][j] == c[i][j]) {
						e[i][j] = inf;
					} else {
						if (f[i][j] == 0) {
							e[i][j] = cost[i][j];
						} else {
							e[i][j] = -cost[j][i];
						}
					}
				}
			}
			for (int k = 0; k < m; k++) {
				for (int i = 0; i < m; i++) {
					for (int j = 0; j < m; j++) {
						if (e[i][k] + e[k][j] < e[i][j]) {
							e[i][j] = e[i][k] + e[k][j];
							p[i][j] = p[i][k];
						}
					}
				}
			}
			if (fee - e[0][m - 1] <= 0) {
				break;
			}
			ans += fee - e[0][m - 1];
			int i = 0;
			while (i != m - 1) {
				int j = p[i][m - 1];
				f[i][j]++;
				f[j][i]--;
				i = j;
			}
		}
		return ans;
	}

	private int get(char c) {
		if ('A' <= c && c <= 'Z') {
			return c - 'A';
		}
		if ('a' <= c && c <= 'z') {
			return c - 'a' + 26;
		}
		if ('0' <= c && c <= '9') {
			return c - '0' + 52;
		}
		if ('+' == c) {
			return 62;
		}
		if ('/' == c) {
			return 63;
		}
		throw new RuntimeException();
	}

}

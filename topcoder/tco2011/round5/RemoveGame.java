package topcoder.tco2011.round5;
public class RemoveGame {
	public long countWinning(String s, int r) {
		int n = s.length();
		long[][] c = new long[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			c[i][0] = c[i][i] = 1;
			for (int j = 1; j < i; j++) {
				c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
			}
		}
		long ans = 0;
		iloop:
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j < i && s.charAt(j) == 'x') {
					continue iloop;
				}
				if (j >= i && s.charAt(j) == 'o') {
					continue iloop;
				}
			}
			if (2 * i > n && i >= r) {
				ans++;
			}
		}
		if (n < 4) {
			return ans;
		}
		for (int i = 1; i + 3 <= n; i++) {
			jloop:
			for (int j = 1; i + j + 2 <= n; j++) {
				for (int k = 0; k < i; k++) {
					if (s.charAt(k) == 'x') {
						continue jloop;
					}
				}
				for (int k = 0; k < j; k++) {
					if (s.charAt(n - 1 - k) == 'o') {
						continue jloop;
					}
				}
				if (s.charAt(i) == 'o') {
					continue jloop;
				}
				if (s.charAt(n - 1 - j) == 'x') {
					continue jloop;
				}
				int os = 1;
				int xs = 1;
				int qs = 0;
				for (int k = i + 1; k < n - 1 - j; k++) {
					switch (s.charAt(k)) {
					case 'o': os++; break;
					case '?': qs++; break;
					case 'x': xs++; break;
					}
				}
				int m = os + xs + qs;
				for (int z = xs; 2 * z <= m && z <= xs + qs; z++) {
					int oo = i + (m - z) - (z - 1);
					int xx = j;
					System.out.println(oo + " " + xx);
					if (oo >= r && oo > xx) {
						ans += c[qs][z - xs];
					}
				}
			}
		}
		return ans;
	}

}

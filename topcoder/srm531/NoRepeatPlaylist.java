package topcoder.srm531;
public class NoRepeatPlaylist {
	long M = 1000000007;

	public int numPlaylists(int n, int m, int p) {
		long[][] a = new long[p + 1][n + 1];
		a[0][0] = 1;
		for (int i = 0; i < p; i++) {
			for (int j = 0; j <= n; j++) {
				a[i + 1][j] += a[i][j] * Math.max(j - m, 0);
				a[i + 1][j] %= M;
				if (j < n) {
					a[i + 1][j + 1] += a[i][j] * (n - j);
					a[i + 1][j + 1] %= M;
				}
			}
		}
		return (int) a[p][n];
	}

}

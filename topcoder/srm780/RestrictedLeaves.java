package topcoder.srm780;

public class RestrictedLeaves {
	int M = 1_000_000_007;

	public int count(int[] parent) {
		int n = parent.length;
		long[][][][] a = new long[n][2][2][2];
		long ans = 0;
		for (int v = n - 1; v >= 0; v--) {
			boolean first = true;
			a[v][0][0][0] = a[v][1][1][1] = 1;
			for (int u = v + 1; u < n; u++) {
				if (parent[u] != v) continue;
				long[][][] b = new long[2][2][2];
				ans = 0;
				for (int left = 0; left < 2; left++) {
					for (int right = 0; right < 2; right++) {
						if (first) {
							b[1][left][right] = a[u][0][left][right];
							b[0][left][right] = b[1][left][right] + a[u][1][left][right];
						} else {
							b[0][left][right] = a[v][0][left][0] * (a[u][0][0][right] + a[u][0][1][right] + a[u][1][0][right] + a[u][1][1][right]);
							b[0][left][right] += a[v][0][left][1] * (a[u][0][0][right] + a[u][1][0][right]);
							b[1][left][right] = a[v][1][left][0] * (a[u][0][0][right] + a[u][0][1][right]);
							b[1][left][right] += a[v][1][left][1] * a[u][0][0][right];
						}
					}
					for (int right = 0; right < 2; right++) {
						for (int top = 0; top < 2; top++) {
							a[v][top][left][right] = b[top][left][right] % M;
							if (left + right < 2) {
								ans += a[v][top][left][right];
							}
						}
					}
				}
				first = false;
			}
		}
		return (int) (ans % M);
	}
}

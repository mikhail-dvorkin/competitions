package topcoder.srm780;

public class RestrictedLeaves {
	int M = 1_000_000_007;

	public int count(int[] parent) {
		int n = parent.length;
		long[][][][] a = new long[2][2][2][n];
		for (int v = n - 1; v >= 0; v--) {
			int kids = 0;
			for (int u = v + 1; u < n; u++) {
				if (parent[u] != v) continue;
				kids++;
			}
			if (kids == 0) {
				a[0][0][0][v] = 1;
				a[1][1][1][v] = 1;
				continue;
			}
			boolean first = true;
			for (int u = v + 1; u < n; u++) {
				if (parent[u] != v) continue;
				if (first) {
					a[0][0][0][v] = (a[0][0][0][u] + a[1][0][0][u]) % M;
					a[0][0][1][v] = (a[0][0][1][u] + a[1][0][1][u]) % M;
					a[0][1][0][v] = (a[0][1][0][u] + a[1][1][0][u]) % M;
					a[0][1][1][v] = (a[0][1][1][u] + a[1][1][1][u]) % M;
					a[1][0][0][v] = a[0][0][0][u];
					a[1][0][1][v] = a[0][0][1][u];
					a[1][1][0][v] = a[0][1][0][u];
					a[1][1][1][v] = a[0][1][1][u];
					first = false;
					continue;
				}
				long[][][] b = new long[2][2][2];
				b[0][0][0] = (a[0][0][0][v] * (a[0][0][0][u] + a[0][1][0][u] + a[1][0][0][u] + a[1][1][0][u]) + a[0][0][1][v] * (a[0][0][0][u] + a[1][0][0][u])) % M;
				b[0][0][1] = (a[0][0][0][v] * (a[0][0][1][u] + a[0][1][1][u] + a[1][0][1][u] + a[1][1][1][u]) + a[0][0][1][v] * (a[0][0][1][u] + a[1][0][1][u])) % M;
				b[0][1][0] = (a[0][1][0][v] * (a[0][0][0][u] + a[0][1][0][u] + a[1][0][0][u] + a[1][1][0][u]) + a[0][1][1][v] * (a[0][0][0][u] + a[1][0][0][u])) % M;
				b[0][1][1] = (a[0][1][0][v] * (a[0][0][1][u] + a[0][1][1][u] + a[1][0][1][u] + a[1][1][1][u]) + a[0][1][1][v] * (a[0][0][1][u] + a[1][0][1][u])) % M;
				b[1][0][0] = (a[1][0][0][v] * (a[0][0][0][u] + a[0][1][0][u]) + a[1][0][1][v] * a[0][0][0][u]) % M;
				b[1][0][1] = (a[1][0][0][v] * (a[0][0][1][u] + a[0][1][1][u]) + a[1][0][1][v] * a[0][0][1][u]) % M;
				b[1][1][0] = (a[1][1][0][v] * (a[0][0][0][u] + a[0][1][0][u]) + a[1][1][1][v] * a[0][0][0][u]) % M;
				b[1][1][1] = (a[1][1][0][v] * (a[0][0][1][u] + a[0][1][1][u]) + a[1][1][1][v] * a[0][0][1][u]) % M;
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						for (int k = 0; k < 2; k++) {
							a[i][j][k][v] = b[i][j][k];
						}
					}
				}
			}
		}
		long ans = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					if (j + k == 2) continue;
					ans = (ans + a[i][j][k][0]) % M;
				}
			}
		}
		return (int) ans;
	}

	public static void main(String[] args) {
		System.out.println(new RestrictedLeaves().count(new int[] {-1, 0, 0, 0, 0}));
		System.out.println(new RestrictedLeaves().count(new int[] {-1, 0, 0, 1, 2, 1, 2, 1, 4, 4}));
	}
}

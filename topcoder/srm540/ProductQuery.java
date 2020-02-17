package topcoder.srm540;
public class ProductQuery {
	final int MOD = 1000000007;
	final long R4 = (MOD + 1) / 4;

	public int theInput(int N, int[] from, int[] to, int[] prod) {
		n = N;
		int m = from.length;
		int[][][] a = new int[n + 1][n + 1][n + 1];
		a[0][0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int x = 0; x <= i; x++) {
				for (int y = 0; y <= i; y++) {
					if (a[i][x][y] == 0) {
						continue;
					}
					dloop:
					for (int d = 0; d < 4; d++) {
						int xx, yy, c;
						if (d < 2) {
							xx = 0;
						} else {
							xx = x + 1;
						}
						if (d % 2 == 0) {
							yy = 0;
							c = 1;
						} else {
							yy = y + 1;
							c = 4;
						}
						for (int q = 0; q < m; q++) {
							if (to[q] != i) {
								continue;
							}
							int len = to[q] + 1 - from[q];
							if (prod[q] % 5 == 0 && yy >= len) {
								continue dloop;
							}
							if (prod[q] % 5 != 0 && yy < len) {
								continue dloop;
							}
							if (prod[q] % 2 == 0 && xx >= len) {
								continue dloop;
							}
							if (prod[q] % 2 != 0 && xx < len) {
								continue dloop;
							}
						}
						a[i + 1][xx][yy] = (int) ((a[i + 1][xx][yy] + c * (long) a[i][x][y]) % MOD);
					}
				}
			}
		}
		int ans = 0;
		for (int x = 0; x <= n; x++) {
			for (int y = 0; y <= n; y++) {
				ans += a[n][x][y];
				ans %= MOD;
			}
		}
		div = 0;
		e = new int[n + 1][n + 1];
		val = new int[n + 1];
		dead = false;
		for (int q = 0; q < m; q++) {
			if (prod[q] % 5 == 0) {
				continue;
			}
			int f = from[q];
			int t = to[q] + 1;
			int p = prod[q];
			if (p % 2 == 0) {
				p = (p + 5) % 10;
			}
			if (e[f][t] != 0 && e[f][t] != p) {
				dead = true;
			}
			e[f][t] = p;
			e[t][f] = reverse(p);
		}
		for (int i = 0; i <= n; i++) {
			if (val[i] == 0) {
				val[i] = 1;
				dfs(i);
			}
		}
		if (dead) {
			return 0;
		}
		for (int i = 0; i < div; i++) {
			ans = (int) ((ans * R4) % MOD);
		}
		return ans;
	}

	private int reverse(int p) {
		if (p == 1 || p == 9) {
			return p;
		}
		if (p == 3 || p == 7) {
			return 10 - p;
		}
		throw new RuntimeException();
	}

	private void dfs(int v) {
		for (int u = 0; u <= n; u++) {
			if (e[v][u] == 0) {
				continue;
			}
			int vv = (val[v] * e[v][u]) % 10;
			if (val[u] == 0) {
				val[u] = vv;
				dfs(u);
				div++;
				continue;
			}
			if (val[u] != vv) {
				dead = true;
			}
		}
	}

	int n;
	int[][] e;
	int[] val;
	boolean dead;
	int div;

}

package topcoder;
public class Spacetsk {
	public int countsets(int L, int H, int k) {
		final long MOD = 1000000007;
		if (k == 1) {
			return (L + 1) * (H + 1);
		}
		int[][] c = new int[3000][k + 1];
		for (int i = 0; i < 3000; i++) {
			c[i][0] = 1;
			if (i <= k) {
				c[i][i] = 1;
			}
			for (int j = 1; j < i && j <= k; j++) {
				c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]);
				if (c[i][j] >= MOD) {
					c[i][j] -= MOD;
				}
			}
		}
		k--;
		long ans = 0;
		for (int dx = 0; dx <= L; dx++) {
			for (int dy = 1; dy <= H; dy++) {
				if (gcd(dx, dy) != 1) {
					continue;
				}
				int x = 0;
				int y = 0;
				for (int t = 1;; t++) {
					x += dx;
					if (x > L) {
						break;
					}
					y += dy;
					if (y > H) {
						break;
					}
					if (t >= k) {
						long v = c[t][k];
						v *= L - x + 1;
						v %= MOD;
						if (dx != 0) {
							v *= 2;
							if (v >= MOD) {
								v -= MOD;
							}
						}
						ans += v;
						if (ans >= MOD) {
							ans -= MOD;
						}
					}
				}
			}
		}
		return (int) ans;
	}
	
	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}


}

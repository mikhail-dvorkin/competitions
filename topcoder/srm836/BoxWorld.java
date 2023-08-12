public class BoxWorld {
	final static int M = 1_000_000_007;

	public int count(int hei, int wid, int cubes) {
		int maxChess = (hei * wid + 1) / 2;
		int waysChess = 2 - (hei * wid) % 2;
		if (cubes >= maxChess) {
			int x = maxChess - 1;
			int y = cubes - maxChess;
			int r = waysChess * factorial(x + y);
			r = (int) (r * ((long) inv(factorial(x))) % M);
			r = (int) (r * ((long) inv(factorial(y))) % M);
			return r;
		}
		int[][] a = new int[1 << hei][cubes + 1];
		a[0][0] = 1;
		for (int j = 0; j < wid; j++) {
			for (int i = 0; i < hei; i++) {
				int[][] b = new int[1 << hei][cubes + 1];
				for (int mask = 0; mask < b.length; mask++) {
					for (int c = 0; c <= cubes; c++) {
						int aHere = a[mask][c];
						if (aHere == 0) continue;
						int nm = mask & ~(1 << i);
						b[nm][c] = (b[nm][c] + aHere) % M;
						if ((mask & (1 << i)) == 0) {
							if (i == 0 || (mask & (1 << (i - 1))) == 0) {
								if (c + 1 <= cubes) {
									nm = mask | (1 << i);
									b[nm][c + 1] = (b[nm][c + 1] + aHere) % M;
								}
							}
						}
					}
				}
				a = b;
			}
		}
		int ans = 0;
		for (int mask = 0; mask < a.length; mask++) {
			ans = (ans + a[mask][cubes]) % M;
		}
		return ans;
	}

	public static int factorial(int n) {
		int r = 1;
		for (int i = 2; i <= n; i++) {
			r = (int) ((r * (long) i) % M);
		}
		return r;
	}

	public static int inv(int n) {
		return modInverse(n, M);
	}

	public static int gcdExtended(int a, int b, int[] xy) {
		if (a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		int d = gcdExtended(b % a, a, xy);
		int t = xy[0];
		xy[0] = xy[1] - (b / a) * xy[0];
		xy[1] = t;
		return d;
	}

	public static int modInverse(int x, int p) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, p, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + p);
		}
		int result = xy[0] % p;
		if (result < 0) {
			result += p;
		}
		return result;
	}
}

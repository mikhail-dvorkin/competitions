package topcoder.srm730;
public class ExpectedMinimumPower_slow {
	static final int MOD = 1_000_000_007;

	public int findExp(int n, int x) {
		int inv2 = (MOD + 1) / 2;
		int t = 1;
		for (int i = 0; i < n + 1 - x; i++) {
			t = (2 * t) % MOD;
		}
		int ans = t;
		for (int i = 1; i <= n - x; i++) {
			t = (int) ((t * (long) inv2) % MOD);
			t = (int) ((t * (long) (i + x - 1)) % MOD);
			t = (int) ((t * (long) modInverse(i)) % MOD);
			ans = (ans + t) % MOD;
		}
		return ans;
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

	public static int modInverse(int x) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, MOD, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + MOD);
		}
		int result = xy[0] % MOD;
		if (result < 0) {
			result += MOD;
		}
		return result;
	}
}

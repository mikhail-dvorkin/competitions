package topcoder.srm768;

import java.math.BigInteger;

public class PBG {
	static final int M = 1000000007;

	public int findEV(int medium, int weak, int strong) {
		int n = medium + weak + strong;
		int[][] a = new int[n + 1][n + 1];
		a[1][0] = 1;
		for (int i = 2; i <= n; i++) {
			int pairs = i * (i - 1) / 2;
			int pairsRev = modInverse(pairs);
			for (int j = 0; j < i; j++) {
				long res = j * i + (pairs - j * (j + 1L) / 2) * a[i - 1][j];
				if (j > 0) {
					res += j * (j - 1L) / 2 * a[i - 1][j - 1];
				}
				a[i][j] = (int) (res % M * pairsRev % M);
			}
		}
		long ans = 0;
		for (int i = 0; i < medium; i++) {
			ans += a[n][strong + i];
		}
		return (int) (ans % M * modInverse(medium) % M);
	}

	static int modInverse(int x) {
		return BigInteger.valueOf(x).modInverse(BigInteger.valueOf(M)).intValue();
	}
}

package topcoder.srm762;

import java.math.BigInteger;

public class StrawberryHard {
	private static final int M = 1_000_000_007;

	public int competitive(int n, int k, int aRange, int bRange, int seed) {
		int[] a = new int[2 * k + 1];
		int[] b = new int[2 * k + 1];
		int aSum = 0, bSum = 0;
		long state = seed;
		for (int i = 0; i <= 2 * k; i++) {
			state = 1103515245 * state + 12345;
			state %= 1 << 31;
			a[i] = (int) (state % aRange);
			aSum = (aSum + a[i]) % M;
			state = 1103515245 * state + 12345;
			state %= 1 << 31;
			b[i] = (int) (state % bRange);
			bSum = (bSum + b[i]) % M;
		}
		int aSumInv = BigInteger.valueOf(aSum).modInverse(BigInteger.valueOf(M)).intValue();
		int bSumInv = BigInteger.valueOf(bSum).modInverse(BigInteger.valueOf(M)).intValue();
		for (int i = 0; i <= 2 * k; i++) {
			a[i] = (int) ((a[i] * (long) aSumInv) % M);
			b[i] = (int) ((b[i] * (long) bSumInv) % M);
		}
		int[] p = new int[2 * k + 1];
		p[k] = 1;
		for (int round = 0; round < n; round++) {
			int[] c = (round % 2 == 0) ? a : b;
			int[] q = new int[2 * k + 1];
			for (int x = 0; x <= 2 * k; x++) {
				if (p[x] == 0) {
					continue;
				}
				int xRev = 2 * k - x;
				for (int y = 0; y <= xRev; y++) {
					q[y] = (int) ((q[y] + p[x] * (long) c[xRev - y]) % M);
				}
			}
			p = q;
		}
		int ans = 0;
		for (int x : p) {
			ans = (ans + x) % M;
		}
		return ans;
	}
}

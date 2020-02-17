package topcoder.srm691;
public class Sunnygraphs {
	public long count(int[] a) {
		int n = a.length;
		long[] set = new long[n];
		for (int i = 0; i < 2; i++) {
			int j = i;
			for (int k = 0; k < n; k++) {
				set[i] |= 1L << j;
				j = a[j];
			}
		}
		long u0 = (1L << Long.bitCount(set[0] & ~set[1])) - 1;
		long u1 = (1L << Long.bitCount(set[1] & ~set[0])) - 1;
		long other = 1L << (n - Long.bitCount(set[0] | set[1]));
		if ((set[0] & set[1]) == 0) {
			return u0 * u1 * other;
		}
		return (1L << n) - (u0 + u1) * other;
	}
}

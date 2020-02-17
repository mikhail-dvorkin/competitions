package topcoder.srm649;
public class XorSequence {
	int m;
	int[] a, b, c;
	long[] x, y;

	public long getmax(int N, int sz, int A0, int A1, int P, int Q, int R) {
		a = new int[sz];
		b = new int[sz];
		c = new int[sz];
		a[0] = A0;
		a[1] = A1;
		for (int i = 2; i < sz; i++) {
		    a[i] = (int) ((a[i - 2] * (long) P + a[i - 1] * (long) Q + R) % N);
		}
		m = 0;
		while ((1 << m) != N) {
			m++;
		}
		x = new long[m];
		y = new long[m];
		solve(0, sz, m - 1);
		long ans = 0;
		for (int i = 0; i < m; i++) {
			ans += Math.max(x[i], y[i]);
		}
		return ans;
	}

	void solve(int from, int to, int k) {
		if (k < 0 || to - from < 2) {
			return;
		}
		int zeros = 0;
		int ones = 0;
		long cur = 0;
		for (int i = from; i < to; i++) {
			if (((a[i] >> k) & 1) != 0) {
				c[ones] = a[i];
				ones++;
				cur += zeros;
			} else {
				b[zeros] = a[i];
				zeros++;
			}
		}
		x[k] += cur;
		y[k] += ones * (long) zeros - cur;
		System.arraycopy(b, 0, a, from, zeros);
		System.arraycopy(c, 0, a, from + zeros, ones);
		solve(from, from + zeros, k - 1);
		solve(from + zeros, to, k - 1);
	}
}

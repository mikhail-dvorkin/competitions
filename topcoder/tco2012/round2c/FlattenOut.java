package topcoder;
public class FlattenOut {
	public long[] simulateIt(long[] a, long t) {
		int n = a.length;
		boolean[] p = new boolean[n];
		while (t > 0) {
			long step = t;
			int pos = 0;
			boolean cycle = true;
			for (int i = 0; i < n; i++) {
				p[i] = (a[i] > 0);
				if (p[i]) {
					pos++;
				}
				if (a[i] > 1 || a[i] < 0) {
					cycle = false;
				}
			}
			if (cycle && t >= n) {
				t %= n;
				continue;
			}
			if (pos == 0 || pos == n) {
				break;
			}
			for (int i = 0; i < n; i++) {
				int ii = (i == 0) ? (n - 1) : (i - 1);
				if (p[i] ^ p[ii]) {
					if (p[i]) {
						step = Math.min(step, a[i]);
					} else {
						step = Math.min(step, 1-a[i]);
					}
				}
			}
			for (int i = 0; i < n; i++) {
				int ii = (i == 0) ? (n - 1) : (i - 1);
				if (p[i] ^ p[ii]) {
					if (p[i]) {
						a[i] -= step;
					} else {
						a[i] += step;
					}
				}
			}
			t -= step;
			if (t == 0) {
				break;
			}
		}
		return a;
	}

}

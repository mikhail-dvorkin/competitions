package topcoder;
import java.util.*;

public class ThreePoints {

	public long countColoring(int n, int xzero, int xmul, int xadd, int xmod, int yzero, int ymul, int yadd, int ymod) {
		int[] x = new int[n];
		int[] y = new int[n];
		x[0] = xzero;
		y[0] = yzero;
		for (int i = 1; i < n; i++) {
			x[i] = (int) ((x[i - 1] * (long) xmul + xadd) % xmod);
			y[i] = (int) ((y[i - 1] * (long) ymul + yadd) % ymod);
		}
		int[] xs = x.clone();
		int[] ys = y.clone();
		Arrays.sort(xs);
		Arrays.sort(ys);
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = Arrays.binarySearch(xs, x[i]);
			y[i] = Arrays.binarySearch(ys, y[i]);
			p[x[i]] = n - 1 - y[i];
		}
		long ans = 0;
		FenwickTree a = new FenwickTree(n);
		FenwickTree b = new FenwickTree(n);
		for (int i = n - 1; i >= 0; i--) {
			int h = p[i];
			long below = a.sum(h);
			ans += below * (below - 1) / 2 - b.sum(h);
			a.add(h, 1);
			b.add(h, below);
		}
		return ans;
	}

	public class FenwickTree {
		long[] t;
		int n;

		public FenwickTree(int n) {
			this.n = n;
			t = new long[n];
		}

		public void add(int i, long value) {
			for (; i < n; i += (i + 1) & -(i + 1)) {
				t[i] += value;
			}
		}

		public long sum(int i) {
			long res = 0;
			for (; i >= 0; i -= (i + 1) & -(i + 1)) {
				res += t[i];
			}
			return res;
		}
	}
	
}

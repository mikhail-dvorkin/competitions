package topcoder;
import java.util.*;

public class HeightRound {
	int n, d;
	int[] h, a;
	boolean[] were;
	
	public int[] getBestRound(int[] he) {
		h = he;
		n = h.length;
		were = new boolean[n];
		a = new int[n];
		Arrays.sort(h);
		int le = -1;
		int ri = 1000;
		while (le + 1 < ri) {
			int mi = (le + ri) / 2;
			d = mi;
			if (p())
				ri = mi;
			else
				le = mi;
		}
		d = ri;
		p();
		return a;
	}

	private boolean p() {
		Arrays.fill(were, false);
		were[0] = true;
		a[0] = h[0];
		xloop:
		for (int x = 1; x < n; x++) {
			for (int i = 1; i < n; i++) {
				if (were[i])
					continue;
				if (Math.abs(a[x - 1] - h[i]) > d)
					continue;
				were[i] = true;
				a[x] = h[i];
				if (poss(h[i], n - x - 1))
					continue xloop;
				were[i] = false;
			}
			return false;
		}
		return true;
	}

	private boolean poss(int b, int l) {
		if (l == 0)
			return Math.abs(b - h[0]) <= d;
		int[] aa = new int[l];
		l = 0;
		for (int i = 0; i < n; i++) {
			if (!were[i])
				aa[l++] = h[i];
		}
		if (aa[0] > h[0] + d)
			return false;
		int lo = 0;
		while (lo + 1 < l && aa[lo + 1] < b) {
			if (Math.abs(aa[lo + 1] - aa[lo]) > d)
				return false;
			lo++;
		}
		int x = b;
		int y = aa[lo];
		if (x > y) {
			int t = x; x = y; y = t;
		}
		for (int i = lo + 1; i < l; i++) {
			if (Math.abs(x - aa[i]) > d)
				return false;
			x = aa[i];
			if (x > y) {
				int t = x; x = y; y = t;
			}
		}
		return Math.abs(x - y) <= d;
	}
	
	public static void main(String[] args) {
		new HeightRound().getBestRound(new int[]{239, 239, 567, 566, 1, 2, 1, 3, 4, 4, 5, 6, 5, 6, 7, 8, 7, 8, 11, 100, 567, 568, 239, 239, 566, 566});
	}
}

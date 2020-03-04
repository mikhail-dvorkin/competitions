package topcoder.humblefool2020;

public class CardDrawPoints {
	public double expectedScore(int[] count) {
		int n = count.length;
		double[] a = new double[1 << n];
		main:
		for (int mask = a.length - 1; mask >= 0; mask--) {
			int options = 0;
			int have = 0;
			double canHave = 0;
			for (int i = 0; i < n; i++) {
				int c = count[i];
				if (((mask >> i) & 1) == 1) {
					c--;
					have += i;
				} else {
					canHave += a[mask | (1 << i)] * c;
				}
				if (c < 0) continue main;
				options += c;
			}
			if (options == 0) canHave = 0; else canHave /= options;
			a[mask] = Math.max(have, canHave);
		}
		return a[0];
	}
}

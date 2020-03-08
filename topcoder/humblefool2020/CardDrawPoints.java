package topcoder.humblefool2020;

public class CardDrawPoints {
	public double expectedScore(int[] count) {
		int n = count.length;
		double[] a = new double[1 << n];
		for (int mask = a.length - 1; mask >= 0; mask--) {
			int options = 0;
			int have = 0;
			for (int i = 0; i < n; i++) {
				options += count[i];
				if (((mask >> i) & 1) == 0) {
					a[mask] += a[mask | (1 << i)] * count[i];
					continue;
				}
				have += i;
				options--;
			}
			a[mask] = options == 0 ? have : Math.max(have, a[mask] / options);
		}
		return a[0];
	}
}

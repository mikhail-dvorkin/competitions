package topcoder;
public class MuddyRoad {
	public double getExpectedValue(int[] road) {
		int n = road.length;
		double[] p = new double[n];
		for (int i = 0; i < n; i++) {
			p[i] = road[i] / 100.0;
		}
		double[] a = new double[n];
		for (int i = 1; i < n; i++) {
			double pmud = 1;
			for (int j = i - 1; j >= 0; j--) {
				int cost = (i - j - 1) / 2;
				a[i] += (1 - p[j]) * pmud * (a[j] + cost);
				pmud *= p[j];
			}
		}
		return a[n - 1];
	}

}

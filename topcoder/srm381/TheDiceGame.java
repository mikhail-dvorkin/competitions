package topcoder.srm381;
public class TheDiceGame {
	public double expectedThrows(int n) {
		double[] a = new double[n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = i - 1; j >= i - 6; j--) {
				if (j <= 0)
					a[i]++;
				else
					a[i] += 1 + a[j];
			}
			a[i] /= 6;
		}
		return a[n];
	}
}

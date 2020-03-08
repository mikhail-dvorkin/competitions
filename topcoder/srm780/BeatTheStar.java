package topcoder.srm780;

public class BeatTheStar {
	public double doesItMatter(int n, int g) {
		g--;
		double[] p = new double[] {1};
		for (int i = 0; i < n; i++) {
			if (i == g) {
				continue;
			}
			double[] q = new double[p.length + i + 1];
			for (int j = 0; j < p.length; j++) {
				q[j] += p[j] * 0.5;
				q[j + i + 1] += p[j] * 0.5;
			}
			p = q;
		}
		double ans = 0;
		int total = n * (n + 1) / 2;
		for (int j = 0; j < p.length; j++) {
			if (2 * j < total && 2 * (j + g + 1) > total) {
				ans += p[j];
			}
		}
		return ans;
	}
}

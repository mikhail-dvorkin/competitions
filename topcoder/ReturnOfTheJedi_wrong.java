package topcoder;
import java.util.*;


public class ReturnOfTheJedi_wrong {
	public double[] minimalExpectation(int[] x, int[] pInt) {
		int n = x.length;
		double[] p = new double[n];
		for (int i = 0; i < n; i++) {
			p[i] = pInt[i] / 100_000.0;
		}
		double[] ans = new double[n];
		Random r = new Random(566);
		for (int k = 1; k <= n; k++) {
			ans[k - 1] = 1e100;
			for (int t = 0; t < n; t++) {
				boolean[] take = new boolean[n];
				double prod = 1;
				long sum = 0;
				for (int i = 0; i < k; i++) {
					int j;
					do {
						j = r.nextInt(n);
					} while (take[j]);
					take[j] = true;
					prod *= p[j];
					sum += x[j];
				}
				loop:
				for (;;) {
					for (int i = 0; i < n; i++) {
						if (!take[i]) {
							continue;
						}
						for (int j = 0; j < n; j++) {
							if (take[j]) {
								continue;
							}
							double pp = prod / p[i] * p[j];
							long ss = sum - x[i] + x[j];
							if (pp * ss < prod * sum) {
								take[i] = false;
								take[j] = true;
								prod = pp;
								sum = ss;
								continue loop;
							}
						}
					}
					break;
				}
				ans[k - 1] = Math.min(ans[k - 1], prod * sum);
			}
		}
		return ans;
	}

}

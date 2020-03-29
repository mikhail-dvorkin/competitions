package topcoder.srm782;

import java.util.Arrays;

public class EmptyTheBox {
	public double minExpectedPenalty(int d, int t) {
		double[] prob = new double[2 * d + 1];
		for (int thrown = 2; thrown <= 2 * d; thrown++) {
			for (int i = 1; i <= d; i++) {
				if (thrown - i >= 1 && thrown - i <= d) {
					prob[thrown]++;
				}
			}
			prob[thrown] /= d * d;
		}

		int[] sum = new int[1 << (2 * d)];
		double[] win = new double[1 << (2 * d)];
		for (int mask = 0; mask < (1 << (2 * d)); mask++) {
			for (int i = 0; i < 2 * d; i++) {
				if (((mask >> i) & 1) == 0) continue;
				sum[mask] += i + 1;
			}
			double[] best = new double[2 * d + 1];
			Arrays.fill(best, -1);
			for (int rem = 0; rem <= mask; rem++) {
				if ((mask & rem) != rem) continue;
				if (sum[rem] > 2 * d || sum[rem] < 2) continue;
				best[sum[rem]] = Math.max(best[sum[rem]], win[mask ^ rem]);
			}
			for (int i = 2; i <= 2 * d; i++) {
				if (best[i] > -1) {
					win[mask] += prob[i] * (i + best[i]);
				}
			}
		}

		double ans = t * (t + 1) / 2.0 - win[(1 << Math.min(2 * d, t)) - 1];
		return ans;
	}
}

package topcoder.srm782;

import java.util.*;

public class EmptyTheBox {
	public double minExpectedPenalty(int d, int t) {
		double[] prob = new double[2 * d + 1];
		for (int i = 1; i <= d; i++) {
			for (int j = 1; j <= d; j++) {
				prob[i + j] += 1.0 / d / d;
			}
		}
		double[] win = new double[1 << (2 * d)];
		int[] sum = new int[win.length];
		double[] best = new double[2 * d + 1];
		for (int mask = 1; mask < win.length; mask++) {
			int bit = Integer.numberOfTrailingZeros(mask);
			sum[mask] = sum[mask ^ (1 << bit)] + bit + 1;
			Arrays.fill(best, -1);
			for (int rem = mask; rem > 0; rem = (rem - 1) & mask) {
				if (sum[rem] > 2 * d || sum[rem] < 2) continue;
				best[sum[rem]] = Math.max(best[sum[rem]], win[mask ^ rem]);
			}
			for (int i = 2; i <= 2 * d; i++) {
				if (best[i] > -1) {
					win[mask] += prob[i] * (i + best[i]);
				}
			}
		}
		return t * (t + 1.0) / 2 - win[(1 << Math.min(2 * d, t)) - 1];
	}
}

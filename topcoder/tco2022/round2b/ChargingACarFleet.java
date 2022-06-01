package topcoder.tco2022.round2b;
import java.util.Arrays;

public class ChargingACarFleet {
	public long[] charge(int[] chargingTime, int[] penalty, int[] carSize, int[] spotAvailable, int[] spotSize) {
		int n = carSize.length;
		int m = spotSize.length;
		int masks = 1 << n;
		long inf = Long.MAX_VALUE;

		int[] neededSpotSize = new int[masks];
		long[] sumPenalties = new long[masks];
		long[] chargingPenalty = new long[masks];
		int[][] order = new int[masks][];
		for (int mask = 0; mask < masks; mask++) {
			int bestFirst = -1;
			long best = inf;
			for (int j = 0; j < n; j++) {
				if (((mask >> j) & 1) == 0) {
					continue;
				}
				int maskOther = mask ^ (1 << j);
				long cost = chargingTime[j] * (penalty[j] + sumPenalties[maskOther]) + chargingPenalty[maskOther];
				if (cost < best) {
					best = cost;
					bestFirst = j;
				}
				neededSpotSize[mask] = Math.max(neededSpotSize[mask], carSize[j]);
				sumPenalties[mask] += penalty[j];
			}
			int size = Integer.bitCount(mask);
			order[mask] = new int[size];
			if (size > 0) {
				order[mask][0] = bestFirst;
				int maskOther = mask ^ (1 << bestFirst);
				System.arraycopy(order[maskOther], 0, order[mask], 1, size - 1);
				chargingPenalty[mask] = best;
			} else {
				chargingPenalty[mask] = 0;
			}
		}

		long[][] a = new long[m + 1][masks];
		int[][] how = new int[m + 1][masks];
		for (int i = 0; i <= m; i++) {
			Arrays.fill(a[i], inf);
		}
		a[0][0] = 0;
		for (int i = 0; i < m; i++) {
			int currentSpotSize = spotSize[i];
			int currentAvailable = spotAvailable[i];
			for (int maskAlready = 0; maskAlready < masks; maskAlready++) {
				long aHere = a[i][maskAlready];
				if (aHere == inf) {
					continue;
				}
				int maskNotYet = (masks - 1) ^ maskAlready;
				int mask = maskNotYet;
				while (true) {
					if (currentSpotSize >= neededSpotSize[mask]) {
						long spent = aHere + currentAvailable * sumPenalties[mask] + chargingPenalty[mask];
						if (a[i + 1][maskAlready | mask] > spent) {
							a[i + 1][maskAlready | mask] = spent;
							how[i + 1][maskAlready | mask] = mask;
						}
					}
					if (mask == 0) {
						break;
					}
					mask = (mask - 1) & maskNotYet;
				}
			}
		}
		long[] ans = new long[2 * n + 1];
		int maskNeed = masks - 1;
		ans[0] = a[m][maskNeed];
		for (int i = m - 1; i >= 0; i--) {
			int mask = how[i + 1][maskNeed];
			long time = spotAvailable[i];
			for (int j : order[mask]) {
				ans[1 + j] = i;
				ans[1 + n + j] = time;
				time += chargingTime[j];
			}
			maskNeed -= mask;
		}
		return ans;
	}
}

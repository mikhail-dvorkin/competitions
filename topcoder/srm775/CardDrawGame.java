package topcoder.srm775;

import java.util.Arrays;

public class CardDrawGame {
	public double winChance(int target, int[] count) {
		int amount = Arrays.stream(count).sum();
		if (amount < 3) return 0;
		int[] totalOptions = totalOptions(amount - 3);
		double ans = 0;
		for (int xx = 0; xx < count.length; xx++) {
			for (int yy = xx; yy < count.length; yy++) {
				for (int zz = yy; zz < count.length; zz++) {
					int options = options(count, xx, yy, zz);
					if (options == 0) continue;
					if (xx + yy + zz == target) {
						ans += options;
						continue;
					}
					count[xx]--; count[yy]--; count[zz]--;
					double[] win = new double[7];
					for (int x = 0; x < count.length; x++) {
						if (amount > 3) {
							double p = 1.0 * count[x] / totalOptions[0];
							if (x + xx + yy == target) win[0] += p;
							if (x + xx + zz == target) win[1] += p;
							if (x + yy + zz == target) win[2] += p;
						}
						for (int y = x; y < count.length; y++) {
							if (totalOptions[1] > 0) {
								double p = count[x] * ((x == y) ? (count[x] - 1.0) / 2 : count[y]) / totalOptions[1];
								if (x + y + xx == target) win[3] += p;
								if (x + y + yy == target) win[4] += p;
								if (x + y + zz == target) win[5] += p;
							}
							if (totalOptions[2] > 0) {
								int z = target - x - y;
								if (z >= y && z < count.length) {
									win[6] += 1.0 * options(count, x, y, z) / totalOptions[2];
								}
							}
						}
					}
					count[xx]++; count[yy]++; count[zz]++;
					ans += options * Arrays.stream(win).max().getAsDouble();
				}
			}
		}
		return ans / totalOptions(amount)[2];
	}

	private int options(int[] count, int x, int y, int z) {
		if (x == y && y == z) return count[x] * (count[x] - 1) * (count[x] - 2) / 6;
		if (x == y) return count[x] * (count[x] - 1) / 2 * count[z];
		if (x == z) return count[x] * (count[x] - 1) / 2 * count[y];
		if (y == z) return count[y] * (count[y] - 1) / 2 * count[x];
		return count[x] * count[y] * count[z];
	}

	private int[] totalOptions(int amount) {
		return new int[] { amount, amount * (amount - 1) / 2, (int) (amount * (amount - 1L) * (amount - 2) / 6) };
	}
}

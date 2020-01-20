package topcoder.srm775;

public class IterateOverACube {
	public int[] findCell(int n, long index) {
		long count3 = 0;
		for (int s = 0;; s++) {
			count3 += count2(n, s) - count2(n, s - n);
			if (index >= count3) {
				index -= count3;
				continue;
			}
			for (int x = 0; x < n; x++) {
				long here = count2(n, s - x);
				if (index >= here) {
					index -= here;
					continue;
				}
				for (int y = 0; y < n; y++) {
					int z = s - x - y;
					if (z < 0 || z >= n) continue;
					if (index-- == 0) return new int[] {x, y, z};
				}
			}
		}
	}

	private long count2(int n, int s) {
		if (s < 0 || s > 2 * n - 2) return 0;
		return (s <= n - 1) ? s + 1 : 2 * n - 1 - s;
	}
}

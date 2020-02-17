package topcoder;
public class BestView {
	public int numberOfBuildings(int[] heights) {
		int n = heights.length;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int cur = 0;
			jloop:
			for (int j = 0; j < n; j++) {
				if (i == j)
					continue;
				for (int k = 0; k < n; k++) {
					if ((i - k) * (j - k) >= 0)
						continue;
					double hk = heights[i] + (heights[j] - heights[i]) * 1d * (k - i) / (j - i);
					if (heights[k] >= hk - 1e-10)
						continue jloop;
				}
				cur++;
			}
			ans = Math.max(ans, cur);
		}
		return ans;
	}
}

package topcoder;
public class MaximizeSquares {
	public int squareCount(int n) {
		int m = (int) Math.floor(Math.sqrt(n) + 1e-10);
		int ans;
		if (n <= m * (m + 1)) {
			ans = count(m, m, n - m * m);
		} else {
			ans = count(m + 1, m, n - m * (m + 1));
		}
		return ans;
	}

	private int count(int h, int w, int k) {
		int res = 0;
		for (int s = 1; s < h && s < w; s++) {
			res += (h - s) * (w - s);
			if (s < k) {
				res += (k - s);
			}
		}
		return res;
	}
}

//{5, 6, 2, -1, 6, 5, -1, 2, -1, 5, 6, -1, 2, -1, 7, -1, 2, -1, -1, 1, 3, 4, 5, 6, 2, -1, 6, 5, -1, 2, -1, 5, 6, -1, 2, -1, 2, -1, -1, 1, 3, 4}
//{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7}
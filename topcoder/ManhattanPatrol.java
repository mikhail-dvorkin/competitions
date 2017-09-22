package topcoder;
public class ManhattanPatrol {
	public long countIntersections(int n, int AX, int BX, int MX, int AY, int BY, int MY) {
		int[] x = new int[n];
		int[] y = new int[n];
		x[0] = BX;
		y[0] = BY;
		for (int i = 1; i < n; i++) {
			x[i] = (AX * x[i - 1] + BX) % MX;
			y[i] = (AY * y[i - 1] + BY) % MY;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < n; j++) {
				if (x[j - 1] > x[j]) {
					int t = x[j - 1]; x[j - 1] = x[j]; x[j] = t;
					t = y[j - 1]; y[j - 1] = y[j]; y[j] = t;
				}
			}
		}
//		System.out.println(Arrays.toString(x));
//		System.out.println(Arrays.toString(y));
		long ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 3; j < n; j++) {
				int lo = 0;
				int hi = 0;
				int ylo = Math.min(y[i], y[j]);
				int yhi = Math.max(y[i], y[j]);
				for (int k = i + 1; k < j; k++) {
					if (y[k] > yhi) {
						hi++;
					} else if (y[k] < ylo) {
						lo++;
					}
				}
				ans += lo * hi;
			}
		}
		return ans;
	}

}

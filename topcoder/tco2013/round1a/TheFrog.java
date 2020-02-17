package topcoder.tco2013.round1a;
public class TheFrog {
	public double getMinimum(int d, int[] from, int[] to) {
		double ans = d;
		int n = from.length;
		for (int i = 0; i <= n; i++) {
			int num = (i == n) ? d : to[i];
			for (int den = 1; den <= num; den++) {
				boolean ok = true;
				for (int j = 0; j < n; j++) {
					int x = from[j] * den / num + 1;
					if (num * x < to[j] * den) {
						ok = false;
					}
				}
				if (ok) {
					ans = Math.min(ans, num * 1.0 / den);
				}
			}
		}
		return ans;
	}

}

package topcoder.srm479;
public class TheCoffeeTimeDivOne {
	public long find(int n, int[] tea) {
		boolean[] t = new boolean[n + 1];
		for (int x : tea) {
			t[x] = true;
		}
		long ans = 4 * n;
		for (boolean drink : new boolean[]{false, true}) {
			int c = 0;
			for (int i = n; i >= 1; i--) {
				if (t[i] != drink) {
					continue;
				}
				if (c == 0) {
					ans += 2 * i + 47;
				}
				c++;
				if (c == 7) {
					c = 0;
				}
			}
		}
		return ans;
	}

}

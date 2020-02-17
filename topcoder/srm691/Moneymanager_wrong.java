package topcoder.srm691;
public class Moneymanager_wrong {
	public int getbest(int[] a, int[] b, int x) {
		int n = a.length;
		int m = n / 2;
		for (;;) {
			boolean improved = false;
			for (int i = 0; i < n - 1; i++) {
				int s = (i == m - 1) ? x : 0;
				if ((a[i] + s) * b[i + 1] < (a[i + 1] + s) * b[i]) {
					int t;
					t = a[i]; a[i] = a[i + 1]; a[i + 1] = t;
					t = b[i]; b[i] = b[i + 1]; b[i + 1] = t;
					improved = true;
				}
			}
			if (!improved) {
				break;
			}
		}

		int ans = 0;
		int exp = 0;
		for (int i = 0; i < n; i++) {
			exp += a[i];
			if (i == m) {
				exp += x;
			}
			ans += exp * b[i];
		}
		return ans;
	}
}

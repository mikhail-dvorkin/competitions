package topcoder.tccc2007.round1b;
public class MarbleTop {
	public int minCuts(int k, int[] stock, int[] orders) {
		int[] have = new int[50];
		int ans = 0;
		for (int x : stock)
			have[x]++;
		int ks = 0;
		for (int x : orders) {
			if (x == k) {
				ks++;
				continue;
			}
			int y = x;
			while (y < 50 && have[y] == 0)
				y += k;
			if (y >= 50)
				return -1;
			have[y]--;
			have[k] += (y - x) / k;
			ans += (y - x) / k;
		}
		for (int ii = 0; ii < ks; ii++) {
			if (have[k] > 0) {
				have[k]--;
				continue;
			}
			int y = 2 * k;
			while (y < 50 && have[y] == 0)
				y += k;
			if (y < 50) {
				have[y]--;
				have[y - k]++;
				ans++;
				continue;
			}
			y = k + 1;
			while (y < 50 && have[y] == 0)
				y++;
			if (y >= 50)
				return -1;
			have[y]--;
			have[y - k]++;
			ans++;
		}
		return ans;
	}

	public static void main(String[] args) {
		int a = new MarbleTop().minCuts(5, new int[]{31, 36, 37, 32, 39, 34, 9, 11, 15, 19, 20}, new int[]{21, 26, 22, 27, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		System.out.println(a);
	}
}

package topcoder.tco2009.round5;
public class LineOfDice {
	int mod = 10007;
	int[][] cnk = new int[5000][1200];

	public int howMany(int[] d) {
		for (int n = 0; n < 5000; n++) {
			cnk[n][0] = 1;
			if (n <= 1000) cnk[n][n] = 1;
			for (int k = 1; k < n && k <= 1000; k++) {
				cnk[n][k] = (cnk[n - 1][k - 1] + cnk[n - 1][k]) % mod;
			}
		}
		int ans = 0;
		ans += solve(d[0], d[1], d[4], d[5]);
		ans += solve(d[0], d[2], d[3], d[5]);
		ans += solve(d[1], d[2], d[3], d[4]);
		return (2 * ans) % mod;
	}

	private int solve(int... d) {
		int n = 5000;
		int[] a = new int[n];
		a[0] = 1;
		for (int i = 0; i < 4; i++) {
			int[] b = new int[n];
			for (int k = 0; k < n; k++) {
				if (a[k] == 0)
					continue;
				for (int t = 0; t <= d[i]; t++) {
					b[k + t] = (b[k + t] + a[k] * cnk[k + t][t]) % mod;
				}
			}
			a = b;
		}
		int ans = 0;
		a[0] = 0;
		for (int i : a) {
			ans = (ans + i) % mod;
		}
		return ans;
	}

	public static void main(String[] args) {
		int a = new LineOfDice().howMany(new int[]{997, 973, 987, 997, 1000, 1000});
		System.out.println(a);
	}
}

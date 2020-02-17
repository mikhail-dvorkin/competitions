package topcoder.srm340;
public class ProblemsToSolve {
	public int minNumber(int[] pleasantness, int variety) {
		int[] a = pleasantness;
		int v = variety;
		int n = a.length;
		int ans = n + 10;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int d = Math.abs(a[i] - a[j]);
				if (d >= v || (j - i == n - 1)) {
					ans = Math.min(ans, 1 + f(i) + f(j - i));
				}
			}
		}
		return ans;
	}

	private int f(int i) {
		return (i + 1) / 2;
	}
}

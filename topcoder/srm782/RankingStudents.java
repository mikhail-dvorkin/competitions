package topcoder.srm782;

public class RankingStudents {
	public String rankingPossible(int n, int[] f, int[] a, int[] b) {
		int[] larger = new int[n];
		for (int v : a) {
			larger[v]++;
		}
		for (int x = n - 1; x >= 0; x--) {
			int u;
			for (u = 0;; u++) {
				if (u == n) return "Impossible";
				if (larger[u] == 0 && f[u] >= x) break;
			}
			larger[u] += n;
			for (int i = 0; i < a.length; i++) {
				if (b[i] == u) larger[a[i]]--;
			}
		}
		return "Possible";
	}
}

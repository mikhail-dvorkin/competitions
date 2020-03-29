package topcoder.srm782;

import java.util.Arrays;

public class RankingStudents {
	public String rankingPossible(int n, int[] f, int[] a, int[] b) {
		boolean[] alive = new boolean[n];
		int[] larger = new int[n];
		for (int i = 0; i < a.length; i++) {
			larger[a[i]]++;
		}
		Arrays.fill(alive, true);
		for (int x = n - 1; x >= 0; x--) {
			int u = -1;
			for (int v = 0; v < n; v++) {
				if (!alive[v]) continue;
				if (f[v] < x) continue;
				if (larger[v] > 0) continue;
				u = v;
				break;
			}
			if (u == -1) return "Impossible";
			alive[u] = false;
			for (int i = 0; i < a.length; i++) {
				if (b[i] == u) larger[a[i]]--;
			}
		}
		return "Possible";
	}
}

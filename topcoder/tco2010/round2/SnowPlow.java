package topcoder.tco2010.round2;
public class SnowPlow {
	int n;
	boolean[] were;
	int[] s;
	int[][] e;

	public int solve(String[] roads) {
		n = roads.length;
		e = new int[n][n];
		s = new int[n];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				e[i][j] = roads[i].charAt(j) - '0';
				s[i] += e[i][j];
			}
			ans += s[i];
		}
		were = new boolean[n];
		dfs(0);
		for (int i = 0; i < n; i++) {
			if (s[i] > 0 && !were[i]) {
				return -1;
			}
		}
		return ans;
	}

	private void dfs(int i) {
		were[i] = true;
		for (int j = 0; j < n; j++) {
			if (e[i][j] > 0 && !were[j]) {
				dfs(j);
			}
		}
	}

}

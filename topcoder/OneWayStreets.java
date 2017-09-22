package topcoder;
public class OneWayStreets {
	boolean[][] e;
	boolean[] mark, gray;
	int n;
	boolean cycle = false;
	
	public String canChange(String[] roads) {
		n = roads.length;
		e = new boolean[n][n];
		mark = new boolean[n];
		gray = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				e[i][j] = roads[i].charAt(j) == 'Y';
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (e[i][j] && e[j][i]) {
					e[i][j] = e[j][i] = false;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			dfs(i);
		}
		return cycle ? "NO" : "YES";
	}

	private void dfs(int v) {
		gray[v] = mark[v] = true;
		for (int i = 0; i < n; i++) if (e[v][i]) {
			if (gray[i]) {
				cycle = true;
				return;
			}
			if (mark[i])
				continue;
			dfs(i);
		}
		gray[v] = false;
	}
}

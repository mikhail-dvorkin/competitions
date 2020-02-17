package topcoder;
public class ChromaticNumber {
	int n;
	boolean[][] e;
	boolean[] mark;
	int vs, es;
	
	public int minColors(String[] graph) {
		n = graph.length;
		e = new boolean[n][n];
		mark = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				e[i][j] = (graph[i].charAt(j) == 'N');
			}
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (mark[i]) {
				continue;
			}
			vs = 0;
			es = 0;
			dfs(i);
			if (vs == 3 && es == 6) {
				ans += 1;
				continue;
			}
			ans += (vs + 1) / 2;
		}
		return ans;
	}

	private void dfs(int v) {
		mark[v] = true;
		vs++;
		for (int u = 0; u < n; u++) {
			if (u == v) {
				continue;
			}
			if (e[v][u]) {
				es++;
				if (!mark[u]) {
					dfs(u);
				}
			}
		}
	}

}

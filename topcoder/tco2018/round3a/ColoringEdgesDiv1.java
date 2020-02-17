package topcoder.tco2018.round3a;
public class ColoringEdgesDiv1 {
	boolean[][] e;
	boolean[][] tree;
	boolean[] mark;

	public int[] findColoring(int n, int[] x, int[] y) {
		e = new boolean[n][n];
		tree = new boolean[n][n];
		for (int i = 0; i < x.length; i++) {
			e[x[i]][y[i]] = e[y[i]][x[i]] = true;
		}
		mark = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!mark[i]) {
				dfs(i);
			}
		}
		int[] ans = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			ans[i] = tree[x[i]][y[i]] ? 1 : 0;
		}
		return ans;
	}

	void dfs(int v) {
		mark[v] = true;
		for (int u = 0; u < e.length; u++) {
			if (e[v][u] && !mark[u]) {
				tree[v][u] = tree[u][v] = true;
				dfs(u);
			}
		}
	}
}

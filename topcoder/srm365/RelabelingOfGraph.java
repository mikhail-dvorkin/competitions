package topcoder.srm365;
public class RelabelingOfGraph {
	boolean[][] e;
	int[] ans;
	boolean[] mrk;
	boolean[] gry;
	int n;

	public int[] renumberVertices(String[] m) {
		n = m.length;
		ans = new int[n];
		e = new boolean[n][n];
		mrk = new boolean[n];
		gry = new boolean[n];
		boolean[] init = new boolean[n];
		for (int i = 0; i < n; i++) {
			init[i] = true;
			for (int j = 0; j < n; j++) {
				e[i][j] = m[i].charAt(j) == '1';
			}
		}
		for (int i = 0; i < n; i++) {
			if (DFS(i))
				return new int[]{};
		}
		go(init, 0);
		return ans;
	}

	private boolean DFS(int v) {
		mrk[v] = true;
		gry[v] = true;
		for (int u = 0; u < n; u++) {
			if (e[v][u]) {
				if (gry[u]) {
					return true;
				}
				if (mrk[u])
					continue;
				if (DFS(u))
					return true;
			}
		}
		gry[v] = false;
		return false;
	}

	private void go(boolean[] set, int lo) {
		int a = -1;
		for (int i = 0; i < n; i++) {
			if (set[i] == true) {
				a = i;
				break;
			}
		}
		if (a == -1)
			return;
		boolean[] prev = new boolean[n];
		dfs(a, set, prev);
		prev[a] = false;
		int sp = 0;
		for (int i = 0; i < n; i++) {
			if (prev[i]) {
				sp++;
			}
		}
		ans[a] = lo + sp;
		go(prev, lo);
		for (int i = 0; i < n; i++) {
			prev[i] = (set[i] & !prev[i]);
		}
		prev[a] = false;
		go(prev, lo + sp + 1);
	}

	private void dfs(int v, boolean[] set, boolean[] prev) {
		prev[v] = true;
		for (int i = 0; i < n; i++) {
			if (!set[i] || prev[i])
				continue;
			if (e[i][v]) {
				dfs(i, set, prev);
			}
		}
	}
}

package topcoder.srm782;

import java.util.ArrayList;

public class PaintItBlack {
	boolean[] color;
	boolean[] mark;
	boolean[][] mark2;
	ArrayList<Integer> ans = new ArrayList<>();
	ArrayList<Integer> odd = null;
	ArrayList<Integer> stack = new ArrayList<>();
	boolean[][] e;
	int n;

	public int[] findWalk(int n, int u, int[] a, int[] b) {
		this.n = n;
		e = new boolean[n][n];
		for (int i = 0; i < a.length; i++) {
			e[a[i]][b[i]] = true;
			e[b[i]][a[i]] = true;
		}
		color = new boolean[n];
		if (n % 2 == 1) {
			mark2 = new boolean[n][2];
			dfs(u, 0, u);
			if (odd == null) return new int[0];
			odd.remove(odd.size() - 1);
			for (int i : odd) {
				color[i] ^= true;
			}
			ans.addAll(odd);
		}
		ans.add(u);
		mark = new boolean[n];
		dfsFinal(u);
		int[] r = new int[ans.size()];
		for (int i = 0; i < ans.size(); i++) {
			r[i] = ans.get(i);
			if (i > 0 && !e[r[i - 1]][r[i]]) throw new RuntimeException();
		}
		if (r.length > 0 && (r[r.length - 1] != r[0] || r[0] != u)) throw new RuntimeException();
		for (int i = 0; i < n; i++) {
			int c = 0;
			for (int j = 1; j < r.length; j++) {
				if (r[j] == i) c = 1 - c;
			}
			if (r.length > 0 && c != 1) {
				throw new RuntimeException();
			}
		}
		return r;
	}

	private void dfs(int v, int c, int w) {
		mark2[v][c] = true;
		stack.add(v);
		if (v == w && c == 1) {
			odd = new ArrayList<>(stack);
		}
		for (int u = 0; u < n; u++) {
			if (!e[u][v]) continue;
			if (mark2[u][1 - c]) continue;
			dfs(u, 1 - c, w);
		}
		stack.remove(stack.size() - 1);
	}

	private void dfsFinal(int v) {
		mark[v] = true;
		for (int u = 0; u < n; u++) {
			if (!e[u][v]) continue;
			if (mark[u]) continue;
			ans.add(u);
			color[u] ^= true;
			dfsFinal(u);
			ans.add(v);
			color[v] ^= true;
			if (!color[u]) {
				ans.add(u);
				color[u] ^= true;
				ans.add(v);
				color[v] ^= true;
			}
		}
	}
}

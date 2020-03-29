package topcoder.srm782;

import java.util.*;

public class PaintItBlack {
	boolean[] color;
	boolean[] mark;
	boolean[][] markOdd;
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
			markOdd = new boolean[n][2];
			dfsOdd(u, 0, u);
			if (odd == null) return new int[0];
			odd.remove(odd.size() - 1);
			for (int i : odd) {
				color[i] ^= true;
			}
			ans.addAll(odd);
		}
		ans.add(u);
		mark = new boolean[n];
		dfs(u);
		int[] r = new int[ans.size()];
		for (int i = 0; i < ans.size(); i++) {
			r[i] = ans.get(i);
		}
		return r;
	}

	private void dfsOdd(int v, int c, int need) {
		markOdd[v][c] = true;
		stack.add(v);
		if (v == need && c == 1) {
			odd = new ArrayList<>(stack);
		}
		for (int u = 0; u < n; u++) {
			if (!e[u][v] || markOdd[u][1 - c]) continue;
			dfsOdd(u, 1 - c, need);
		}
		stack.remove(stack.size() - 1);
	}

	private void dfs(int v) {
		mark[v] = true;
		for (int u = 0; u < n; u++) {
			if (!e[u][v] || mark[u]) continue;
			add(u);
			dfs(u);
			add(v);
			if (!color[u]) {
				add(u);
				add(v);
			}
		}
	}

	private void add(int v) {
		ans.add(v);
		color[v] ^= true;
	}
}

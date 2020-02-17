package topcoder;
public class ScotlandYard {
	int n;
	int[][] e;
	boolean[] cont;
	
	public int maxMoves(String[] taxi, String[] bus, String[] metro) {
		n = taxi.length;
		int t = 1;
		e = new int[n][n];
		cont = new boolean[n];
		for (String[] s : new String[][]{taxi, bus, metro}) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (s[i].charAt(j) == 'Y') {
						e[i][j] |= t;
						cont[i] = true;
					}
				}
			}
			t *= 2;
		}
		mark = new int[n * n];
		dist = new int[n * n];
		cycle = false;
		maxDist = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					continue;
				}
				int v = i * n + j;
				if (mark[v] == 0) {
					dfs(v);
					if (cycle) {
						return -1;
					}
				}
			}
		}
		return maxDist;
	}
	
	int[] mark;
	int[] dist;
	boolean cycle;
	int maxDist;
	
	void dfs(int v) {
		mark[v] = 1;
		int i = v / n;
		int j = v % n;
		int[] ei = e[i];
		int[] ej = e[j];
		int d = -1;
		for (int ii = 0; ii < n; ii++) {
			for (int jj = 0; jj < n; jj++) {
				if (ii == jj) {
					continue;
				}
				if (((ei[ii] | ej[ii]) & (ei[jj] | ej[jj])) == 0) {
					continue;
				}
				int u = ii * n + jj;
				if (mark[u] == 1) {
					cycle = true;
					return;
				}
				if (mark[u] == 0) {
					dfs(u);
					if (cycle) {
						return;
					}
				}
				d = Math.max(d, dist[u]);
			}
		}
		if (d == -1) {
			if (cont[i] || cont[j]) {
				d = 1;
			} else {
				d = 0;
			}
		} else {
			d++;
		}
		dist[v] = d;
		maxDist = Math.max(maxDist, d);
		mark[v] = 2;
	}

}

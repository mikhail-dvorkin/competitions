package topcoder.tco2015.round2a;
import java.util.*;

public class FoxMeeting {
	int[][] e;
	int[][] p;

    public int maxDistance(int[] from, int[] to, int[] len, int[] foxes) {
    	int n = from.length + 1;
    	e = new int[n][n];
    	p = new int[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			e[i][j] = Integer.MAX_VALUE / 3;
    			p[i][j] = -1;
    		}
    		e[i][i] = 0;
    	}
    	for (int i = 0; i < foxes.length; i++) {
    		foxes[i]--;
    	}
    	for (int i = 0; i < n - 1; i++) {
    		from[i]--;
    		to[i]--;
    		e[from[i]][to[i]] = e[to[i]][from[i]] = len[i];
    		p[from[i]][to[i]] = to[i];
    		p[to[i]][from[i]] = from[i];
    	}
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			for (int k = 0; k < n; k++) {
    				if (e[j][i] + e[i][k] < e[j][k]) {
    					e[j][k] = e[j][i] + e[i][k];
    					p[j][k] = p[j][i];
    				}
    			}
    		}
    	}
    	int low = -1;
    	int high = 0;
    	for (int i = 0; i < n; i++) {
    		high = Math.max(high, e[0][i]);
    	}
    	while (low + 1 < high) {
    		int dist = (low + high) / 2;
    		boolean can = false;
    		for (int r = 0; r < n; r++) {
    			TreeSet<Integer> set = new TreeSet<Integer>();
    			set.add(r);
    			for (int f : foxes) {
    				int v = f;
    				int travel = dist;
    				for (;;) {
    					if (v == r) {
    						break;
    					}
    					int u = p[v][r];
    					if (e[v][u] > travel) {
    						break;
    					}
    					travel -= e[v][u];
    					v = u;
    				}
    				set.add(v);
    				while (v != r) {
    					v = p[v][r];
    					set.add(v);
    				}
    			}
    			if (set.size() > foxes.length) {
    				continue;
    			}
    			edge = new boolean[foxes.length][set.size()];
    			int j = 0;
    			for (int v : set) {
    				for (int i = 0; i < foxes.length; i++) {
    					edge[i][j] = e[v][foxes[i]] <= dist;
    				}
    				j++;
    			}
    			int matching = solve();
    			if (matching == set.size()) {
    				can = true;
    				break;
    			}
    		}
    		if (can) {
    			high = dist;
    		} else {
    			low = dist;
    		}
    	}
    	return high;
    }

	public boolean[][] edge;
	private boolean[] mark;
	private int[] left;

	public int solve() {
		mark = new boolean[edge.length];
		left = new int[edge[0].length];
		Arrays.fill(left, -1);
		int ans = 0;
		for (int i = 0; i < edge.length; i++) {
			Arrays.fill(mark, false);
			if (dfs(i)) {
				ans++;
			}
		}
		return ans;
	}

	private boolean dfs(int i) {
		if (mark[i]) {
			return false;
		}
		mark[i] = true;
		for (int j = 0; j < edge[i].length; j++) {
			if (!edge[i][j]) {
				continue;
			}
			if (left[j] == -1 || dfs(left[j])) {
				left[j] = i;
				return true;
			}
		}
		return false;
	}

}

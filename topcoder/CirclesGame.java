package topcoder;
import java.util.*;

public class CirclesGame {
	int[] p;
	int[][] c;
	int[] g;
	int[] gc;
	int n;
	
	public String whoCanWin(int[] x, int[] y, int[] r) {
		n = x.length;
		p = new int[n];
		c = new int[n][0];
		g = new int[n];
		gc = new int[n];
		for (int i = 0; i < n; i++) {
			int bestj = -1;
			for (int j = 0; j < n; j++) {
				if (r[j] <= r[i]) {
					continue;
				}
				if (Math.hypot(x[i] - x[j], y[i] - y[j]) < r[j]) {
					if (bestj < 0 || r[j] < r[bestj]) {
						bestj = j;
					}
				}
			}
			p[i] = bestj;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (p[j] == i) {
					int[] cc = new int[c[i].length + 1];
					System.arraycopy(c[i], 0, cc, 0, c[i].length);
					cc[cc.length - 1] = j;
					c[i] = cc;
				}
			}
		}
		int xor = 0;
		for (int i = 0; i < n; i++) {
			if (p[i] >= 0) {
				continue;
			}
			dfs(i);
			xor ^= g[i];
		}
		return xor == 0 ? "Bob" : "Alice";
	}

	void dfs(int v) {
		for (int u : c[v]) {
			dfs(u);
			gc[v] ^= g[u];
		}
		Set<Integer> set = new HashSet<Integer>();
		dfs2(v, set, gc[v]);
		for (int i = 0;; i++) {
			if (!set.contains(i)) {
				g[v] = i;
				return;
			}
		}
	}

	void dfs2(int v, Set<Integer> set, int xor) {
		set.add(xor);
		for (int u : c[v]) {
			dfs2(u, set, xor ^ g[u] ^ gc[u]);
		}
	}

}

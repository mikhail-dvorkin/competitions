package topcoder.tco2012.round3a;
import java.util.*;

public class FoxAndCake {
	int[] di = new int[]{1, 0, -1, 0};
	int[] dj = new int[]{0, 1, 0, -1};

	public String ableToDivide(int n, int m, int[] x, int[] y) {
		TreeSet<Integer> impX = new TreeSet<Integer>();
		TreeSet<Integer> impY = new TreeSet<Integer>();
		for (int i = 0; i < 7; i++) {
			for (int d = -1; d <= 1; d++) {
				if (x[i] + d >= 1 && x[i] + d <= n) {
					impX.add(x[i] + d);
				}
				if (y[i] + d >= 1 && y[i] + d <= m) {
					impY.add(y[i] + d);
				}
			}
		}
		impX.add(1);
		if (n > 1) {
			impX.add(2);
			impX.add(n - 1);
		}
		impX.add(n);
		impY.add(1);
		if (m > 1) {
			impY.add(2);
			impY.add(m - 1);
		}
		impY.add(m);
		ArrayList<Integer> impXlist = new ArrayList<Integer>(impX);
		ArrayList<Integer> impYlist = new ArrayList<Integer>(impY);
		for (int i = 0; i < 7; i++) {
			x[i] = Collections.binarySearch(impXlist, x[i]);
			y[i] = Collections.binarySearch(impYlist, y[i]);
		}
		n = impX.size();
		m = impY.size();
		vers = 2 * n * m + 2;
		c = new byte[vers][vers];
		f = new byte[vers][vers];
		S = vers - 2;
		T = vers - 1;
		c[S][(y[1] * n + x[1]) * 2] = 1;
		c[S][(y[2] * n + x[2]) * 2] = 1;
		c[S][(y[3] * n + x[3]) * 2] = 1;
		c[(y[4] * n + x[4]) * 2 + 1][T] = 1;
		c[(y[5] * n + x[5]) * 2 + 1][T] = 1;
		c[(y[6] * n + x[6]) * 2 + 1][T] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				c[(j * n + i) * 2][(j * n + i) * 2 + 1] = 1;
				for (int d = 0; d < 4; d++) {
					int ii = i + di[d];
					int jj = j + dj[d];
					if (ii < 0 || jj < 0 || ii >= n || jj >= m) {
						continue;
					}
					c[(j * n + i) * 2 + 1][(jj * n + ii) * 2] = 1;
				}
			}
		}
		c[(y[0] * n + x[0]) * 2][(y[0] * n + x[0]) * 2 + 1] = 0;
		for (int i = 0; i < 3; i++) {
			mark = new boolean[vers];
			if (!dfs(S)) {
				return "No";
			}
		}
		return "Yes";
	}

	private boolean dfs(int v) {
		if (v == T) {
			System.out.println("--");
			return true;
		}
		mark[v] = true;
		for (int u = 0; u < vers; u++) {
			if (f[v][u] == c[v][u] || mark[u]) {
				continue;
			}
			if (dfs(u)) {
				if (v / 2 != u / 2) {
					System.out.println((v / 2) + " " + (u / 2));
				}
				f[v][u]++;
				f[u][v]--;
				return true;
			}
		}
		return false;
	}

	byte[][] c, f;
	boolean[] mark;
	int vers, S, T;

}

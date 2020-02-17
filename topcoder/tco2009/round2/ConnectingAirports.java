package topcoder;
import java.util.*;

public class ConnectingAirports {
	int na, nb, n;
	char[][] ans;
	int[][] f;
	int[][] c;
	int[] oa;
	int[] ob;
	int[] ca;
	int[] cb;
	boolean[] mark;
	
	public String[] getSchedule(int[] a, int[] b) {
		oa = a;
		ob = b;
		ca = new int[n];
		cb = new int[n];
		na = a.length;
		nb = b.length;
		n = na + nb + 2;
		ans = new char[na][nb];
		f = new int[n][n];
		c = new int[n][n];
		mark = new boolean[n];
		if (!poss())
			return new String[]{};
		for (int i = 0; i < na; i++) {
			for (int j = 0; j < nb; j++) {
				ans[i][j] = '0';
				if (poss())
					continue;
				ans[i][j] = '1';
			}
		}
		String[] r = new String[na];
		for (int i = 0; i < na; i++) {
			r[i] = new String(ans[i]);
		}
		return r;
	}

	private boolean poss() {
		for (int i = 0; i < n; i++) {
			Arrays.fill(c[i], 0);
			Arrays.fill(f[i], 0);
		}
		for (int i = 0; i < na; i++) {
			ca[i] = oa[i];
		}
		for (int i = 0; i < nb; i++) {
			cb[i] = ob[i];
		}
		for (int i = 0; i < na; i++) {
			for (int j = 0; j < nb; j++) {
				if (ans[i][j] == 0) {
					c[1 + i][1 + na + j] = 1;
				} else if (ans[i][j] == '1') {
					ca[i]--;
					cb[j]--;
				}
			}
		}
		int todo = 0;
		int todo2 = 0;
		for (int i = 0; i < na; i++) {
			if (ca[i] < 0)
				return false;
			todo += (c[0][1 + i] = ca[i]);
		}
		for (int i = 0; i < nb; i++) {
			if (cb[i] < 0)
				return false;
			todo2 += (c[1 + na + i][n - 1] = cb[i]);
		}
		if (todo != todo2)
			return false;
		for (int i = 1; i < 1 + na; i++) {
			for (int j = 1 + na; j < 1 + na + nb; j++) {
				if (f[0][i] < c[0][j] && f[i][j] < c[i][j] && f[j][n - 1] < c[j][n - 1]) {
					todo--;
					f[0][i]++;
					f[i][j]++;
					f[j][n - 1]++;
				}
			}
		}
		while (true) {
			Arrays.fill(mark, false);
			int d = search(0);
			todo -= d;
			if (todo == 0)
				return true;
			if (d == 0)
				return false;
		}
	}

	private int search(int v) {
		if (v == n - 1) {
			return 1;
		}
		mark[v] = true;
		for (int u = 0; u < n; u++) {
			if (mark[u] || f[v][u] == c[v][u])
				continue;
			int d = search(u);
			if (d > 0) {
				f[v][u]++;
				f[u][v]--;
				return 1;
			}
		}
		return 0;
	}
}

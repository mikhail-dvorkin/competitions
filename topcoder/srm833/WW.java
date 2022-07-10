package topcoder.srm833;

import java.util.Arrays;

public class WW {
	public int rearrange(String s) {
		int n = s.length();
		int[] count = new int['z' + 1];
		for (int i = 0; i < n; i++) {
			count[s.charAt(i)]++;
		}
		for (int c = 0; c < count.length; c++) {
			if (count[c] % 2 == 1) {
				return -1;
			}
		}
		int[][] e = new int[n / 2][n / 2];
		int[] pos = new int[n];
		int q = 0;
		for (int c = 0; c < count.length; c++) {
			int p = 0;
			for (int i = 0; i < n; i++) {
				if (s.charAt(i) != c) continue;
				pos[p++] = i;
			}
			for (int i = 0; i < p / 2; i++) {
				int p1 = pos[i];
				int p2 = pos[i + p / 2];
				for (int j = 0; j < n / 2; j++) {
					e[q][j] = (Math.abs(p1 - j) + Math.abs(p2 - (j + n / 2))) * c;
				}
				q++;
			}
		}
		return hungarian(e);
	}

	public static int hungarian(int[][] e) {
		int[] ans = new int[e.length];
		Arrays.fill(ans, -1);
		if (e.length == 0 || e[0].length == 0) {
			return 0;
		}
		int infty = Integer.MAX_VALUE / 2;
		boolean swap = false;
		if (e.length > e[0].length) {
			swap = true;
			int[][] f = new int[e[0].length][e.length];
			for (int i = 0; i < e.length; i++) {
				for (int j = 0; j < e[0].length; j++) {
					f[j][i] = e[i][j];
				}
			}
			e = f;
		}
		int n1 = e.length;
		int n2 = e[0].length;
		int[] u = new int[n1 + 1];
		int[] v = new int[n2 + 1];
		int[] p = new int[n2 + 1];
		int[] way = new int[n2 + 1];
		for (int i = 1; i <= n1; i++) {
			p[0] = i;
			int j0 = 0;
			double[] minv = new double[n2 + 1];
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				double delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						double cur = e[i0 - 1][j - 1] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= n2; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 > 0);
		}
		int sum = 0;
		for (int j = 1; j <= n2; j++) {
			if (p[j] > 0) {
				// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
				sum += e[p[j] - 1][j - 1];
				if (swap) {
					ans[j - 1] = p[j] - 1;
				} else {
					ans[p[j] - 1] = j - 1;
				}
			}
		}
		return sum;
	}
}

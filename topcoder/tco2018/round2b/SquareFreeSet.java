package topcoder.tco2018.round2b;
import java.util.*;

public class SquareFreeSet {
	public int findCost(int[] x) {
		int n = x.length;
		Arrays.sort(x);
		int gap = 566;
		int max = x[x.length - 1] + gap + 1;

		boolean[] isPrime = new boolean[max];
		int[] group = new int[max];
		for (int i = 1; i < max; i++) {
			isPrime[i] = true;
			group[i] = i;
		}
		for (int i = 2, j; (j = i * i) < max; i++) {
			if (!isPrime[i]) {
				continue;
			}
			do {
				isPrime[j] = false;
				while (group[j] % (i * i) == 0) {
					group[j] /= i * i;
				}
				j += i;
			} while (j < max);
		}

		int[] count = new int[max];
		for (int i = 0; i < n; i++) {
			int v = x[i];
			int g = group[v];
			count[g]++;
		}
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int v : x) {
			for (int u = Math.max(v - gap, 1); u <= v + gap; u++) {
				int gr = group[u];
				if (!map.containsKey(gr)) {
					map.put(gr, map.size());
				}
			}
		}
		int inf = Integer.MAX_VALUE / 3;
		int[][] e = new int[n][map.size()];
		for (int i = 0; i < n; i++) {
			int v = x[i];
			Arrays.fill(e[i], inf);
			for (int u = Math.max(v - gap, 1); u <= v + gap; u++) {
				int gr = group[u];
				int cost = Math.abs(v - u);
				int j = map.get(gr);
				e[i][j] = Math.min(e[i][j], cost);
			}
		}
		return hungarian(e);
	}

	public static int hungarian(int[][] e) {
		if (e.length == 0 || e[0].length == 0) {
			return 0;
		}
		int infty = 1_000_000;
		if (e.length > e[0].length) {
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
			int[] minv = new int[n2 + 1];
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				int delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						int cur = e[i0 - 1][j - 1] - u[i0] - v[j];
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
				sum += e[p[j] - 1][j - 1];
			}
		}
		return sum;
	}
}

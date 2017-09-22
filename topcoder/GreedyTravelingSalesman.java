package topcoder;
import java.util.*;

public class GreedyTravelingSalesman {
	public int worstDistance(String[] thousands, String[] hundreds, String[] tens, String[] ones) {
		int n = thousands.length;
		int worst = 0;
		boolean[] were = new boolean[n];
		int[][] e = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				e[i][j] =
						(thousands[i].charAt(j) - '0') * 1000 +
						(hundreds[i].charAt(j) - '0') * 100 +
						(tens[i].charAt(j) - '0') * 10 +
						(ones[i].charAt(j) - '0') * 1;
			}
		}
		for (int from = 0; from < n; from++) {
			for (int to = 0; to < n; to++) {
				if (from == to) {
					continue;
				}
				int remember = e[from][to];
				for (int d = -1; d <= 0; d++) {
					for (int k = 0; k < n; k++) {
						if (k == from) {
							e[from][to] = (d == 0) ? 9999 : 1;
						} else {
							e[from][to] = e[from][k] + d;
						}
						if (e[from][to] <= 0 || e[from][to] >= 10000) {
							continue;
						}
						Arrays.fill(were, false);
						were[0] = true;
						int v = 0;
						int travel = 0;
						for (;;) {
							int u = -1;
							int be = Integer.MAX_VALUE;
							for (int i = 0; i < n; i++) {
								if (were[i]) {
									continue;
								}
								if (e[v][i] < be) {
									be = e[v][i];
									u = i;
								}
							}
							if (u == -1) {
								break;
							}
							travel += be;
							v = u;
							were[v] = true;
						}
						if (travel > worst) {
							worst = travel;
						}
					}
				}
				e[from][to] = remember;
			}
		}
		return worst;
	}

}

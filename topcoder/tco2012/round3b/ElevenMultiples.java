package topcoder.tco2012.round3b;
import java.util.*;

public class ElevenMultiples {
	long M = 1000000007;

	public int countMultiples(String[] pieces) {
		int n = pieces.length;
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		int[] v = new int[n];
		boolean[] odd = new boolean[n];
		for (int i = 0; i < n; i++) {
			v[i] = calc11(pieces[i]);
			odd[i] = (pieces[i].length() % 2 == 1);
			if (odd[i]) {
				a.add(v[i]);
			} else {
				b.add(v[i]);
			}
		}
		int m = a.size();
		long[][][] z = new long[m + 1][m + 1][11];
		z[0][0][0] = 1;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j <= i; j++) {
				for (int r = 0; r < 11; r++) {
					if (z[i][j][r] == 0) {
						continue;
					}
					int rr = (r + a.get(i)) % 11;
					z[i + 1][j + 1][rr] = (z[i + 1][j + 1][rr] + z[i][j][r]) % M;
					rr = (r + 11 - a.get(i)) % 11;
					z[i + 1][j][rr] = (z[i + 1][j][rr] + z[i][j][r]) % M;
				}
			}
		}
		long[] init = z[m][(m + 1) / 2].clone();
		for (int r = 0; r < 11; r++) {
			for (int i = 1; i <= (m / 2); i++) {
				init[r] *= i;
				init[r] %= M;
			}
			for (int i = 1; i <= (m + 1) / 2; i++) {
				init[r] *= i;
				init[r] %= M;
			}
		}
		int o = b.size();
		long[][][][] y = new long[o + 1][n + 2][n + 2][11];
		y[0][m / 2 + 1][(m + 1) / 2] = init;
		for (int i = 0; i < o; i++) {
			for (int j = 0; j <= n; j++) {
				for (int k = 0; k <= n; k++) {
					for (int r = 0; r < 11; r++) {
						if (y[i][j][k][r] == 0) {
							continue;
						}
						int rr = (r + b.get(i)) % 11;
						y[i + 1][j + 1][k][rr] = (y[i + 1][j + 1][k][rr] + y[i][j][k][r] * j) % M;
						rr = (r + 11 - b.get(i)) % 11;
						y[i + 1][j][k + 1][rr] = (y[i + 1][j][k + 1][rr] + y[i][j][k][r] * k) % M;
					}
				}
			}
		}
		long ans = 0;
		for (int j = 0; j <= n + 1; j++) {
			for (int k = 0; k <= n + 1; k++) {
				ans = (ans + y[o][j][k][0]) % M;
			}
		}
		return (int) ans;
	}

	private static int calc11(String n) {
		if (n.length() == 0) {
			return 0;
		}
		return ((n.charAt(n.length() - 1) - '0') - calc11(n.substring(0, n.length() - 1)) + 11) % 11;
	}

}

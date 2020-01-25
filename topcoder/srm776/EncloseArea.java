package topcoder.srm776;

import java.util.Arrays;

public class EncloseArea {
	int n = 50;

	public String[] enclose(int area) {
		if (area > n * n - 2 * n + 2 || area % 2 == 1) return new String[0];
		area /= 2;
		boolean[][] taken = new boolean[n + 1][n + 1];
		for (int x = 1; area > 0; x++) {
			taken[x][x] = true;
			if (--area == 0) break;
			for (int y = 1; y < x; y++) {
				int i = x + y, j = x - y;
				System.out.println(i + " " + j);
				if (i == n) break;
				taken[i][j] = true;
				if (--area == 0) break;
				taken[j][i] = true;
				if (--area == 0) break;
			}
		}
		char[][] f = new char[n][n];
		String[] ans = new String[n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(f[i], '.');
			for (int j = 0; j < n; j++) {
				if ((i + j) % 2 == 0) {
					if (taken[i][j] ^ taken[i + 1][j + 1]) {
						f[i][j] = '/';
					}
				} else {
					if (taken[i + 1][j] ^ taken[i][j + 1]) {
						f[i][j] = '\\';
					}
				}
			}
			ans[i] = new String(f[i]);
		}
		return ans;
	}
}

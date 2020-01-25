package topcoder.srm776;

public class EncloseArea {
	int n = 50;

	public String[] enclose(int area) {
		if (area > n * n - 2 * n + 2 || area % 2 == 1) return new String[0];
		area /= 2;
		boolean[][] taken = new boolean[n + 1][n + 1];
		main:
		for (int x = 1;; x++) {
			for (int y = 0; y < x && x + y < n; y++) {
				int i = x + y, j = x - y;
				taken[i][j] = true;
				if (--area == 0) break main;
				if (y == 0) continue;
				taken[j][i] = true;
				if (--area == 0) break main;
			}
		}
		String[] ans = new String[n];
		char[] s = new char[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s[j] = '.';
				if (taken[i][j] ^ taken[i + 1][j + 1]) s[j] = '/';
				if (taken[i + 1][j] ^ taken[i][j + 1]) s[j] = '\\';
			}
			ans[i] = new String(s);
		}
		return ans;
	}
}

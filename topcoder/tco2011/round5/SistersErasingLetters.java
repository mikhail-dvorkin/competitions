package topcoder.tco2011.round5;
public class SistersErasingLetters {
	String FIRST = "Camomile";
	String SECOND = "Romashka";

	public String whoWins(String s) {
		int n = s.length();
		boolean[][] a = new boolean[n + 1][n + 1];
		boolean[][] b = new boolean[n + 1][n + 1];
		for (int j = 0; j < n; j++) {
			b[n][j] = true;
		}
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				a[i][j] = false;
				b[i][j] = false;
				for (int k = i; k < n; k++) {
					String t = s.substring(0, j) + s.substring(i, k);
					if (s.startsWith(t)) {
						int ii = k + 1;
						int jj = j + k - i;
						if (!b[ii][jj]) {
							a[i][j] = true;
						}
						if (!a[ii][jj]) {
							b[i][j] = true;
						}
					} else {
						if (t.compareTo(s) > 0) {
							a[i][j] = true;
						} else {
							b[i][j] = true;
						}
					}
				}
			}
		}
		return a[0][0] ? FIRST : SECOND;
	}

}

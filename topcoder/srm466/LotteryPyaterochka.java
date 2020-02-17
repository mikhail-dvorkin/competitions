package topcoder.srm466;
public class LotteryPyaterochka {
	public double chanceToWin(int n) {
		double[][] c = new double[5 * n + 1][5 * n + 1];
		for (int i = 0; i <= 5 * n; i++) {
			c[i][0] = c[i][i] = 1;
			for (int j = 1; j < i; j++) {
				c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
			}
		}
		double[][] lose = new double[n + 1][6];
		double[][] win = new double[n + 1][6];
		lose[0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= 5; j++) {
				for (int k = 0; j + k <= 5; k++) {
					int r = (n - i) * 5;
					double pp = (c[5 - j][k] * c[r - 5 + j][5 - k] / c[r][5]);
					if (k <= 2) {
						lose[i + 1][j + k] += lose[i][j] * pp;
						win[i + 1][j + k] += win[i][j] * pp;
					} else {
						win[i + 1][j + k] += (win[i][j] + lose[i][j]) * pp;
					}
				}
			}
		}
		return win[n][5];
	}

}

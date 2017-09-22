package topcoder;
 // Rest in peace, rem.          //
// We will always remember you. //

public class ProductAndSum {
	public static final int M = 500500573;

	public int getSum(int p2, int p3, int p5, int p7, int sum) {
		int[][][][] a = new int[p2 + p3 + 2][p2 + 5][p3 + 5][mx(p2, p3) + 1];
		a[0][0][0][0] = 1;
		int ans = 0;
		for (int i = 0; i < p2 + p3; i++) {
			for (int q2 = 0; q2 <= p2; q2++) {
				for (int q3 = 0; q3 <= p3; q3++) {
					int mx = mx(q2, q3);
					for (int s = 0; s <= mx; s++) {
						int aa = a[i][q2][q3][s];
						if (aa == 0) {
							continue;
						}
						a[i + 1][q2 + 1][q3][s + 2] = p(a[i + 1][q2 + 1][q3][s + 2], aa);
						a[i + 1][q2][q3 + 1][s + 3] = p(a[i + 1][q2][q3 + 1][s + 3], aa);
						a[i + 1][q2 + 2][q3][s + 4] = p(a[i + 1][q2 + 2][q3][s + 4], aa);
						a[i + 1][q2 + 1][q3 + 1][s + 6] = p(a[i + 1][q2 + 1][q3 + 1][s + 6], aa);
						a[i + 1][q2 + 3][q3][s + 8] = p(a[i + 1][q2 + 3][q3][s + 8], aa);
						a[i + 1][q2][q3 + 2][s + 9] = p(a[i + 1][q2][q3 + 2][s + 9], aa);
					}
				}
			}
		}
		
		int[][] cnk = new int[5000][300];
		for (int n = 0; n < cnk.length; n++) {
			cnk[n][0] = 1;
			if (n < cnk[0].length) {
				cnk[n][n] = 1;
			}
			for (int k = 1; k < n && k < cnk[0].length; k++) {
				cnk[n][k] = p(cnk[n - 1][k - 1], cnk[n - 1][k]);
			}
		}
		
		sum -= 5 * p5 + 7 * p7;
		int c57 = cnk[p5 + p7][p5];
		for (int i = 0; i <= p2 + p3; i++) {
			int mx = mx(p2, p3);
			for (int s = 0; s <= mx && s <= sum; s++) {
				//(sum - s, p5, p7);
				int aa = a[i][p2][p3][s];
				if (aa == 0) {
					continue;
				}
				int v = m(aa, cnk[i + sum - s + p5 + p7][p5 + p7]);
				v = m(v, c57);
				v = m(v, cnk[i + sum - s][i]);
				ans = p(ans, v);
			}
		}
		return ans;
	}
	
	private int p(int x, int y) {
		x += y;
		if (x >= M) {
			x -= M;
		}
		return x;
	}

	private int m(int x, int y) {
		return (int) ((x * (long) y) % M);
	}

	int mx(int q2, int q3) {
		return 10 * (q2 + q3);
	}

}

package topcoder.tco2008.round1;
public class Chomp {
	public int moves(int N) {
		int[][][] a = new int[N + 1][N + 1][N + 1];
		a[0][0][1] = -1;
		for (int i = 0; i <= N; i++) {
			for (int j = i; j <= N; j++) {
				for (int k = j; k <= N; k++) {
					if (i + j + k < 2)
						continue;
					boolean win = false;
					int bestneg = -10000000;
					int bestpos = 0;
					int ii, jj, kk;
					for (int x = 1; x <= i; x++) {
						ii = x - 1;
						jj = j;
						kk = k;
						if (a[ii][jj][kk] < 0) {
							win = true;
							bestneg = Math.max(bestneg, a[ii][jj][kk]);
						} else if (!win) {
							bestpos = Math.max(bestpos, a[ii][jj][kk]);
						}
					}
					for (int x = 1; x <= j; x++) {
						ii = Math.min(x - 1, i);
						jj = x - 1;
						kk = k;
						if (a[ii][jj][kk] < 0) {
							win = true;
							bestneg = Math.max(bestneg, a[ii][jj][kk]);
						} else if (!win) {
							bestpos = Math.max(bestpos, a[ii][jj][kk]);
						}
					}
					for (int x = 2; x <= k; x++) {
						ii = Math.min(x - 1, i);
						jj = Math.min(x - 1, j);
						kk = x - 1;
						if (a[ii][jj][kk] < 0) {
							win = true;
							bestneg = Math.max(bestneg, a[ii][jj][kk]);
						} else if (!win) {
							bestpos = Math.max(bestpos, a[ii][jj][kk]);
						}
					}
					if (win) {
						a[i][j][k] = 1 - bestneg;
					} else {
						a[i][j][k] = - 1 - bestpos;
					}
				}
			}
		}
		return a[N][N][N];
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			System.out.println(i + " " + new Chomp().moves(i));
		}
	}
}

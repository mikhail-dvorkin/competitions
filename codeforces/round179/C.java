package codeforces.round179;
import java.util.*;

public class C {
	private static Scanner in;

	final static int M = 1000000007;
	
	public void run() {
		int n = in.nextInt();
		int k = in.nextInt() / 50;
		int small = 0;
		for (int i = 0; i < n; i++) {
			if (in.nextInt() == 50) {
				small++;
			}
		}
		if (small == 0) {
			small = n;
			k /= 2;
		}
		int[][] cnk = new int[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			cnk[i][i] = cnk[i][0] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % M;
			}
		}
		int[][] a = new int[n + 1][n + 1];
		boolean[][] aa = new boolean[n + 1][n + 1];
		a[n][small] = 1;
		aa[n][small] = true;
		for (int t = 0;; t++) {
			int[][] b = new int[n + 1][n + 1];
			boolean[][] bb = new boolean[n + 1][n + 1];
			for (int m = 0; m <= n; m++) {
				for (int s = 0; s <= m; s++) {
					if (!aa[m][s]) {
						continue;
					}
					int h = m - s;
					int mm = n - m;
					int ss = small - s;
					for (int i = 0; i <= s; i++) {
						for (int j = 0; j <= h; j++) {
							if (i + 2 * j > k) {
								break;
							}
							if (i + j == 0) {
								continue;
							}
							long w = a[m][s];
							w = (w * cnk[s][i]) % M;
							w = (w * cnk[h][j]) % M;
							b[mm + i + j][ss + i] = (int) ((b[mm + i + j][ss + i] + w) % M);
							bb[mm + i + j][ss + i] = true;
						}
					}
				}
			}
			a = b;
			aa = bb;
			if ((t % 2 == 0) && (aa[n][small])) {
				System.out.println(t + 1);
				System.out.println(a[n][small]);
				return;
			}
			if (k < 3 && t > 5 * n) {
				System.out.println("-1\n0");
				return;
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}

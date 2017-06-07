package codeforces.round179;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int inf = Integer.MAX_VALUE / 3;
		int n = in.nextInt();
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = in.nextInt();
			}
		}
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			p[n - 1 - i] = in.nextInt() - 1;
		}
		int[][] b = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				b[i][j] = a[p[i]][p[j]];
			}
			b[i][i] = inf;
		}
		long[] ans = new long[n];
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (j == i) {
						continue;
					}
					a[i][j] = Math.min(b[i][j], b[i][k] + b[k][j]);
					if (i <= k && j <= k) {
						ans[k] += a[i][j];
					}
				}
				a[i][i] = inf;
			}
			int[][] c = a; a = b; b = c;
		}
		for (int i = n - 1; i >= 0; i--) {
			System.out.print(ans[i] + " ");
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

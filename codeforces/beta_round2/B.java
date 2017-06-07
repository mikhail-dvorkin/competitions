package codeforces.beta_round2;
import java.io.*;
import java.util.*;

public class B {
	private static BufferedReader in;
	
	int best;
	String ans;
	int n;
	int[][] f;
	boolean[][] from;

	public void run() throws NumberFormatException, IOException {
		n = Integer.parseInt(in.readLine());
		int[][] a = new int[n][n];
		int[][] b = new int[n][n];
		int[][] c = new int[n][n];
		f = new int[n][n];
		from = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int j = 0; j < n; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
				b[i][j] = count(a[i][j], 2);
				c[i][j] = count(a[i][j], 5);
			}
		}
		best = Integer.MAX_VALUE;
		solve(b);
		solve(c);
		solve2(a);
		System.out.println(best);
		System.out.println(ans);
	}

	private int count(int m, int p) {
		if (m == 0) {
			return 100000;
		}
		int res = 0;
		while (m % p == 0) {
			res++;
			m /= p;
		}
		return res;
	}
	
	private void solve(int[][] b) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i > 0 || j > 0) {
					int v1 = (i > 0) ? f[i - 1][j] : Integer.MAX_VALUE;
					int v2 = (j > 0) ? f[i][j - 1] : Integer.MAX_VALUE;
					if (v1 < v2) {
						f[i][j] = v1;
						from[i][j] = true;
					} else {
						f[i][j] = v2;
						from[i][j] = false;
					}
				} else {
					f[i][j] = 0;
				}
				f[i][j] += b[i][j];
			}
		}
		if (f[n - 1][n - 1] < 0) {
			f[n - 1][n - 1] = 1;
		}
		if (f[n - 1][n - 1] < best) {
			best = f[n - 1][n - 1];
			ans = getWay(n - 1, n - 1).toString();
		}
	}

	private void solve2(int[][] a) {
		int ii = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (a[i][j] == 0) {
					ii = i;
				}
			}
		}
		if (ii == -1) {
			return;
		}
		if (1 < best) {
			best = 1;
			StringBuilder sb = new StringBuilder(2 * n - 2);
			for (int i = 0; i < ii; i++) {
				sb.append("D");
			}
			for (int i = 0; i < n - 1; i++) {
				sb.append("R");
			}
			for (int i = ii; i < n - 1; i++) {
				sb.append("D");
			}
			ans = sb.toString();
		}
	}

	private StringBuilder getWay(int i, int j) {
		if (i == 0 && j == 0) {
			return new StringBuilder(2 * n - 2);
		}
		if (from[i][j]) {
			return getWay(i - 1, j).append("D");
		} else {
			return getWay(i, j - 1).append("R");
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new B().run();
		in.close();
	}
}

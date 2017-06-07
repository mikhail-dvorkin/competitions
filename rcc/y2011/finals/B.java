package rcc.y2011.finals;

import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		long x = in.nextLong();
		int n = 300;
		System.out.println(n + " " + n);
		int[][] f = new int[n][n];
		for (int i = 1; i < n; i++) {
			f[i][0] = f[0][i] = 1;
		}
		for (int i = 0; i < 64; i++) {
			if (((x >> i) & 1) == 0) {
				continue;
			}
			int d = 10 + 4 * i;
			for (int j = 0; j <= d; j++) {
				f[j][d - j] = 1;
			}
			for (int j = 2; j <= d - 1; j++) {
				f[j][d - j + 1] = 1;
			}
			for (int j = 2; j < 2 + i; j++) {
				f[j][d - j - 1] = 1;
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(f[i][n - 1 - j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}
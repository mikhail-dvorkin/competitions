package codeforces.yandex2011.round2;
import java.util.*;

public class B {
	private static Scanner in;
	
	int[] di = new int[]{1, 0, -1, 0};
	int[] dj = new int[]{0, 1, 0, -1};

	int hei;
	int wid;
	boolean[][] a;
	int[][] b;
	
	public void run() {
		hei = in.nextInt();
		wid = in.nextInt();
		a = new boolean[hei][wid];
		b = new int[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				a[i][j] = (s.charAt(j) == '#');
			}
		}
		for (int i = 0; i < hei; i++) {
			int cc = (i % 4) * 2;
			for (int j = 0; j < wid; j++) {
				if (a[i][j]) {
					continue;
				}
				int jj = j;
				while (jj + 1 < wid && !a[i][jj + 1]) {
					jj++;
				}
				if (jj == j) {
					continue;
				}
				if (j % 2 == jj % 2) {
					b[i][j] = cc;
					j++;
				}
				for (int k = j; k <= jj; k++) {
					b[i][k] = cc;
					if (k % 2 != j % 2) {
						cc ^= 1;
					}
				}
				j = jj;
			}
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (a[i][j]) {
					continue;
				}
				int dv = -1;
				boolean alone = true;
				boolean horAlone = true;
				for (int d = 0; d < 4; d++) {
					int ii = i + di[d];
					int jj = j + dj[d];
					if (ii >= 0 && jj >= 0 && ii < hei && jj < wid && !a[ii][jj]) {
						alone = false;
						if (di[d] == 0) {
							horAlone = false;
						} else {
							dv = d;
						}
					}
				}
				if (alone) {
					System.out.println(-1);
					return;
				}
				if (horAlone) {
					b[i][j] = b[i + di[dv]][j + dj[dv]];
				}
			}
		}
		int[] t = new int[11];
		for (int i = 0; i + 2 < hei; i++) {
			for (int j = 0; j + 2 < wid; j++) {
				if (a[i + 1][j + 1]) {
					continue;
				}
				int c = b[i + 1][j + 1];
				int cnt = 0;
				for (int ii = 0; ii < 3; ii++) {
					for (int jj = 0; jj < 3; jj++) {
						if (b[ii][jj] == c) {
							cnt++;
						}
					}
				}
				if (cnt <= 5) {
					continue;
				}
				for (int ii = 0; ii < 3; ii++) {
					if (b[ii][j] == c) {
						b[ii][j] = 10;
					}
				}
				Arrays.fill(t, 0);
				for (int ii = i - 1; ii <= i + 3; ii++) {
					for (int jj = j - 1; jj <= j + 1; jj++) {
						if (ii < 0 || jj < 0 || ii >= hei || jj >= wid || a[ii][jj]) {
							continue;
						}
						t[b[ii][jj]]++;
					}
				}
				int cc = 0;
				while (t[cc] > 0) {
					cc++;
				}
				for (int ii = 0; ii < 3; ii++) {
					if (b[ii][j] == 10) {
						b[ii][j] = cc;
					}
				}
			}
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (a[i][j]) {
					System.out.print("#");
				} else {
					System.out.print(b[i][j]);
				}
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

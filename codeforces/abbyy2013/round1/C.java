package codeforces.abbyy2013.round1;
import java.util.*;

public class C {
	private static Scanner in;

	public static final int[] dx = new int[]{1, 0, -1, 0};
	public static final int[] dy = new int[]{0, 1, 0, -1};
	
	public void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int s = hei * wid / 2;
		int[][] a = new int[hei][wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = in.nextInt() - 1;
			}
		}
		int infty = 3 * s;
		int[][] e = new int[s][s];
		for (int i = 0; i < s; i++) {
			Arrays.fill(e[i], infty);
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if ((i + j) % 2 == 0) {
					continue;
				}
				int x = (i * wid + j) / 2;
				for (int d = 0; d < 4; d++) {
					int ii = i + dx[d];
					int jj = j + dy[d];
					if (ii < 0 || jj < 0 || ii >= hei || jj >= wid) {
						continue;
					}
					int y = (ii * wid + jj) / 2;
					e[x][y] = (a[i][j] == a[ii][jj]) ? 0 : 1;
				}
			}
		}
		System.out.println(hungarian(e));
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
	
	/*
	 * infty must be greater than all elements of e
	 */
	public static int hungarian(int[][] e) {
		int infty = Integer.MAX_VALUE / 3;
		int n1 = e.length;
		int n2 = e[0].length;
		int[] u = new int[n1 + 1];
		int[] v = new int[n2 + 1];
		int[] p = new int[n2 + 1];
		int[] way = new int[n2 + 1];
		for (int i = 1; i <= n1; i++) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[n2 + 1]; 
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				double delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						int cur = e[i0 - 1][j - 1] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= n2; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 > 0);
		}
		int sum = 0;
		for (int j = 1; j <= n2; j++) {
			if (p[j] > 0) {
				// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
				sum += e[p[j] - 1][j - 1];
			}
		}
		return sum;
	}
}
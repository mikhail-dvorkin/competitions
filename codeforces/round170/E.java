package codeforces.round170;
import java.util.*;

public class E {
	private static Scanner in;
	final double inf = 1e10;

	public void run() {
		int n = in.nextInt();
		int[] x = new int[n + 1];
		int[] y = new int[n + 1];
		int yMax = Integer.MIN_VALUE;
		int yMaxI = -1;
		int yMin = Integer.MAX_VALUE;
		int yMinI = -1;
		for (int i = 1; i <= n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			if (y[i] > yMax) {
				yMax = y[i];
				yMaxI = i;
			} else if (y[i] == yMax) {
				yMaxI = -1;
			}
			if (y[i] < yMin) {
				yMin = y[i];
				yMinI = i;
			}
		}
		if (yMaxI == -1) {
			System.out.println(-1);
			return;
		}
		double[][] e = new double[n + 1][2 * n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (y[j] > y[i]) {
					e[i][2 * j - 1] = e[i][2 * j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
				} else {
					e[i][2 * j - 1] = e[i][2 * j] = inf;
				}
			}
		}
		e[yMaxI][2 * yMinI] = 0;
		double[] u = new double[n + 1];
		double[] v = new double[2 * n + 1];
		int[] p = new int[2 * n + 1];
		int[] way = new int[2 * n + 1];
		for (int i = 1; i <= n; i++) {
			p[0] = i;
			int j0 = 0;
			double[] minV = new double[2 * n + 1];
			Arrays.fill(minV, inf);
			boolean[] used = new boolean[2 * n + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				double delta = inf;
				for (int j = 1; j <= 2 * n; j++) {
					if (!used[j]) {
						double cur = e[i0][j] - u[i0] - v[j];
						if (cur < minV[j]) {
							minV[j] = cur;
							way[j] = j0;
						}
						if (minV[j] < delta) {
							delta = minV[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= 2 * n; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minV[j] -= delta;
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
		double ans = 0;
		for (int j = 1; j <= 2 * n; j++) {
			if (p[j] > 0) {
				ans += e[p[j]][j];
			}
		}
		if (ans >= inf) {
			System.out.println(-1);
			return;
		}
		System.out.println(ans);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}

package topcoder.srm417;
public class WalkingDistance {
	public double getLongestShortest(int[] x, int[] y, String[] streets) {
		int n = x.length;
		double inf = 1e10;
		double[][] e = new double[n][n];
		double ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (streets[i].charAt(j) == 'Y') {
					e[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
					ans = Math.max(ans, e[i][j]);
				} else {
					e[i][j] = inf;
				}
			}
			e[i][i] = 0;
		}
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				for (int k = 0; k < n; k++) {
					if (e[i][j] + e[j][k] < e[i][k]) {
						e[i][k] = e[i][j] + e[j][k];
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (streets[i].charAt(j) == 'N')
					continue;
				for (int k = i; k < n; k++) {
					for (int l = k + 1; l < n; l++) {
						if (streets[k].charAt(l) == 'N')
							continue;
						if (i == k && j == l)
							continue;
						a = e[i][k];
						b = e[i][l];
						c = e[j][k];
						d = e[j][l];
						M = e[i][j];
						N = e[k][l];
						if (!solve(ans)) {
							continue;
						}
						double lo = 0;
						double hi = M + a + N;
						double mid = (lo + hi) * 0.5;
						while (lo + eps < hi) {
							if (solve(mid)) {
								lo = mid;
							} else {
								hi = mid;
							}
							mid = (lo + hi) * 0.5;
						}
						ans = Math.max(ans, mid);
					}
				}
			}
		}
		return ans;
	}

	double a, b, c, d, M, N;
	final double eps = .3e-9;
	int[] aa = new int[]{1, 1, 1, 1, 1, 1, 0, 0};
	int[] bb = new int[]{1, 1, -1, -1, 0, 0, 1, 1};
	double[] cc = new double[8];

	private boolean solve(double mid) {
		cc[0] = mid - a;
		cc[1] = M + d + N - mid;
		cc[2] = mid - b - N;
		cc[3] = M + c - mid;
		cc[4] = 0;
		cc[5] = M;
		cc[6] = 0;
		cc[7] = N;
		for (int i = 0; i < 8; i++) {
			jloop:
			for (int j = i + 1; j < 8; j++) {
				if (aa[i] == aa[j] && bb[i] == bb[j])
					continue;
				double x = (cc[i] * bb[j] - bb[i] * cc[j]) / (aa[i] * bb[j] - aa[j] * bb[i]);
				double y = (cc[i] * aa[j] - aa[i] * cc[j]) / (- aa[i] * bb[j] + aa[j] * bb[i]);
				for (int k = 0; k < 8; k++) {
					if (aa[k] * x + bb[k] * y < cc[k] - eps)
						continue jloop;
					k++;
					if (aa[k] * x + bb[k] * y > cc[k] + eps)
						continue jloop;
				}
				return true;
			}
		}
		return false;
	}
}

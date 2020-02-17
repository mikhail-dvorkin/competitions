package topcoder;
public class ConstructPolyline {
	int[] x;
	int[] y;
	int[] z;
	double[] nx;
	double[] ny;
	double[] nz;
	int n;
	long ans = 0;
	
	public long maximizeLength(int[] xx, int[] yy, int[] zz) {
		x = xx;
		y = yy;
		z = zz;
		n = x.length;
		nx = new double[n];
		ny = new double[n];
		nz = new double[n];
		for (int i = 0; i < n; i++) {
			check(x[i], y[i], z[i]);
			double len = Math.sqrt(x[i] * x[i] + y[i] * y[i] + z[i] * z[i]);
			nx[i] = x[i] / len;
			ny[i] = y[i] / len;
			nz[i] = z[i] / len;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					continue;
				}
				check(y[i] * z[j] - z[i] * y[j], z[i] * x[j] - x[i] * z[j], x[i] * y[j] - y[i] * x[j]);
				check(nx[i] + nx[j], ny[i] + ny[j], nz[i] + nz[j]);
				if (i < j) {
					for (int k = j + 1; k < n; k++) {
						check(nx[i] + nx[j] + nx[k], ny[i] + ny[j] + ny[k], nz[i] + nz[j] + nz[k]);
					}
				}
			}
		}
		return ans;
	}

	private void check(double xx, double yy, double zz) {
		long xs = 0;
		long ys = 0;
		long zs = 0;
		for (int i = 0; i < n; i++) {
			int sign = (xx * x[i] + yy * y[i] + zz * z[i] > 0) ? 1 : -1;
			xs += sign * x[i];
			ys += sign * y[i];
			zs += sign * z[i];
		}
		long cur = xs * xs + ys * ys + zs * zs;
		ans = Math.max(ans, cur);
	}

}

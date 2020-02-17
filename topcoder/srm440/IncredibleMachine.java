package topcoder.srm440;
public class IncredibleMachine {
	public double gravitationalAcceleration(int[] x, int[] y, int T) {
		int n = x.length;
		double lo = 1e-15;
		double hi = 1e15;
		while (lo + 1e-10 < hi) {
			double g = (lo + hi) * 0.5;
			double v = 0;
			double t = 0;
			for (int i = 0; i < n - 1; i++) {
				double d = Math.hypot(x[i] - x[i + 1], y[i] - y[i + 1]);
				double a = g * (y[i] - y[i + 1]) / d;
				double tt = - v / a + Math.sqrt(v * v / (a * a) + 2 * d / a);
				t += tt;
				v += a * tt;
			}
			if (t > T)
				lo = g;
			else
				hi = g;
		}
		return (lo + hi) / 2;
	}
}

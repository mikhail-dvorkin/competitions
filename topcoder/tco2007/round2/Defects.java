package topcoder.tco2007.round2;
import java.util.*;

public class Defects {
	public double maxSum(int w, int h, double[] defectw, double[] defecth) {
		int n = defecth.length;
		double[] x = new double[n];
		for (int i = 0; i < n; i++) {
			if (defecth[i] == 0) {
				x[i] = defectw[i];
			} else if (defectw[i] == w) {
				x[i] = w + defecth[i];
			} else if (defecth[i] == h) {
				x[i] = w + h + (w - defectw[i]);
			} else {
				x[i] = w + h + w + (h - defecth[i]);
			}
		}
		Arrays.sort(x);
		p = w + h + w + h;
		for (int i = 0; i < n; i++) {
			check(x, x[i]);
			check(x, x[i] + w + h);
		}
		return ans;
	}

	private void check(double[] x, double t) {
		if (t >= p)
			t -= p;
		double s = 0;
		for (int i = 0; i < x.length; i++) {
			double d = Math.abs(t - x[i]);
			if (d > p / 2d)
				d = p - d;
			s += d;
		}
		ans = Math.max(ans, s);
	}

	double ans = 0;
	int p;
}

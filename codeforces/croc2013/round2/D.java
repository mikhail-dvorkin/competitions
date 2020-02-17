package codeforces.croc2013.round2;
import java.util.*;

public class D {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[] x = new int[n + 1];
		int[] y = new int[n + 1];
		int xMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			xMin = Math.min(xMin, x[i]);
			xMax = Math.max(xMax, x[i]);
		}
		x[n] = x[0];
		y[n] = y[0];
		double sx = 0;
		double sx2 = 0;
		double sy = 0;
		double sy2 = 0;
		double cnt = 0;
		int i = 0;
		while (x[i] != xMin) {
			i++;
		}
		int j = i;
		if (j == 0) j = n;
		double slope1 = (y[i + 1] - y[i]) * 1.0 / (x[i + 1] - x[i]);
		double slope2 = (y[j - 1] - y[j]) * 1.0 / (x[j - 1] - x[j]);
		for (int t = xMin; t <= xMax; t++) {
			if (t < xMax) {
				while (x[i + 1] <= t) {
					i++;
					if (i == n) {
						i = 0;
					}
					slope1 = (y[i + 1] - y[i]) * 1.0 / (x[i + 1] - x[i]);
				}
				while (x[j - 1] <= t) {
					j--;
					if (j == 0) {
						j = n;
					}
					slope2 = (y[j - 1] - y[j]) * 1.0 / (x[j - 1] - x[j]);
				}
			}
			double y1 = y[i] + (t - x[i]) * slope1;
			double y2 = y[j] + (t - x[j]) * slope2;
			if (y1 > y2) {
				double p = y1; y1 = y2; y2 = p;
			}
			double yFrom = Math.ceil(y1);
			double yTo = Math.floor(y2) + 1;
			double m = yTo - yFrom;
			cnt += m;
			sx += m * 1.0 * t;
			sx2 += m * 1.0 * t * t;
			sy += m * 0.5 * (yFrom + yTo - 1);
			sy2 += ((yTo - 1) * 1.0 * (yTo) * (2 * yTo - 1) - (yFrom - 1) * 1.0 * (yFrom) * (2 * yFrom - 1)) / 6;
		}
		double ans = (sx2 * cnt - sx * sx + sy2 * cnt - sy * sy) / cnt / (cnt - 1);
		System.out.println(ans);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}

package topcoder;
import java.util.*;

public class DoraemonPuzzleGame {
	class Level implements Comparable<Level> {
		double x, y, s;

		public Level(double x, double y) {
			this.x = x;
			this.y = y;
			this.s = x + y;
		}

		@Override
		public int compareTo(Level that) {
			return Double.compare(y, that.y);
		}
	}
	
    public double solve(int[] xs, int[] ys, int m) {
    	int n = xs.length;
    	m -= n;
    	Level[] levels = new Level[n];
    	for (int i = 0; i < n; i++) {
    		levels[i] = new Level(xs[i] / 1000.0, ys[i] / 1000.0);
    	}
    	Arrays.sort(levels);
    	double[] p = new double[n + 1];
    	double[] pt = new double[n + 1];
    	double[] q = new double[n + 1];
    	double[] qt = new double[n + 1];
    	p[0] = 1;
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j <= i; j++) {
    			if (n - i + j == m) {
    				q[j + 1] += p[j];
    				qt[j + 1] += pt[j];
    				qt[j + 1] += p[j] * (1.0 / levels[i].y);
    			} else {
    				q[j + 1] += p[j] * levels[i].y / levels[i].s;
    				qt[j + 1] += pt[j] * levels[i].y / levels[i].s;
    				qt[j + 1] += p[j] * levels[i].y / levels[i].s * (1.0 / levels[i].s);
    				q[j] += p[j] * levels[i].x / levels[i].s;
    				qt[j] += pt[j] * levels[i].x / levels[i].s;
    				qt[j] += p[j] * levels[i].x / levels[i].s * (1.0 / levels[i].s);
    			}
    		}
    		double[] t = p; p = q; q = t;
    		t = pt; pt = qt; qt = t;
    		Arrays.fill(q, 0);
    		Arrays.fill(qt, 0);
    	}
    	double ans = 0;
    	for (int i = 0; i <= n; i++) {
    		ans += pt[i];
    	}
    	return ans;
    }

}

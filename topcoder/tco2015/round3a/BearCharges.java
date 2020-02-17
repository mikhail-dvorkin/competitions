package topcoder.tco2015.round3a;
import java.util.*;

public class BearCharges {
	final double INF = 1e100;

	int n;
	double[][] d;
	double best;

    public double minTime(int[] x, int[] y) {
    	n = x.length;
    	if (n == 1) {
    		return 0;
    	}
    	Random r = new Random(566);
    	for (int i = 2; i < n; i++) {
    		int j = 1 + r.nextInt(i);
    		int t = x[i]; x[i] = x[j]; x[j] = t;
    		t = y[i]; y[i] = y[j]; y[j] = t;
    	}
    	d = new double[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			d[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
    		}
    	}
    	started = new double[n];
    	best = INF;
    	search(1, 0);
    	return best;
    }

    double[] started;

    void search(int our, double time) {
    	if (time > best) {
    		return;
    	}
    	if (our == (1 << n) - 1) {
    		best = Math.min(best, time);
    		return;
    	}
    	for (int i = 0; i < n; i++) {
			if (((our >> i) & 1) != 0) {
				continue;
			}
			double bestEat = INF;
	    	for (int j = 0; j < n; j++) {
				if (((our >> j) & 1) == 0) {
					continue;
				}
				bestEat = Math.min(started[j] + d[j][i], bestEat);
	    	}
	    	if (bestEat > best) {
	    		return;
	    	}
    	}
    	for (int i = 0; i < n; i++) {
			if (((our >> i) & 1) == 0) {
				continue;
			}
			for (int j = 0; j < n; j++) {
				int our2 = our | (1 << j);
				if (our2 == our) {
					continue;
				}
//				double t = Math.max(started[i] + d[i][j], time);
				double t = started[i] + d[i][j];
				if (t + 1e-9 < time) {
					continue;
				}
				started[i] += d[i][j];
				started[j] = t;
				search(our2, t);
				started[i] -= d[i][j];
			}
		}
	}

}

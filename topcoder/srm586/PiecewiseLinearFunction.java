package topcoder;
public class PiecewiseLinearFunction {
    public int maximumSolutions(int[] y) {
    	int n = y.length;
    	for (int i = 1; i < n; i++) {
    		if (y[i - 1] == y[i]) {
    			return -1;
    		}
    	}
    	int best = 0;
    	for (int i = 0; i < n; i++) {
    		for (int e = -1; e <= 1; e++) {
    			int cur = 0;
    			long v = 2L * y[i] + e;
    			for (int j = 1; j < n; j++) {
    				long a = 2L * Math.min(y[j - 1], y[j]);
    				long b = 2L * Math.max(y[j - 1], y[j]);
    				if (a < v && v < b) {
    					cur++;
    				}
    			}
    			for (int j = 0; j < n; j++) {
    				long a = 2L * y[j];
    				if (a == v) {
    					cur++;
    				}
    			}
    			best = Math.max(best, cur);
    		}
    	}
    	return best;
    }

}

package topcoder.srm635;
public class SimilarRatingGraph {
    public double maxLength(int[] date, int[] rating) {
    	int n = date.length;
    	double ans = 0;
    	int[] ps = new int[n - 1];
    	int[] qs = new int[n - 1];
    	double[] hypot = new double[n - 1];
    	for (int i = 0; i + 1 < n; i++) {
    		ps[i] = date[i + 1] - date[i];
    		qs[i] = rating[i + 1] - rating[i];
    		hypot[i] = Math.hypot(ps[i], qs[i]);
    	}
    	for (int i = 0; i < n; i++) {
    		for (int j = i + 1; j < n; j++) {
    			long p01 = 0, p02 = 0;
    			double cur = 0;
    			for (int k = 0;; k++) {
    				if (i + k + 1 == n || j + k + 1 == n) {
    					break;
    				}
    				long p1 = ps[i + k];
    				long q1 = qs[i + k];
    				long p2 = ps[j + k];
    				long q2 = qs[j + k];
    				if (p1 * q2 != p2 * q1) {
    					break;
    				}
    				if (k == 0) {
    					p01 = p1;
    					p02 = p2;
    				} else {
    					if (p1 * p02 != p2 * p01) {
    						break;
    					}
    				}
    				if (p1 > p2) {
    					cur += hypot[i + k];
    				} else {
    					cur += hypot[j + k];
    				}
    				ans = Math.max(ans, cur);
    			}
    		}
    	}
    	return ans;
    }

}

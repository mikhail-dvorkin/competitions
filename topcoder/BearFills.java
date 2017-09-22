package topcoder;
public class BearFills {
    public long countSets(int n, long w, long h) {
    	if (w < h) {
    		long t = w; w = h; h = t;
    	}
    	int m = 0;
    	while ((1L << m) < w) {
    		m++;
    	}
    	long ans = 0;
    	if (m < n) {
    		ans += (1L << n) - (1L << m);
    	}
    	m = Math.min(m - 1, n - 1);
    	if (m == -1 || (1L << m) < h) {
    		return ans;
    	}
    	return ans + countSets(m, w - (1L << m), h);
    }

}

package topcoder.tco2013.round3b;
/*
 * WRONG SOLUTION
 */
public class ToastJumping {
    public int[] minJumps(int[] x, int[] y, int[] d) {
    	int n = x.length;
    	int[] ans = new int[n];
    	for (int i = 0; i < n; i++) {
    		ans[i] = minJumps(x[i], y[i], d[i]);
    	}
    	return ans;
    }

    int minJumps(long x, long y, long d) {
    	x = Math.abs(x);
    	y = Math.abs(y);
    	if (x < y) {
    		long t = x; x = y; y = t;
    	}
    	if (x == 0) {
    		return 0;
    	}
    	double a = Math.sqrt(d) * x / Math.sqrt(x * x + y * y);
    	long ans = Long.MAX_VALUE;
    	for (int i = -3; i <= 3; i++) {
    		for (int j = 1; j <= 3; j++) {
    			for (int p = 0; p <= 3; p++) {
        			for (int q = 0; q <= 3; q++) {
                    	long a1 = i + (long) Math.floor(a);
                    	long a2 = a1 + j;
                    	if (a2 * a2 > d) {
                    		continue;
                    	}
                    	if (a1 < 0) {
                    		continue;
                    	}
                    	long xx = x;
                    	long yy = y;
                    	int plus = 0;
                    	if (p > 0) {
                    		if (1 < j) {
                    			long aa = a1 + 1;
                    			long bb = (long) Math.floor(Math.sqrt(d - aa * aa));
                    			xx -= aa * p;
                    			yy -= bb * p;
                    			plus += p;
                    		} else {
                    			continue;
                    		}
                    	}
                    	if (q > 0) {
                    		if (2 < j) {
                    			long aa = a1 + 2;
                    			long bb = (long) Math.floor(Math.sqrt(d - aa * aa));
                    			xx -= aa * q;
                    			yy -= bb * q;
                    			plus += q;
                    		} else {
                    			continue;
                    		}
                    	}
                    	xx = Math.abs(xx);
                    	yy = Math.abs(yy);
                    	if (xx < y) {
                    		long t = xx; xx = yy; yy = t;
                    	}
                    	long b1 = (long) Math.floor(Math.sqrt(d - a1 * a1));
                    	long b2 = (long) Math.floor(Math.sqrt(d - a2 * a2));
                    	long low = 0;
                    	long high = xx + yy;
                    	while (low + 1 < high) {
                    		long v = (low + high) / 2;
                    		long u = 0;
                    		if (xx > v * a1) {
                    			u = (xx - v * a1 + j - 1) / j;
                    		}
                    		if (u > v) {
                    			low = v;
                    			continue;
                    		}
                    		long maxY = u * b2 + (v - u) * b1;
                    		if (yy <= maxY) {
                    			high = v;
                    		} else {
                    			low = v;
                    		}
                    	}
                    	ans = Math.min(ans, high + plus);
        			}
    			}
    		}
    	}
		return (int) ans;
	}

}

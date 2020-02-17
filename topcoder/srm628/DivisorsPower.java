package topcoder.srm628;
public class DivisorsPower {
    public long findArgument(long n) {
    	for (int p = 2; p < 70; p++) {
    		long m = Math.round(Math.pow(n, 1.0 / p));
    		if (Math.pow(m, p) > n * 1.01) {
    			continue;
    		}
    		long nn = 1;
    		for (int i = 0; i < p; i++) {
    			nn *= m;
    		}
    		if (n != nn) {
    			continue;
    		}
    		int divs = 0;
    		for (long i = 1; i * i <= m; i++) {
    			if (m % i == 0) {
    				if (i * i == m) {
    					divs++;
    				} else {
    					divs += 2;
    				}
    			}
    		}
    		if (divs == p) {
    			return m;
    		}
    	}
    	return -1;
    }

}

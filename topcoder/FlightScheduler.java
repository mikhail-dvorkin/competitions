package topcoder;
public class FlightScheduler {
	int d, k, m, f0;
	
    public double minimizeFuel(int distance, int K, int emptyMass, int takeoffFuel) {
    	d = distance;
    	k = K;
    	m = emptyMass;
    	f0 = takeoffFuel;
    	double ans = solve(1);
    	for (int i = 1; i <= d; i++) {
    		ans = Math.min(ans, solve(i));
    	}
    	long low = 1;
    	long high = (long) 1e17;
    	while (low + 3 <= high) {
    		long a = (2 * low + high) / 3;
    		long b = (low + 2 * high) / 3;
    		double sa = solve(a);
    		double sb = solve(b);
			if (sb < sa) {
				ans = Math.min(ans, sb);
				low = a;
    		} else {
    			ans = Math.min(ans, sa);
    			high = b;
    		}
    	}
    	for (long i = 0; i < 1000; i++) {
    		long j = Math.max(low - 500, 1) + i;
			ans = Math.min(ans, solve(j));
    	}
    	return ans;
    }
    
    double solve(long n) {
    	return n * (f0 - m + m * Math.exp(d * 1.0 / k / n));
    }

}

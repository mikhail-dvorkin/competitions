package topcoder;
import java.util.Arrays;

public class SubtreeSumHash {
    static final int M = 1_000_000_007;

	public int count(int[] weight, int[] p, int x) {
    	int n = weight.length;
    	int[] h = new int[n];
    	Arrays.fill(h, 1);
    	int ans = 0;
    	for (int v = n - 1;; v--) {
    		h[v] = (int) ((h[v] * power(x, weight[v])) % M);
    		ans = (ans + h[v]) % M;
    		if (v == 0) {
    			break;
    		}
    		h[p[v - 1]] = (int) ((h[p[v - 1]] * (h[v] + 1L)) % M);
    	}
    	return ans;
    }
	
	static long power(long base, long p) {
		if (p == 0)
			return 1;
		if (p == 1)
			return base % M;
		long v = power(base, p / 2);
		v = v * v % M;
		return (p & 1) == 0 ? v : (v * base) % M;
	}

    
}

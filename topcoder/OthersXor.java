package topcoder;
public class OthersXor {
	int M = 30;
	
    public long minSum(int[] x) {
    	int n = x.length;
    	int absent = -1;
    	int present = -1;
    	for (int i = 0; i < n; i++) {
    		if (x[i] == -1) {
    			absent = i;
    		} else {
    			present = i;
    		}
    	}
    	if (present == -1) {
    		return 0;
    	}
    	long ans = 0;
    	for (int k = 0; k < M; k++) {
    		int best = Integer.MAX_VALUE;
    		for (int p = 0; p < 2; p++) {
    			int sum = p;
    			for (int i = 0; i < n; i++) {
    				if (i == present) {
    					continue;
    				}
    				if (x[i] == -1) {
    					continue;
    				}
    				int xor = ((x[present] ^ x[i]) >> k) & 1;
    				sum += p ^ xor;
    			}
    			int xor = (x[present] >> k) & 1;
    			if (xor != ((sum ^ p) & 1)) {
    				if (absent == -1) {
    					continue;
    				}
    				sum++;
    			}
    			best = Math.min(best, sum);
    		}
    		if (best == Integer.MAX_VALUE) {
    			return -1;
    		}
    		ans += best * 1L * (1 << k);
    	}
    	return ans;
    }

}

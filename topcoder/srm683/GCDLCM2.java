package topcoder;
import java.util.*;

public class GCDLCM2 {
	int M = 1000000007;
	int MAX = 10_000_000;
	
    public int getMaximalSum(int[] start, int[] d, int[] cnt) {
    	int m = start.length;
    	int[] count = new int[MAX + 1];
    	int n = 0;
    	for (int i = 0; i < m; i++) {
    		for (int j = 0; j < cnt[i]; j++) {
    			int x = start[i] + j * d[i];
    			count[x]++;
    		}
    		n += cnt[i];
    	}
		boolean[] skip = new boolean[MAX + 1];
		ArrayList<Integer> powers = new ArrayList<>();
    	int[] array = new int[n];
    	Arrays.fill(array, 1);
		for (int p = 2; p <= MAX; p++) {
			if (skip[p]) {
				continue;
			}
			powers.clear();
			for (int i = p; i <= MAX; i += p) {
				skip[i] = true;
				if (count[i] == 0) {
					continue;
				}
	    		int c = 1;
	    		int j = i / p;
	    		while (j % p == 0) {
	    			j /= p;
	    			c++;
	    		}
	    		for (int k = 0; k < count[i]; k++) {
	    			powers.add(c);
				}
			}
    		Collections.sort(powers);
    		long v = 1;
    		int cur = 0;
    		int size = powers.size();
    		for (int i = 0; i < size; i++) {
    			int pow = powers.get(i);
    			while (cur < pow) {
    				cur++;
    				v = v * p % M; 
    			}
    			int j = size - 1 - i;
    			array[j] = (int) (v * array[j] % M);
    		}
    	}
    	int ans = 0;
    	for (int x : array) {
    		ans = (ans + x) % M;
    	}
    	return ans;
    }

}

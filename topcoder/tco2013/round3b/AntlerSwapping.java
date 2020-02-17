package topcoder.tco2013.round3b;
import java.util.*;

public class AntlerSwapping {
    public int getmin(int[] a, int[] b, int c) {
    	int n = a.length;
    	int[] t = new int[2 * n];
    	int[] op = new int[1 << n];
    	int inf = n + 1;
    	Arrays.fill(op, inf);
    	maskLoop:
    	for (int mask = 1; mask < (1 << n); mask++) {
    		int k = 0;
    		for (int i = 0; i < n; i++) {
    			if (((mask >> i) & 1) == 0) {
    				continue;
    			}
    			t[k++] = a[i];
    			t[k++] = b[i];
    		}
    		Arrays.sort(t, 0, k);
    		for (int i = 0; i < k; i += 2) {
    			if (t[i + 1] > t[i] + c) {
    				continue maskLoop;
    			}
    		}
    		k /= 2;
    		op[mask] = k - 1;
    	}
    	int[] v = new int[1 << n];
    	Arrays.fill(v, inf);
    	v[0] = 0;
    	for (int mask = 1; mask < (1 << n); mask++) {
    		for (int mm = mask; mm > 0; mm = (mm - 1) & mask) {
    			v[mask] = Math.min(v[mask], v[mask ^ mm] + op[mm]);
    		}
    	}
    	int ans = v[(1 << n) - 1];
    	if (ans == inf) {
    		return -1;
    	}
    	return ans;
    }

}

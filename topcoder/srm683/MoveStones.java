package topcoder.srm683;
import java.util.*;

public class MoveStones {
    public long get(int[] a, int[] b) {
    	int n = a.length;
    	long sumA = 0;
    	long sumB = 0;
    	for (int i = 0; i < n; i++) {
    		sumA += a[i];
    		sumB += b[i];
    	}
    	if (sumA != sumB) {
    		return -1;
    	}
    	long[] d = new long[n];
    	for (int i = 1; i < n; i++) {
    		d[i] = d[i - 1] + b[i] - a[i];
    	}
    	Arrays.sort(d);
    	long c = d[n / 2];
    	long ans = 0;
    	for (int i = 0; i < n; i++) {
    		ans += Math.abs(d[i] - c);
    	}
    	return ans;
    }

}

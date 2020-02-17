package topcoder.srm667;
import java.util.*;

public class OrderOfOperations {
    public int minTime(String[] s) {
    	int n = s.length;
    	int[] use = new int[n];
    	int all = 0;
    	for (int i = 0; i < n; i++) {
    		use[i] = Integer.parseInt(s[i], 2);
    		all |= use[i];
    	}
    	int[] a = new int[all + 1];
    	int inf = Integer.MAX_VALUE / 3;
    	Arrays.fill(a, inf);
    	a[0] = 0;
    	for (int mask = 0; mask < all; mask++) {
    		if (a[mask] == inf) {
    			continue;
    		}
    		for (int i = 0; i < n; i++) {
    			int newMask = mask | use[i];
    			int pay = Integer.bitCount(newMask ^ mask);
    			pay *= pay;
    			a[newMask] = Math.min(a[newMask], a[mask] + pay);
    		}
    	}
    	return a[all];
    }

}

package topcoder.srm680;
import java.util.*;

public class BearFair {
	String fair = "fair";
	String unfair = "unfair";

    public String isFair(int n, int max, int[] upTo, int[] quantity) {
    	int[] count = new int[max + 1];
    	Arrays.fill(count, -1);
    	count[max] = n;
    	count[0] = 0;
    	for (int i = 0; i < upTo.length; i++) {
    		if (count[upTo[i]] != -1 && count[upTo[i]] != quantity[i]) {
    			return unfair;
    		}
    		count[upTo[i]] = quantity[i];
    	}
    	int j = 0;
    	boolean[] a = new boolean[n / 2 + 1];
    	a[0] = true;
    	for (int i = 1; i <= max; i++) {
    		if (count[i] == -1) {
    			continue;
    		}
    		int s = i - j;
    		int sEven = i / 2 - j / 2;
    		int sOdd = s - sEven;
    		int c = count[i] - count[j];
    		if (c < 0) {
    			return unfair;
    		}
    		boolean[] b = new boolean[n / 2 + 1];
    		for (int k = 0; k <= n / 2; k++) {
    			if (!a[k]) {
    				continue;
    			}
        		for (int t = k; t <= n / 2; t++) {
        			int cEven = t - k;
        			int cOdd = c - cEven;
        			if (cEven <= sEven && 0 <= cOdd && cOdd <= sOdd) {
        				b[t] = true;
        			}
        		}
    		}
    		a = b;
    		j = i;
    	}
    	if (a[n / 2]) {
    		return fair;
    	}
    	return unfair;
    }

}

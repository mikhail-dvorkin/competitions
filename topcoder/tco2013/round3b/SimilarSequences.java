package topcoder;
public class SimilarSequences {
	final int M = 1000000009;
	
    public int count(int[] a, int m) {
    	int n = a.length;
    	long ans = 1;
    	for (int i = 0; i < n; i++) {
    		ans += m - 1;
    	}
    	for (int i = 0; i < n; i++) {
    		jloop:
    		for (int j = i + 1; j < n; j++) {
    			if (a[i] != a[i + 1]) {
    				ans += m - 1;
    			}
    			if (a[j] != a[j - 1]) {
    				ans += m - 1;
    			}
    			if (a[i] != a[i + 1] && a[j] != a[j - 1]) {
    				for (int k = i; k <= j - 2; k++) {
    					if (a[k] != a[k + 2]) {
    						continue jloop;
    					}
    				}
    				ans--;
    			}
    		}
    	}
    	return (int) (ans % M);
    }

}

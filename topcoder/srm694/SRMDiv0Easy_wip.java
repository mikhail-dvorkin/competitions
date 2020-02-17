package topcoder.srm694;
public class SRMDiv0Easy_wip {
    public int get(int n, int[] from, int[] to, int[] x, int[] y) {
    	int q = from.length;
    	int[] a = new int[n];
    	long[][] c = new long[n + 1][n + 1];
    	for (int k = 0; k < q; k++) {
    		to[k]++;
    		c[from[k]][to[k]] += y[k] - x[k];
    		for (int i = from[k]; i < to[k]; i++) {
    			a[i] += x[k];
    		}
    	}
    	long[] d = new long[n + 1];
    	for (int i = 1; i < n; i++) {
    		d[i] = a[i - 1] - a[i];
    	}
    	int f = 0; //maxFlowWithDifferences(c, d, 0, n);
    	if (f < 0) {
    		return f;
    	}
    	return a[0] + f;
    }

}

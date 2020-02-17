package topcoder.srm685;
public class FoxAirline2 {
    public String isPossible(int n, int[] a, int[] b) {
    	int[][] e = new int[n][n];
    	int edges = a.length;
    	for (int i = 0; i < edges; i++) {
    		e[a[i]][b[i]]++;
    		e[b[i]][a[i]]++;
    	}
    	int[][] c = new int[1 << n][n + 1];
    	int[] ones = new int[1 << n];
    	for (int mask = 0; mask < (1 << n); mask++) {
    		for (int i = 0; i < n; i++) {
    			if (((mask >> i) & 1) == 0) {
    				continue;
    			}
    			ones[mask]++;
        		for (int j = i + 1; j < n; j++) {
        			if (((mask >> j) & 1) == 0) {
        				continue;
        			}
        			c[mask][1] += e[i][j];
        		}
    		}
    	}
    	for (int mask = 1; mask < (1 << n); mask++) {
    		for (int mask2 = 1; mask2 < (1 << n); mask2++) {
    			if ((mask & mask2) != 0) {
    				continue;
    			}
    			for (int i = 1; i <= ones[mask]; i++) {
    				c[mask | mask2][i + 1] = Math.max(c[mask | mask2][i + 1], c[mask][i] + c[mask2][1]);
    			}
    		}
    	}
    	for (int i = 1; i <= n; i++) {
    		if (edges - c[(1 << n) - 1][i] < 2 * (i - 1)) {
    			return "Impossible";
    		}
    	}
    	return "Possible";
    }

}

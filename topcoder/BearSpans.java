package topcoder;
public class BearSpans {
    public int[] findAnyGraph(int n, int m, int k) {
    	int[][] e = new int[n][n];
    	if (31 - Integer.numberOfLeadingZeros(n) < k) {
    		return new int[]{-1};
    	}
    	int step = 1;
    	for (int r = 0; r < k - 1; r++) {
			int s = 1 << r;
			for (int i = s; i < n; i += 2 * s) {
				e[i - 1][i] = step++;
			}
    	}
		for (int i = 1; i < n; i++) {
			if (e[i - 1][i] == 0) {
				e[i - 1][i] = step++;
			}
		}
    	int[] ans = new int[2 * m];
    	for (int i = 0; i < n; i++) {
    		for (int j = i + 1; j < n; j++) {
    			if (e[i][j] == 0 && step <= m) {
    				e[i][j] = step++;
    			}
    			int w = e[i][j];
    			if (w > 0) {
    				ans[2 * w - 2] = i + 1;
    				ans[2 * w - 1] = j + 1;
    			}
    		}
    	}
    	return ans;
    }

}

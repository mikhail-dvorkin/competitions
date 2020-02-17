package topcoder.srm685;
public class MultiplicationTable2 {
    public int minimalGoodSet(int[] table) {
    	int n = (int) Math.round(Math.sqrt(table.length));
    	int[][] a = new int[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			a[i][j] = table[i * n + j];
    		}
    	}
    	int ans = n;
    	for (int s = 0; s < n; s++) {
    		boolean[] take = new boolean[n];
    		take[s] = true;
    		for (;;) {
    			boolean grow = false;
    			for (int i = 0; i < n; i++) {
    				if (!take[i]) {
    					continue;
    				}
        			for (int j = 0; j < n; j++) {
        				if (!take[j]) {
        					continue;
        				}
        				if (!take[a[i][j]]) {
        					grow = true;
        					take[a[i][j]] = true;
        				}
        			}
    			}
    			if (!grow) {
    				break;
    			}
    		}
    		int cur = 0;
    		for (int i = 0; i < n; i++) {
    			if (take[i]) {
    				cur++;
    			}
    		}
    		ans = Math.min(ans, cur);
    	}
    	return ans;
    }

}

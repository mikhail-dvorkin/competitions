package topcoder;
import java.util.*;

public class FarStrings {
    public String[] find(String s) {
    	int n = s.length();
    	String[] ans = new String[n];
    	char[] c = new char[n];
    	int[][] min = new int[n + 1][n + 1];
    	int[][] max = new int[n + 1][n + 1];
    	for (int d = 1; d <= n; d++) {
    		Arrays.fill(c, ' ');
    		for (int x = 0; x < n; x++) {
    			for (c[x] = 'a'; c[x] <= 'z'; c[x]++) {
    				for (int i = 0; i <= n; i++) {
    					for (int j = 0; j <= n; j++) {
    						if (i == 0 || j == 0) {
    							min[i][j] = max[i][j] = i + j;
    							continue;
    						}
    						int eqMin, eqMax;
    						if (c[j - 1] != ' ') {
    							eqMin = eqMax = (s.charAt(i - 1) == c[j - 1] ? 0 : 1);
    						} else {
    							eqMin = 0;
    							eqMax = 1;
    						}
    						min[i][j] = Math.min(min[i - 1][j - 1] + eqMin, Math.min(min[i - 1][j], min[i][j - 1]) + 1);
    						max[i][j] = Math.min(max[i - 1][j - 1] + eqMax, Math.min(max[i - 1][j], max[i][j - 1]) + 1);
    					}
    				}
    				if (min[n][n] <= d && d <= max[n][n]) {
    					break;
    				}
    			}
    		}
    		ans[d - 1] = new String(c);
    	}
    	return ans;
    }

}

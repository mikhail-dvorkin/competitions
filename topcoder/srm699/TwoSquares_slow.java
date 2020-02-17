package topcoder.srm699;
public class TwoSquares_slow {
    public int maxOnes(String[] strings) {
    	int n = strings.length;
    	boolean[][] f = new boolean[n][n];
    	int[][] s = new int[n + 1][n + 1];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			f[i][j] = strings[i].charAt(j) == '1';
    			s[i + 1][j + 1] = s[i + 1][j] + s[i][j + 1] - s[i][j] + (f[i][j] ? 1 : 0);
    		}
    	}
    	int ans = s[n][n];
    	for (int i1 = 0; i1 < n; i1++) {
        	for (int j1 = 0; j1 < n; j1++) {
        		for (int s1 = 1; i1 + s1 <= n && j1 + s1 <= n; s1++) {
        			int o1 = s[i1 + s1][j1 + s1] - s[i1 + s1][j1] - s[i1][j1 + s1] + s[i1][j1];
        			int a1 = s1 * s1;
        			ans = Math.max(ans, s[n][n] + a1 - 2 * o1);
        			for (int i2 = 0; i2 < n; i2++) {
        				for (int j2 = 0; j2 < n; j2++) {
        	        		for (int s2 = 1; i2 + s2 <= n && j2 + s2 <= n; s2++) {
        	        			int o2 = s[i2 + s2][j2 + s2] - s[i2 + s2][j2] - s[i2][j2 + s2] + s[i2][j2];
        	        			int a2 = s2 * s2;
        	        			int i3 = Math.max(i1, i2);
        	        			int i4 = Math.min(i1 + s1, i2 + s2);
        	        			int j3 = Math.max(j1, j2);
        	        			int j4 = Math.min(j1 + s1, j2 + s2);
        	        			int o3 = 0;
        	        			int a3 = 0;
        	        			if (i3 < i4 && j3 < j4) {
        	        				o3 = s[i4][j4] - s[i4][j3] - s[i3][j4] + s[i3][j3];
        	        				a3 = (i4 - i3) * (j4 - j3);
        	        			}
        	        			int cur = s[n][n] + a1 - a3 - 2 * (o1 - o3) + a2 - a3 - 2 * (o2 - o3);
        	        			ans = Math.max(ans, cur);
        	        		}
        				}
        			}
        		}
        	}
    	}
    	return ans;
    }

}

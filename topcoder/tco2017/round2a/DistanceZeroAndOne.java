package topcoder.tco2017.round2a;
import java.util.*;

public class DistanceZeroAndOne {
    public String[] construct(int[] dist0, int[] dist1) {
    	int n = dist0.length;
    	int inf = n + 1;
    	int[][] e = new int[n][n];
    	for (int i = 0; i < n; i++) {
    		Arrays.fill(e[i], inf);
    		for (int j = 0; j < n; j++) {
    			if (Math.abs(dist0[i] - dist0[j]) <= 1 && Math.abs(dist1[i] - dist1[j]) <= 1) {
    				e[i][j] = 1;
    			}
    		}
    		e[i][i] = 0;
    	}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (e[i][j] > e[i][k] + e[k][j]) {
						e[i][j] = e[i][k] + e[k][j];
					}
				}
			}
		}
		String[] ans = new String[n];
		for (int i = 0; i < n; i++) {
			if (e[0][i] != dist0[i] || e[1][i] != dist1[i]) {
				return new String[0];
			}
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < n; j++) {
				sb.append(e[i][j] == 1 ? "Y" : "N");
			}
			ans[i] = sb.toString();
		}
		return ans;
    }

}

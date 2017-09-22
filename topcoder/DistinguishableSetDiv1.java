package topcoder;
public class DistinguishableSetDiv1 {
    public int count(String[] strings) {
    	int n = strings.length;
    	int m = strings[0].length();
    	char[][] s = new char[n][];
    	boolean[] bad = new boolean[1 << m];
    	for (int i = 0; i < n; i++) {
    		s[i] = strings[i].toCharArray();
    		for (int j = 0; j < i; j++) {
    			int mask = 0;
    			for (int k = 0; k < m; k++) {
    				if (s[i][k] == s[j][k]) {
    					mask |= 1 << k;
    				}
    			}
    			bad[mask] = true;
    		}
    	}
    	int ans = 0;
    	for (int mask = (1 << m) - 1; mask >= 0; mask--) {
    		if (!bad[mask]) {
    			ans++;
    			continue;
    		}
    		for (int i = 0; i < m; i++) {
    			bad[mask & ~(1 << i)] = true;
    		}
    	}
    	return ans;
    }

}

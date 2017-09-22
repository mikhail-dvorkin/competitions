package topcoder;
public class CountingCommonSubsequences {
    public long countCommonSubsequences(String a, String b, String c) {
    	int an = a.length();
    	int bn = b.length();
    	int cn = c.length();
    	long[][][] e = new long[an + 1][bn + 1][cn + 1];
    	for (int i = 0; i <= an; i++) {
    		for (int j = 0; j <= bn; j++) {
    			for (int k = 0; k <= cn; k++) {
    				if (i == 0 || j == 0 || k == 0) {
    					e[i][j][k] = 1;
    				}
    			}
    		}
    	}
    	for (int i = 0; i < an; i++) {
    		for (int j = 0; j < bn; j++) {
    			for (int k = 0; k < cn; k++) {
    				char ac = a.charAt(i);
    				char bc = b.charAt(j);
    				char cc = c.charAt(k);
    				int ii = i - 1;
    				while (ii >= 0 && a.charAt(ii) != ac) {
    					ii--;
    				}
    				int jj = j - 1;
    				while (jj >= 0 && b.charAt(jj) != bc) {
    					jj--;
    				}
    				int kk = k - 1;
    				while (kk >= 0 && c.charAt(kk) != cc) {
    					kk--;
    				}
    				if (ac == bc && bc == cc) {
    					e[i + 1][j + 1][k + 1] = 2 * e[i][j][k];
    					if (ii >= 0 && jj >= 0 && kk >= 0) {
    						e[i + 1][j + 1][k + 1] -= e[ii][jj][kk];
    					}
    				} else {
        				e[i + 1][j + 1][k + 1] =
        						e[i + 1][j + 1][k] +
        						e[i + 1][j][k + 1] +
        						e[i][j + 1][k + 1] -
        						e[i + 1][j][k] -
        						e[i][j + 1][k] -
        						e[i][j][k + 1] +
        						e[i][j][k];
    				}
    			}
    		}
    	}
    	return e[an][bn][cn] - 1;
    }

}

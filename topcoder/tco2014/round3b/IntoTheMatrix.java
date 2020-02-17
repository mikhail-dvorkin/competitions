package topcoder;
public class IntoTheMatrix {
    public int takePills(int turns, int bags) {
    	if (bags == 1) {
    		return 0;
    	}
    	int c = 40;
    	int[][] cnk = new int[c + 1][c + 1];
    	for (int i = 0; i <= c; i++) {
    		cnk[i][0] = cnk[i][i] = 1;
    		for (int j = 1; j < i; j++) {
    			cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
    		}
    	}
    	int lo = 0;
    	int hi = c;
    	while (lo + 1 < hi) {
    		int f = (lo + hi) / 2;
    		long[][] a = new long[turns + 1][f + 1];
    		for (int i = 0; i <= f; i++) {
    			a[0][i] = 1;
    		}
    		for (int t = 1; t <= turns; t++) {
    			a[t][0] = 1;
    			for (int i = 1; i <= f; i++) {
    				for (int j = 0; j <= i; j++) {
    					a[t][i] += a[t - 1][i - j] * cnk[i][j];
    				}
    			}
    		}
    		boolean can = a[turns][f] >= bags;
    		if (can) {
    			hi = f;
    		} else {
    			lo = f;
    		}
    	}
    	return hi;
    }

}

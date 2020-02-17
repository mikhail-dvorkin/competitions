package topcoder.srm694;
public class TrySail {
	static final int MAX = 256;

    public int get(int[] values) {
    	boolean[][][] a = new boolean[MAX][MAX][8];
    	a[0][0][0] = true;
    	int xor = 0;
    	for (int v : values) {
    		xor ^= v;
    		boolean[][][] b = new boolean[MAX][MAX][8];
    		for (int i = 0; i < MAX; i++) {
    			for (int j = 0; j < MAX; j++) {
    				for (int m = 0; m < 8; m++) {
    					if (!a[i][j][m]) {
    						continue;
    					}
    					for (int k = 0; k < 3; k++) {
    						int mm = m | (1 << k);
    						int ii = i;
    						int jj = j;
    						if (k == 0) {
    							ii ^= v;
    						} else if (k == 1) {
    							jj ^= v;
    						}
    						b[ii][jj][mm] = true;
    					}
    				}
    			}
    		}
    		a = b;
    	}
    	int ans = 0;
		for (int i = 0; i < MAX; i++) {
			for (int j = 0; j < MAX; j++) {
				if (a[i][j][7]) {
					ans = Math.max(ans, i + j + (xor ^ i ^ j));
				}
			}
		}
		return ans;
    }

}

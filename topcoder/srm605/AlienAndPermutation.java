package topcoder.srm605;
public class AlienAndPermutation {
	final static int M = 1000000007;

    public int getNumber(int[] p, int operations) {
    	int n = p.length;
    	int[] pos = new int[n];
    	for (int i = 0; i < n; i++) {
    		p[i]--;
    		pos[p[i]] = i;
    	}
    	int[][] max = new int[n][n + 1];
    	for (int len = 1; len <= n; len++) {
    		for (int from = 0; from + len <= n; from++) {
    			int to = from + len;
    			if (len == 1) {
    				max[from][to] = p[from];
    			} else {
    				max[from][to] = Math.max(max[from + 1][to], p[from]);
    			}
    		}
    	}
    	int[][][] a = new int[n][n][operations + 2];
    	int[][][] b = new int[n][n][operations + 2];
    	for (int x = 0; x < n; x++) {
    		if (x == p[0]) {
    			a[0][x][0] = 1;
    		} else {
    			if (max[0][pos[x] + 1] == x) {
    				b[0][x][1] = 1;
    			}
    		}
    	}
    	for (int i = 1; i < n; i++) {
    		int pi = p[i];
    		for (int x = 0; x < n; x++) {
    			for (int ops = 0; ops <= operations; ops++) {
    				for (int s = 0; s < 2; s++) {
    					int aCur = (s == 0) ? a[i - 1][x][ops] : b[i - 1][x][ops];
    					if (aCur == 0) {
    						continue;
    					}
    					if (x >= pi) {
    						if (s == 1) {
    							b[i][x][ops] = (b[i][x][ops] + aCur) % M;
    						} else {
    							b[i][x][ops + 1] = (b[i][x][ops + 1] + aCur) % M;
    						}
    					}
    					for (int j = pos[x] + 1; j < n; j++) {
    						int y = p[j];
    						if (j == i) {
    							a[i][y][ops] = (a[i][y][ops] + aCur) % M;
    							continue;
    						}
    						int mx = max[Math.min(i, j)][Math.max(i, j) + 1];
    						if (mx != y) {
    							continue;
    						}
    						b[i][y][ops + 1] = (b[i][y][ops + 1] + aCur) % M;
    					}
    				}
    			}
    		}
    	}
    	int ans = 0;
    	for (int x = 0; x < n; x++) {
    		for (int ops = 0; ops <= operations; ops++) {
   				ans = (ans + a[n - 1][x][ops]) % M;
   				ans = (ans + b[n - 1][x][ops]) % M;
    		}
    	}
    	return ans;
    }

}

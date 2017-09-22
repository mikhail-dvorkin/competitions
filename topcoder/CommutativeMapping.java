package topcoder;
import java.util.*;

public class CommutativeMapping {
	final int M = 1000000007;
	
	int n;
	int[] f;
	ArrayList<Integer>[] prev;
	int[][] a;
	boolean[] onCycle;
	int[] cycleSize;
	int[] cycleId;

	
    @SuppressWarnings("unchecked")
	public int count(int[] array) {
    	this.f = array;
    	n = array.length;
    	prev = new ArrayList[n];
    	for (int i = 0; i < n; i++) {
    		prev[i] = new ArrayList<Integer>();
    	}
    	for (int i = 0; i < n; i++) {
    		prev[f[i]].add(i);
    	}
    	a = new int[n][n];
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			a[i][j] = -1;
    		}
    	}
    	onCycle = new boolean[n];
    	cycleSize = new int[n];
    	cycleId = new int[n];
    	for (int i = 0; i < n; i++) {
    		int x = i;
    		cycleId[i] = i;
    		for (int j = 1; j <= n; j++) {
    			x = array[x];
    			cycleId[i] = Math.min(cycleId[i], x);
    			if (x == i) {
    				onCycle[i] = true;
    				cycleSize[i] = j;
    				break;
    			}
    		}
    	}
    	for (int i = 0; i < n; i++) {
    		if (onCycle[i]) {
    			continue;
    		}
    		int x = i;
    		while (!onCycle[x]) {
    			x = array[x];
    		}
    		cycleId[i] = cycleId[x];
    	}
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			calc(i, j);
    		}
    	}
    	long ans = 1;
    	for (int k = 0; k < n; k++) {
    		long ansK = 0;
    		if (!onCycle[k] || cycleId[k] != k) {
    			continue;
    		}
    		int c = cycleSize[k];
    		for (int j = 0; j < n; j++) {
    			if (!onCycle[j]) {
    				continue;
    			}
    			if (c % cycleSize[j] != 0) {
    				continue;
    			}
   				long here = 1;
   				int x = k;
   				int y = j;
   				for (int i = 0; i < c; i++) {
   					here *= a[x][y];
   					here %= M;
   					x = array[x];
   					y = array[y];
   				}
   				ansK += here;
   				ansK %= M;
    		}
    		ans = (ans * ansK) % M;
    	}
    	
    	// TEST
    	if (array.length == 3) {
    		int b = 0;
    		int[] g = new int[f.length];
    		for (g[0] = 0; g[0] < 3; g[0]++) {
    			for (g[1] = 0; g[1] < 3; g[1]++) {
    				for (g[2] = 0; g[2] < 3; g[2]++) {
    					if (f[g[0]] != g[f[0]]) {
    						continue;
    					}
    					if (f[g[1]] != g[f[1]]) {
    						continue;
    					}
    					if (f[g[2]] != g[f[2]]) {
    						continue;
    					}
    					b++;
    				}
    			}
    		}
    		if (ans != b) {
    			throw new RuntimeException("" + b);
    		}
    	}
    	
    	return (int) ans;
    }

    void calc(int x, int y) {
    	if (a[x][y] >= 0) {
    		return;
    	}
		long res = 1;
		for (int p : prev[x]) {
			if (onCycle[p]) {
				continue;
			}
			int resP = 0;
			for (int q : prev[y]) {
				calc(p, q);
				resP += a[p][q];
				resP %= M;
			}
			res = (res * resP) % M;
		}
		a[x][y] = (int) res;
	}

}

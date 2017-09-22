package topcoder;
import java.util.*;

public class KeyDungeonDiv1 {
    public int maxKeys(int[] doorR, int[] doorG, int[] roomR, int[] roomG, int[] roomW, int[] keys) {
    	int n = doorR.length;
    	int kR = keys[0];
    	int kG = keys[1];
    	int kW = keys[2];
    	int max = 10 * n;
    	byte[][][] a = new byte[max + 1][max + 1][1 << n];
    	for (int i = 0; i < a.length; i++) {
    		for (int j = 0; j < a[0].length; j++) {
    			Arrays.fill(a[i][j], (byte) -1);
    		}
    	}
    	a[kR][kG][0] = (byte) kW;
    	int ans = 0;
    	for (int mask = 0; mask < (1 << n); mask++) {
    		for (int r = 0; r <= max; r++) {
    			for (int g = 0; g <= max; g++) {
    				int w = a[r][g][mask];
    				if (w == -1) {
    					continue;
    				}
    				if (w < -1) {
    					w += 256;
    				}
    				ans = Math.max(ans, r + g + w);
    				for (int i = 0; i < n; i++) {
    					int nmask = mask | (1 << i);
    					if (mask == nmask) {
    						continue;
    					}
    					int nr = r;
    					int ng = g;
    					int nw = w;
    					int dr = doorR[i];
    					int dg = doorG[i];
    					if (nr >= dr) {
    						nr -= dr;
    					} else {
    						nw -= (dr - nr);
    						nr = 0;
    					}
    					if (ng >= dg) {
    						ng -= dg;
    					} else {
    						nw -= (dg - ng);
    						ng = 0;
    					}
    					if (nw < 0) {
    						continue;
    					}
    					nr += roomR[i];
    					ng += roomG[i];
    					nw += roomW[i];
    					int u = 0;
    					try {
							u = a[nr][ng][nmask];
						} catch (Exception e) {
	    					if (nr > max) {
	    						nw += nr - max;
	    						nr = max;
	    					}
	    					if (ng > max) {
	    						nw += ng - max;
	    						ng = max;
	    					}
							u = a[nr][ng][nmask];
						}
    					if (u < -1) {
    						u += 256;
    					}
    					a[nr][ng][nmask] = (byte) Math.max(u, nw);
    				}
    			}
    		}
    	}
    	return ans;
    }

}

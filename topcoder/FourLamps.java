package topcoder;
import java.util.*;

public class FourLamps {
    public long count(String init, int ops) {
    	int n = init.length() - 1;
    	ArrayList<Integer> gs = new ArrayList<>();
    	boolean prev = false;
    	for (int i = 1; i <= n; i++) {
    		boolean eq = init.charAt(i - 1) == init.charAt(i);
    		if (i > 1 && eq != prev) {
    			gs.add(i - 1);
    		}
    		prev = eq;
    	}
    	int g = gs.size();
    	if (g == 0 || g == n - 1 || ops == 0) {
    		return 1;
    	}
    	long[][][] a = new long[g + 1][n][g * n + 1];
    	a[0][0][0] = 1;
    	for (int i = 0; i < g; i++) {
    		for (int j = 0; j < n; j++) {
    			for (int d = 0; d <= i * n; d++) {
    				long aa = a[i][j][d];
    				if (aa == 0) {
    					continue;
    				}
    				for (int k = j + 1; k < n; k++) {
    					a[i + 1][k][d + Math.abs(gs.get(i) - k)] += aa;
    				}
    			}
    		}
    	}
    	long ans = 0;
    	for (int j = 0; j < n; j++) {
    		for (int k = 0; k <= g * n && k <= ops; k++) {
    			ans += a[g][j][k];
    		}
    	}
    	return 2 * ans - ((ops == 1) ? 1 : 0);
    }

}

package topcoder;
import java.util.*;

public class CombinationLockDiv1 {
    public int minimumMoves(String[] p, String[] q) {
    	StringBuilder sb = new StringBuilder();
    	for (String s : p) {
    		sb.append(s);
    	}
    	StringBuilder tb = new StringBuilder();
    	for (String s : q) {
    		tb.append(s);
    	}
    	String s = sb.toString();
    	String t = tb.toString();
    	int n = s.length();
    	int pa = 0;
    	int pb = 0;
    	int prevUp = 0;
    	int prevDown = 0;
    	for (int i = 0; i < n; i++) {
    		int up = t.charAt(i) - s.charAt(i);
    		if (up < 0) {
    			up += 10;
    		}
    		int down = s.charAt(i) - t.charAt(i);
    		if (down < 0) {
    			down += 10;
    		}
    		int a = Math.min(pb + up, pa + Math.max(up - prevUp, 0));
    		int b = Math.min(pa + down, pb + Math.max(down - prevDown, 0));
    		prevUp = up;
    		prevDown = down;
    		pa = a;
    		pb = b;
    	}
    	int ans = 5 * n; //Math.min(pa, pb);
    	int m = 20;
    	int[] a = new int[2 * m + 1];
    	int[] b = new int[2 * m + 1];
    	int inf = Integer.MAX_VALUE / 3;
    	Arrays.fill(a, inf);
    	a[n] = 0;
    	for (int i = 0; i < n; i++) {
    		Arrays.fill(b, inf);
//    		int shift = (i == 0) ? 0 : (t.charAt(i - 1) - s.charAt(i - 1));
    		for (int j = -m; j <= m; j++) {
    			if (a[m + j] == inf) {
    				continue;
    			}
    			int moves = (i == 0) ? 0 : ((10 * j + t.charAt(i - 1)) - s.charAt(i - 1));
    			for (int k = -m; k <= m; k++) {
    				int need = 10 * k + t.charAt(i) - s.charAt(i);
    				int steps = 0;
    				if (moves >= 0) {
    					if (need >= moves) {
    						steps = need - moves;
    					} else if (need < 0) {
    						steps = -need;
    					}
    				} else {
    					if (need <= moves) {
    						steps = moves - need;
    					} else if (need > 0) {
    						steps = need;
    					}
    				}
    				int cur = a[m + j] + steps;
    				b[m + k] = Math.min(b[m + k], cur);
    			}
    		}
    		System.arraycopy(b, 0, a, 0, b.length);
    	}
    	for (int i : a) {
    		ans = Math.min(ans, i);
    	}
		return ans;
    }

}

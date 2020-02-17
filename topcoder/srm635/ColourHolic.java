package topcoder.srm635;
import java.util.*;

public class ColourHolic {
	final int M = 1000000009;

    public int countSequences(int[] count) {
    	Arrays.sort(count);
    	int a = count[0];
    	int b = count[1];
    	int c = count[2];
    	int d = count[3];
    	int[] xs = new int[a + b + 1];
    	if (a + b == 0) {
    		xs[0] = 1;
    	} else {
    		int[][] ta = new int[a + 1][a + b + 2];
    		int[][] tb = new int[a + 1][a + b + 2];
    		if (a >= 1) {
    			ta[1][0] = 1;
    		}
    		if (b >= 1) {
    			tb[0][0] = 1;
    		}
    		for (int i = 1; i < a + b; i++) {
        		int[][] qa = new int[a + 1][a + b + 2];
        		int[][] qb = new int[a + 1][a + b + 2];
    			for (int j = 0; j <= a; j++) {
    				for (int k = 0; k <= a + b; k++) {
    					if (ta[j][k] == 0 && tb[j][k] == 0) {
    						continue;
    					}
    					if (j < a) {
    						qa[j + 1][k + 1] += ta[j][k];
    						qa[j + 1][k + 1] %= M;
    						qa[j + 1][k] += tb[j][k];
    						qa[j + 1][k] %= M;
    					}
    					if (i - j < b) {
    						qb[j][k + 1] += tb[j][k];
    						qb[j][k + 1] %= M;
    						qb[j][k] += ta[j][k];
    						qb[j][k] %= M;
    					}
    				}
    			}
    			ta = qa;
    			tb = qb;
    		}
    		for (int k = 0; k <= a + b; k++) {
    			xs[k] += ta[a][k];
    			xs[k] %= M;
    			xs[k] += tb[a][k];
    			xs[k] %= M;
    		}
    	}
    	int[][] cnk = new int[a + b + d + 1][a + b + 2];
    	for (int i = 0; i < cnk.length; i++) {
    		cnk[i][0] = 1;
    		if (i < cnk[i].length) {
    			cnk[i][i] = 1;
    		}
    		for (int j = 1; j < i && j < cnk[i].length; j++) {
    			cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % M;
    		}
    	}
    	int[] s = new int[a + b + 2];
    	for (int i = 1; i <= a + b + 1; i++) {
    		for (int cc = 0; cc <= i; cc++) {
    			int dd = cc - c + d;
    			int ee = i - cc - dd;
    			if (cc + ee > c || dd < 0 || dd + ee > d || ee < 0) {
    				continue;
    			}
    			int leftover = c - cc - ee;
    			if (d - dd - ee != leftover) {
    				throw new RuntimeException();
    			}
    			long cur = cnk[i][ee];
    			cur *= cnk[i - ee][cc];
    			cur %= M;
    			cur *= cnk[leftover + i - 1][i - 1];
    			cur %= M;
    			for (int j = 0; j < ee; j++) {
    				cur *= 2;
    				if (cur >= M) {
    					cur -= M;
    				}
    			}
    			s[i] += cur;
    			s[i] %= M;
    		}
    	}
    	int ans = 0;
		for (int k = 0; k <= a + b; k++) {
			if (xs[k] == 0) {
				continue;
			}
			int kk = a + b + 1 - k;
			for (int j = 0; j <= kk; j++) {
				long cur = cnk[kk][j];
				cur *= s[k + j];
				cur %= M;
				cur *= xs[k];
				cur %= M;
				ans += cur;
				ans %= M;
			}
		}
    	return ans;
    }

}

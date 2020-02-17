package topcoder.srm667;
import java.util.*;

public class BipartiteGraphGame_wrong {
	int[] ans;
	int pos;
	boolean[][] used;
	int[] a;
	int r;

    public int[] getMoves(int[] red, int[] blue) {
    	r = red.length;
    	int n = r + blue.length;
    	int[] b = new int[n];
    	for (int i = 0; i < r; i++) {
    		b[i] = red[i];
    	}
    	for (int i = r; i < n; i++) {
    		b[i] = blue[i - r] + r;
    	}
    	Random random = new Random(566);
    	ans = new int[2000];
    	int[] bad = new int[n];
    	int[] good = new int[n];
    	main:
    	for (;;) {
    		a = b.clone();
    		used = new boolean[n][n];
    		pos = 0;
    		int sos = 0;

    		for (;;) {
    			int bads = 0;
    			for (int i = 0; i < n; i++) {
    				if (a[i] == i) {
    					continue;
    				}
   					bad[bads++] = i;
    			}
    			if (bads == 0) {
    				return Arrays.copyOf(ans, pos);
    			}
   				int j = bad[random.nextInt(bads)];
   				if ((j < r) ^ (a[j] < r)) {
   					if (used[j][a[j]]) {
   	   					sos++;
   	   					if (sos == 2) {
   	   						continue main;
   	   					}
   					}
   					use(j, a[j]);
   					continue;
   				}
   				int goods = 0;
   				for (int i = 0; i < n; i++) {
   					if ((j < r) ^ (i < r)) {
   						if (!used[i][j] && !used[i][a[j]]) {
   							good[goods++] = i;
   						}
   					}
   				}
   				if (goods == 0) {
   					sos++;
   					if (sos == 2) {
   						continue main;
   					}
   					goods = 0;
   					for (int i = 0; i < n; i++) {
   						if ((j < r) ^ (i < r)) {
   							if (!used[i][j] ^ !used[i][a[j]]) {
   								good[goods++] = i;
   							}
   						}
   					}
   					if (goods == 0) {
   						continue main;
   					}
   				}
   				int i = good[random.nextInt(goods)];
   				int aj = a[j];
   				use(i, j);
   				use(i, aj);
    		}
    	}
    }

    void use(int j, int i) {
    	if (i > j) {
    		int t = i; i = j; j = t;
    	}
    	used[i][j] = used[j][i] = true;
    	int t = a[i]; a[i] = a[j]; a[j] = t;
    	ans[pos++] = i;
    	ans[pos++] = j - r;
	}

}

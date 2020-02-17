package topcoder;
import java.util.*;

public class LineMST {
	final static int M = 1000000007;
	
    public int count(int n, int max) {
    	int[][] a = new int[n + 1][max + 1];
    	int[][] pow = new int[max + 1][n * n];
    	for (int i = 1; i < pow.length; i++) {
    		pow[i][0] = 1;
    		for (int j = 1; j < pow[i].length; j++) {
    			pow[i][j] = (int) (pow[i][j - 1] * 1L * i % M);
    		}
    	}
    	Arrays.fill(a[1], 1);
    	for (int i = 2; i <= n; i++) {
    		for (int m = 1; m <= max; m++) {
    			if (m > 1) {
    				a[i][m] = a[i][m - 1];
    			}
    			for (int j = 1; j < i; j++) {
    				long v = a[j][m - 1];
    				v *= a[i - j][m];
    				v %= M;
    				v *= pow[max - m + 1][(i - j) * j - 1];
    				v %= M;
    				a[i][m] = (a[i][m] + (int) v) % M;
    			}
    		}
    	}
    	long v = a[n][max];
    	for (int i = 3; i <= n; i++) {
    		v *= i;
    		v %= M;
    	}
    	return (int) v;
    }

}

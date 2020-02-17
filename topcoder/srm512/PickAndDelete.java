package topcoder;
import java.util.*;

public class PickAndDelete {
	static final int MOD = 1000000007;
	
	public int count(String[] input) {
		StringBuilder sb = new StringBuilder();
		for (String s : input) {
			sb.append(s);
		}
		String[] ss = sb.toString().split(" ");
		int n = ss.length;
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(ss[i]);
		}
		Arrays.sort(a);
		int[][] c = new int[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			c[i][0] = c[i][i] = 1;
			for (int j = 1; j < i; j++) {
				c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % MOD;
			}
		}
		int[][] p = new int[n + 1][n + 1];
		p[0][0] = 1;
		int[] pow = new int[n + 1];
		pow[0] = 1;
		for (int i = 1; i <= n; i++) {
			int pp = a[i - 1] - (i == 1 ? 0 : a[i - 2]);
			for (int j = 1; j <= n; j++) {
				pow[j] = (int) ((pow[j - 1] * (long) pp) % MOD);
			}
			for (int j = i; j <= n; j++) {
				for (int k = 0; k <= j; k++) {
					long v = p[i - 1][j - k];
					v = v * pow[k];
					v %= MOD;
					v *= c[j][k];
					v %= MOD;
					p[i][j] = (p[i][j] + (int) v) % MOD;
				}
			}
		}
		return p[n][n];
	}

}

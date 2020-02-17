package topcoder;
import java.util.*;

public class BoxesArrangement {
	public int maxUntouched(String s) {
		int n = s.length();
		if (n < 3)
			return n;
		int[] a = new int[n];
		int[] am = new int[3];
		for (int i = 0; i < n; i++) {
			a[i] = s.charAt(i) - 'A';
			am[a[i]]++;
		}
		int[][][][] r = new int[n + 1][n + 1][n + 1][9];
		int infty = 1000;
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				for (int k = 0; k <= n; k++) {
					Arrays.fill(r[i][j][k], -infty);
				}
			}
		}
		int[] z = new int[3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				z[x]++;
				z[y]++;
				int left = 0;
				if (x == a[0])
					left++;
				if (y == a[1])
					left++;
				r[z[0]][z[1]][z[2]][3 * x + y] = left;
				z[x]--;
				z[y]--;
			}
		}
		for (int len = 2; len < n; len++) {
			for (z[0] = 0; z[0] <= len; z[0]++) {
				for (z[1] = 0; z[1] <= len; z[1]++) {
					z[2] = len - z[0] - z[1];
					if (z[2] < 0)
						continue;
					for (int last = 0; last < 9; last++) {
						int q1 = last / 3;
						int q2 = last % 3;
						for (int q3 = 0; q3 < 3; q3++) {
							if (q1 == q2 && q2 == q3)
								continue;
							int cur = r[z[0]][z[1]][z[2]][last];
							if (q3 == a[len])
								cur++;
							z[q3]++;
							r[z[0]][z[1]][z[2]][3 * q2 + q3] = Math.max(r[z[0]][z[1]][z[2]][3 * q2 + q3], cur);
							z[q3]--;
						}
					}
				}
			}
		}
		int ans = -infty;
		for (int last = 0; last < 9; last++)
			ans = Math.max(ans, r[am[0]][am[1]][am[2]][last]);
		if (ans < 0)
			return -1;
		return ans;
	}
	
	public static void main(String[] args) {
		//ABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABC
	}
}

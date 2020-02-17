package topcoder.srm531;
import java.util.*;

public class MonsterFarm {
	long M = 1000000007;

	public int numMonsters(String[] transforms) {
		int n = transforms.length;
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++) {
			String[] s = transforms[i].split(" ");
			a[i] = new int[s.length];
			for (int j = 0; j < s.length; j++) {
				a[i][j] = Integer.parseInt(s[j]) - 1;
			}
		}
		int[] s = new int[n];
		Arrays.fill(s, 1);
		int[] t;
		for (int iter = 0; iter < 3 * n + 10; iter++) {
			t = new int[n];
			for (int i = 0; i < n; i++) {
				for (int j : a[i]) {
					t[i] += s[j];
				}
				t[i] = Math.min(t[i], 2);
			}
			s = t;
		}
		boolean[] superb = new boolean[n];
		long[] ans = new long[n];
		for (int i = 0; i < n; i++) {
			superb[i] = (s[i] == 1);
			ans[i] = 1;
		}
		for (;;) {
			boolean improve = false;
			for (int i = 0; i < n; i++) {
				if (superb[i]) {
					continue;
				}
				boolean nice = true;
				ans[i] = 0;
				for (int j : a[i]) {
					if (superb[j]) {
						ans[i] += ans[j];
						ans[i] %= M;
					} else {
						nice = false;
					}
				}
				if (nice) {
					superb[i] = true;
					improve = true;
				}
			}
			if (improve) {
				continue;
			}
			break;
		}
		if (superb[0]) {
			return (int) ans[0];
		}
		return -1;
	}

}

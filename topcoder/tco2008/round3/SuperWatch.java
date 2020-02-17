package topcoder;
import java.util.Arrays;

public class SuperWatch {
	int n, f, z;
	boolean[][] e;
	
	public int smallestImprecision(String[] times, int[] zones) {
		n = times.length;
		int[] t = new int[n];
		int[][] r = new int[n][n];
		e = new boolean[n][n];
		lp = new int[n];
		rp = new int[n];
		were = new boolean[n];
		for (int i = 0; i < n; i++) {
			int h = (times[i].charAt(0) - '0') * 10 + (times[i].charAt(1) - '0');
			int m = (times[i].charAt(3) - '0') * 10 + (times[i].charAt(4) - '0');
			t[i] = h * 60 + m;
		}
		int max = 24 * 60;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				r[i][j] = (t[i] - 60 * zones[j] + max) % max;
			}
		}
		int ans = max;
		for (f = 0; f < n; f++) {
			for (int w = 0; w < 24; w++) {
				z = 0;
				while (z < n && zones[z] != w)
					z++;
				if (z == n)
					continue;
				int t0 = r[f][z];
				int le = -1;
				int ri = max;
				while (le + 1 < ri) {
					int mi = (le + ri) / 2;
					for (int i = 0; i < n; i++) {
						if (i == f)
							continue;
						for (int j = 0; j < n; j++) {
							if (j == z)
								continue;
							int t1 = r[i][j] - t0;
							if (t1 < 0)
								t1 += max;
							e[i][j] = (t1 <= mi);
						}
					}
					if (match()) {
						ri = mi;
					} else {
						le = mi;
					}
				}
				ans = Math.min(ans, ri);
			}
		}
		return ans;
	}

	int[] lp, rp;
	boolean[] were;
	
	private boolean match() {
		Arrays.fill(lp, -1);
		Arrays.fill(rp, -1);
		for (int i = 0; i < n; i++) {
			if (i == f)
				continue;
			Arrays.fill(were, false);
			if (!dfs(i))
				return false;
		}
		return true;
	}

	private boolean dfs(int i) {
		were[i] = true;
		for (int j = 0; j < n; j++) {
			if (j == z || !e[i][j])
				continue;
			if (lp[j] == -1) {
				lp[j] = i;
				rp[i] = j;
				return true;
			}
			if (!were[lp[j]] && dfs(lp[j])) {
				lp[j] = i;
				rp[i] = j;
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		int a = new SuperWatch().smallestImprecision(new String[]
{"02:00", "07:00"}
, new int[]
{2, 23}
);
		System.out.println(a);
	}
}

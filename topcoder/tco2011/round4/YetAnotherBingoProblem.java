package topcoder;
import java.util.*;

public class YetAnotherBingoProblem {
	int N = 75;
	
	public int longestWinningSequence(String[] cards1, String[] cards2) {
		boolean[][] me = parse(cards1);
		boolean[][] he = parse(cards2);
		int ans = -1;
		for (int win = 0; win < me.length; win++) {
			for (int last = 0; last < N; last++) {
				if (!me[win][last]) {
					continue;
				}
				ArrayList<boolean[]> kill = new ArrayList<boolean[]>();
				for (boolean[] b : he) {
					kill.add(b);
				}
				for (boolean[] b : me) {
					if (!b[last]) {
						kill.add(b);
					}
				}
				int ks = kill.size();
				int max = 1 << ks;
				int[] d = new int[max];
				int inf = Integer.MAX_VALUE / 3;
				Arrays.fill(d, inf);
				d[0] = 0;
				int[] mm = new int[N];
				for (int i = 0; i < N; i++) {
					if (me[win][i]) {
						continue;
					}
					for (int j = 0; j < ks; j++) {
						if (kill.get(j)[i]) {
							mm[i] |= 1 << j;
						}
					}
				}
				for (int mask = 0; mask < max; mask++) {
					if (d[mask] == inf) {
						continue;
					}
					for (int i = 0; i < N; i++) {
						if (me[win][i]) {
							continue;
						}
						int nm = mask | mm[i];
						d[nm] = Math.min(d[nm], d[mask] + 1);
					}
				}
				if (d[max - 1] < inf) {
					ans = Math.max(ans, N - d[max - 1]);
				}
			}
		}
		return ans;
	}

	private boolean[][] parse(String[] cards) {
		StringBuilder sb = new StringBuilder();
		for (String s : cards) {
			sb.append(s);
		}
		String[] ss = sb.toString().split(",");
		int n = ss.length;
		boolean[][] a = new boolean[n][N];
		for (int i = 0; i < n; i++) {
			String[] s = ss[i].split(" ");
			for (int j = 0; j < s.length; j++) {
				a[i][Integer.parseInt(s[j]) - 1] = true;
			}
//			System.out.println(Arrays.toString(a[i]));
		}
		return a;
	}

}

package topcoder.tco2009.round4;
import java.util.*;

public class ChuckContest {
	boolean better(int p1, int t1, int p2, int t2) {
		if (p1 == p2) {
			return t1 < t2;
		}
		return p1 > p2;
	}

	public String chuckRules(int numProblems, String[] lowerBounds, String[] upperBounds, int[] partTimes) {
		int inf = 10000000;
		int n = partTimes.length;
		int T = partTimes[n - 1];
		int[] low1 = new int[T + 1];
		int[] low2 = new int[T + 1];
		int[] hi1 = new int[T + 1];
		int[] hi2 = new int[T + 1];
		for (int i = 0; i < n; i++) {
			int t = partTimes[i];
			String[] s = lowerBounds[i].split(" ");
			low1[t] = Integer.parseInt(s[0]);
			low2[t] = Integer.parseInt(s[1]);
			s = upperBounds[i].split(" ");
			hi1[t] = Integer.parseInt(s[0]);
			hi2[t] = Integer.parseInt(s[1]);
		}
		int[][][] a = new int[T + 1][numProblems + 1][20];
		int[][][] b = new int[T + 1][numProblems + 1][20];
		for (int t = 0; t <= T; t++) {
			for (int p = 0; p <= numProblems; p++) {
				Arrays.fill(a[t][p], inf);
				Arrays.fill(b[t][p], 0);
			}
		}
		a[0][0][0] = 0;
		b[0][0][0] = 0;
		int ans1 = 0;
		int ans2 = inf;
		for (int t = 0; t <= T; t++) {
			int t1 = t + 1;
			for (int p = 0; p <= numProblems; p++) {
				for (int r = 0; r < 20; r++) {
					if (a[t][p][r] == inf)
						continue;
					if (low1[t] > 0) {
						if (p < low1[t])
							continue;
						if (p > hi1[t])
							continue;
						if (p == low1[t]) {
							int tt = low2[t] - 1;
							int dec = (tt % 20) - r;
							if (dec < 0)
								dec += 20;
							tt -= dec;
							b[t][p][r] = Math.min(b[t][p][r], tt);
						}
						if (p == hi1[t]) {
							int tt = hi2[t] + 1;
							int inc = r - (tt % 20);
							if (inc < 0)
								inc += 20;
							tt += inc;
							a[t][p][r] = Math.max(a[t][p][r], tt);
						}
						if (a[t][p][r] > b[t][p][r])
							continue;
					}
					if (t == T) {
						if (better(p, a[t][p][r], ans1, ans2)) {
							ans1 = p;
							ans2 = a[t][p][r];
						}
						continue;
					}
					for (int s = 0; p + s <= numProblems; s++) {
						int rr = (r + s * t1) % 20;
						int add = s * t1;
						a[t1][s + p][rr] = Math.min(a[t1][s + p][rr], a[t][p][r] + add);
						if (s == 0) {
							b[t1][s + p][rr] = Math.max(b[t1][s + p][rr], b[t][p][r] + add);
						} else {
							b[t1][s + p][rr] = inf;
						}
					}
				}
			}
		}
		if (ans1 == 0)
			return "";
		return ans1 + " " + ans2;
	}
	public static void main(String[] args) {
		String s = new ChuckContest().chuckRules(10, new String[]{"10 31"}, new String[]{"10 10"}, new int[]{1});
		System.out.println(s);
	}
}

package topcoder.srm779;

import java.util.*;

public class SubstringQueries {
	public long solve(String s, int k) {
		String ss = s + s;
		int n = s.length();
		boolean[] can = new boolean[n];
		Arrays.fill(can, true);
		long ans = 0;
		int best = 0;
		for (int len = 1; len <= n; len++) {
			best = -1;
			for (int i = 0; i < n; i++) {
				if (!can[i]) continue;
				if (best == -1 || ss.charAt(i + len - 1) < ss.charAt(best + len - 1)) {
					best = i;
				}
  			}
			for (int i = 0; i < n; i++) {
				if (!can[i]) continue;
				if (ss.charAt(i + len - 1) > ss.charAt(best + len - 1)) {
					can[i] = false;
				}
  			}
			ans += best;
		}
		ans += best * (k - 2L) * n;
		if (k == 1) {
			return solveEnd(s, 1);
		}
		ans += solveEnd(ss, n + 1);
		return ans;
	}

	private int solveEnd(String s, int from) {
		int n = s.length();
		int ans = 0;
		ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
		lists.add(new ArrayList<>());
		for (int i = 0; i <= n; i++) {
			lists.get(0).add(i);
		}
		for (int len = 1; len <= n; len++) {
			ArrayList<ArrayList<Integer>> next = new ArrayList<>();
			for (ArrayList<Integer> equal : lists) {
				ArrayList[] types = new ArrayList[26];
				for (int x : equal) {
					if (x + len - 1 >= n) continue;
					int c = s.charAt(x + len - 1) - 'a';
					if (types[c] == null) types[c] = new ArrayList();
					types[c].add(x);
				}
				for (ArrayList list : types) {
					if (list == null) continue;
					next.add(list);
				}
			}
			lists = next;
			if (len >= from) {
				ans += lists.get(0).get(0);
			}
		}
		return ans;
	}
}


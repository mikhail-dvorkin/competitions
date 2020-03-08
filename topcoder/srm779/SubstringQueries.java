package topcoder.srm779;

import java.util.*;

public class SubstringQueries {
	public long solve(String s, int k) {
		return k == 1 ? solve(s, 1, 1) : solve(s, 0, k) + solve(s + s, s.length() + 1, 1);
	}

	private long solve(String s, int from, int k) {
		int n = s.length();
		ArrayList<ArrayList<Integer>> lists = new ArrayList<>(Collections.singletonList(new ArrayList<>()));
		for (int i = 0; i <= n; i++) {
			lists.get(0).add(i);
		}
		long ans = 0;
		Map<Character, ArrayList<Integer>> classes = new TreeMap<>();
		for (int len = 1; len <= n; len++) {
			ArrayList<ArrayList<Integer>> next = new ArrayList<>();
			for (ArrayList<Integer> equivalenceClass : lists) {
				if (equivalenceClass.size() == 1) {
					if (k > 1 || equivalenceClass.get(0) + len - 1 < n) next.add(equivalenceClass);
					continue;
				}
				for (int x : equivalenceClass) {
					if (k == 1 && x + len - 1 >= n) continue;
					classes.computeIfAbsent(s.charAt((x + len - 1) % n), ((c) -> new ArrayList<>())).add(x);
				}
				next.addAll(classes.values());
				classes.clear();
			}
			lists = next;
			long c = 1;
			if (k == 1 && len < from) c = 0;
			if (k > 1 && len == n) c += (k - 2L) * n;
			ans += c * lists.get(0).get(0);
		}
		return ans;
	}
}


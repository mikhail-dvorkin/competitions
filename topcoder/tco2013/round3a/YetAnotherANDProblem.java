package topcoder.tco2013.round3a;
import java.util.*;

public class YetAnotherANDProblem {
	public String test(int[] original, int k, int[] queries) {
		Arrays.sort(original);
		String ans = "";
		for (int q : queries) {
			List<Integer> list = new ArrayList<Integer>();
			for (int x : original) {
				if ((x & q) == q) {
					list.add(x ^ q);
				}
			}
			if (solve(list, k)) {
				ans += "+";
			} else {
				ans += "-";
			}
		}
		return ans;
	}

	boolean solve(List<Integer> list, int k) {
		int n = list.size();
		if (n == 0) {
			return false;
		}
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = list.get(i);
		}
		int zero = Integer.MAX_VALUE;
		for (int m = 1; m < (1 << n); m++) {
			int sz = 0;
			int and = -1;
			for (int i = 0; i < n; i++) {
				if (((m >> i) & 1) == 0) {
					continue;
				}
				sz++;
				and = and & array[i];
			}
			if (and == 0) {
				zero = Math.min(zero, steps(sz));
			}
		}
		if (zero == Integer.MAX_VALUE) {
			return false;
		}
		if (n >= 3) {
			return zero <= k;
		}
		if (n == 2) {
			return (zero <= k) && (k <= 1);
		}
		if (n == 1) {
			return (zero <= k) && (k <= 0);
		}
		throw new RuntimeException();
	}

	int steps(int sz) {
		if (sz == 1) {
			return 0;
		}
		return 1 + steps((sz + 1) / 2);
	}

}

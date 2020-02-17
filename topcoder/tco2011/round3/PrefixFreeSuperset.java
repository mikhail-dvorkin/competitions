package topcoder.tco2011.round3;
import java.math.BigInteger;
import java.util.*;

public class PrefixFreeSuperset {
	String[] ss;
	ArrayList<Integer> holes = new ArrayList<Integer>();

	public long minSumLength(String[] cur, long k) {
		ss = cur;
		k -= cur.length;
		dfs("");
		long sum = 0;
		for (String s : ss) {
			sum += s.length();
		}
		if (k == 0) {
			return sum;
		}
		if (holes.isEmpty()) {
			return -1;
		}
		Collections.sort(holes);
		BigInteger max = BigInteger.TEN.pow(18);
		for (int len = 0;; len++) {
			long n = 0;
			long nn = 0;
			for (int h : holes) {
				if (h <= len) {
					n += 1L << (len - h);
				} else if (h == len + 1) {
					nn++;
				}
			}
			if (2 * n + nn >= k) {
				long m;
				long mm;
				if (n >= k) {
					m = k;
					mm = 0;
				} else if (n + nn >= k) {
					m = n;
					mm = k - m;
				} else {
					m = 2 * n + nn - k;
					mm = k - m;
				}
				BigInteger r = BigInteger.valueOf(sum);
				r = r.add(BigInteger.valueOf(m).multiply(BigInteger.valueOf(len)));
				r = r.add(BigInteger.valueOf(mm).multiply(BigInteger.valueOf(len + 1)));
				if (r.compareTo(max) > 0) {
					return -2;
				}
				return r.longValue();
			}
		}
	}

	private void dfs(String t) {
		for (String s : ss) {
			if (s.equals(t)) {
				return;
			}
			if (s.startsWith(t)) {
				dfs(t + "0");
				dfs(t + "1");
				return;
			}
		}
		holes.add(t.length());
	}

}

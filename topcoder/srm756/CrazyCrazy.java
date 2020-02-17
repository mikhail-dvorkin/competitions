package topcoder;
import java.util.*;

public class CrazyCrazy {
	public String possible(String s) {
		int n = s.length();
		TreeSet<String> gaps1 = gaps(s.substring(0, n / 2), true);
		TreeSet<String> gaps2 = gaps(s.substring(n / 2, n), false);
		gaps1.retainAll(gaps2);
		return gaps1.isEmpty() ? "impossible" : "possible";
	}

	public TreeSet<String> gaps(String s, boolean side) {
		TreeSet<String> result = new TreeSet<>();
		char[] c = s.toCharArray();
		int n = s.length();
		char[] a = new char[n];
		char[] b = new char[n];
		loop:
		for (int i = 0; i < (1 << n); i++) {
			if (Integer.bitCount(i) * 2 < n) {
				continue;
			}
			int aLen = 0;
			int bLen = 0;
			for (int j = 0; j < n; j++) {
				if (((i >> j) & 1) == 1) {
					a[aLen++] = c[j];
				} else {
					b[bLen++] = c[j];
				}
			}
			for (int j = 0; j < bLen; j++) {
				if (side && b[j] != a[j] || !side && b[j] != a[j + aLen - bLen]) {
					continue loop;
				}
			}
			StringBuilder sb = new StringBuilder();
			if (side) {
				for (int j = bLen; j < aLen; j++) {
					sb.append(a[j]);
				}
			} else {
				for (int j = 0; j < aLen - bLen; j++) {
					sb.append(a[j]);
				}
			}
			result.add(sb.toString());
		}
		return result;
	}
}
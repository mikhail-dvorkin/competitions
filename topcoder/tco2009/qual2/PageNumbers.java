package topcoder.tco2009.qual2;
import java.util.*;

public class PageNumbers {
	public int[] getCounts(int n) {
		int[] a = get(n);
		return a;
	}

	public int[] get(int n) {
		int[] a = new int[10];
		if (n < 0)
			return a;
		int m = n - n % 10;
		for (int i = m; i <= n; i++) {
			String s = "" + i;
			if (i == 0)
				s = "";
			for (int j = 0; j < s.length(); j++) {
				a[s.charAt(j) - '0']++;
			}
		}
		m = m / 10 - 1;
		int[] b = get(m);
		for (int i = 0; i < 10; i++) {
			a[i] += 10 * b[i] + m + 1;
		}
		if (m >= 0) a[0]--;
		return a;
	}

	public static void main(String[] args) {
		int[] a = new int[10];
		for (int i = 1;; i++) {
			String s = "" + i;
			for (int j = 0; j < s.length(); j++) {
				a[s.charAt(j) - '0']++;
			}
			int[] b = new PageNumbers().getCounts(i);
			if (!Arrays.equals(a, b))
				throw new RuntimeException("" + i);
			if (i == 134235) {
				System.out.println("OK");
			}
		}
	}
}

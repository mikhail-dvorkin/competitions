package topcoder.srm380;
import java.util.*;

public class NestedDiamonds {
	public double minHeight(int[] sd) {
		int n = sd.length;
		long[] s = new long[n];
		for (int i = 0; i < n; i++) {
			s[i] = sd[i] * 1L * sd[i];
		}
		Arrays.sort(s);
		for (int i = 0; i < n - 1; i++)
			if (s[i] == s[i + 1])
				return -1;
		long maxH = s[n - 1];
		long minH = 0;
		boolean tall = true;
		long d = 0;
		for (int i = n - 2; i >= 0; i--) {
			long e;
			if (tall) {
				e = s[i] - d;
				minH = Math.max(minH, e - d);
				maxH = Math.min(maxH, 2 * e);
			} else {
				e = s[i] - d;
				maxH = Math.min(maxH, (d - e));
				minH = Math.max(minH, -2 * e);
			}
			d = e;
			tall ^= true;
		}
		if (maxH >= minH) {
			return Math.sqrt(minH) * 8;
		}
		return -1;
	}

	public static void main(String[] args) {
		new NestedDiamonds().minHeight(new int[]{4, 5, 68, 69});
	}
}

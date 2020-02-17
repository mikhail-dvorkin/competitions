package topcoder.srm558;
import java.util.*;

public class Stamp {
	public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
		String col = "RGB*";
		int n = desiredColor.length();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = col.indexOf(desiredColor.charAt(i));
		}
		int best = Integer.MAX_VALUE;
		for (int len = 1; len <= n; len++) {
			int[] p = new int[n + 1];
			Arrays.fill(p, Integer.MAX_VALUE);
			p[0] = 0;
			for (int i = 0; i + len <= n; i++) {
				if (p[i] == Integer.MAX_VALUE) {
					continue;
				}
				int[] seen = new int[3];
				for (int j = i; j < i + len; j++) {
					if (a[j] < 3) {
						seen[a[j]] = 1;
					}
				}
				for (int j = i + len; j <= n; j++) {
					int c = seen[0] + seen[1] + seen[2];
					if (c >= 2) {
						break;
					}
					p[j] = Math.min(p[j], p[i] + (j - i + len - 1) / len);
					if (j < n) {
						if (a[j] < 3) {
							seen[a[j]] = 1;
						}
					}
				}
			}
			if (p[n] == Integer.MAX_VALUE) {
				continue;
			}
			int cur = len * stampCost + p[n] * pushCost;
			best = Math.min(best, cur);
		}
		return best;
	}

}

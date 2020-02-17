package topcoder.tco2018.round2b;
import java.util.*;

public class LineColoring {
	public int minCost(int[] x) {
		int n = x.length;
		if (n == 1) {
			return x[0];
		}
		int[] max2 = Arrays.copyOf(x, 2);
		for (int i = 2; i < n; i++) {
			max2[i % 2] = Math.max(max2[i % 2], x[i]);
		}
		int ans = max2[0] + max2[1];

		int v0 = Math.max(max2[0], max2[1]);
		int inf = Integer.MAX_VALUE / 3;
		for (int v1 : x) {
			int a0 = 0, a1 = 0, a2 = 0;
			for (int i = 0; i < n; i++) {
				int b0 = Math.min(a1, a2);
				int b1 = (x[i] <= v1) ? Math.min(a0, a2) : inf;
				int b2 = Math.max(Math.min(a0, a1), x[i]);
				a0 = b0; a1 = b1; a2 = b2;
			}
			ans = Math.min(ans, v0 + v1 + Math.min(a0, Math.min(a1, a2)));
		}
		return ans;
	}
}

package topcoder.srm730;
import java.util.*;

public class StonesOnATree {
	public int minStones(int[] p, int[] w) {
		int n = w.length;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] kids = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			kids[i] = new ArrayList<>();
		}
		for (int i = 1; i < n; i++) {
			kids[p[i - 1]].add(i);
		}
		int[] max = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			if (kids[i].size() == 0) {
				max[i] = w[i];
				continue;
			}
			int j = kids[i].get(0);
			if (kids[i].size() == 1) {
				max[i] = Math.max(max[j], w[i] + w[j]);
				continue;
			}
			int k = kids[i].get(1);
			int wayJ = Math.max(max[j], w[j] + max[k]);
			int wayK = Math.max(max[k], w[k] + max[j]);
			max[i] = Math.max(Math.min(wayJ, wayK), w[i] + w[j] + w[k]);
		}
		return max[0];
	}
}

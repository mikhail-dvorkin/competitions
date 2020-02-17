package topcoder.srm366;
public class ChangingSounds {
	public int maxFinal(int[] changeIntervals, int beginLevel, int maxLevel) {
		int n = changeIntervals.length;
		boolean[][] poss = new boolean[maxLevel + 1][n + 1];
		poss[beginLevel][0] = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < maxLevel; j++) if (poss[j][i]) {
				int k = j - changeIntervals[i];
				if (k >= 0)
					poss[k][i + 1] = true;
				k = j + changeIntervals[i];
				if (k <= maxLevel)
					poss[k][i + 1] = true;
			}
		}
		int ans = maxLevel;
		while (ans >= 0 && !poss[ans][n])
			ans--;
		return ans;
	}
}

package topcoder;
public class FiveHundredEleven {
	public String theWinner(int[] cards) {
		int n = cards.length;
		boolean[][] a = new boolean[512][n + 1];
		for (int m = 511; m >= 0; m--) {
			int f = 0;
			for (int i : cards) {
				if ((i | m) == m) {
					f++;
				}
			}
			for (int k = n; k >= 0; k--) {
				if (m == 511) {
					a[m][k] = true;
					continue;
				}
				if (k == n) {
					continue;
				}
				if (k < f && !a[m][k + 1]) {
					a[m][k] = true;
					continue;
				}
				for (int i : cards) {
					if ((i | m) == m) {
						continue;
					}
					if (!a[i | m][k + 1]) {
						a[m][k] = true;
						continue;
					}
				}
			}
		}
		if (a[0][0]) {
			return "Fox Ciel";
		}
		return "Toastman";
	}
	

}

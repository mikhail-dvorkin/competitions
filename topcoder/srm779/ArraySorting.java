package topcoder.srm779;

public class ArraySorting {
	public int[] arraySort(int[] a) {
		int max = 2001;
		int n = a.length;
		int[][] b = new int[n + 1][max];
		for (int i = 0; i < max; i++) {
			b[n][i] = n + 1;
		}
		b[n][max - 1] = 0;
		for (int i = n - 1; i >= 0; i--) {
			int bestNext = n + 1;
			for (int j = max - 1; j >= 1; j--) {
				bestNext = Math.min(bestNext, b[i + 1][j]);
				b[i][j] = bestNext + (a[i] == j ? 0 : 1);
			}
		}
		int j = 1;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			int best = n + 1;
			for (int k = j; k < max; k++) {
				if (b[i][k] < best) {
					best = b[i][k];
					j = k;
				}
			}
			ans[i] = j;
		}
		return ans;
	}
}

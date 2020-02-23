package topcoder.srm779;

import java.util.Arrays;

public class ArraySorting {
	public int[] arraySort(int[] a) {
		int n = a.length;
		int max = Arrays.stream(a).max().orElseThrow(null);
		int[][] b = new int[n + 1][max + 1];
		Arrays.fill(b[n], n);
		b[n][max] = 0;
		for (int i = n - 1; i >= 0; i--) {
			int bestNext = n;
			for (int j = max; j >= 1; j--) {
				bestNext = Math.min(bestNext, b[i + 1][j]);
				b[i][j] = bestNext + (a[i] == j ? 0 : 1);
			}
		}
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = i == 0 ? 1 : ans[i - 1];
			for (int j = ans[i] + 1; j <= max; j++) {
				if (b[i][j] < b[i][ans[i]]) {
					ans[i] = j;
				}
			}
		}
		return ans;
	}
}

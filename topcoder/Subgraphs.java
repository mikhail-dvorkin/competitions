package topcoder;
import java.util.Arrays;

public class Subgraphs {
	public String[] findGroups(int k) {
		int n = 2 * k;
		int max = k * (k - 1) / 2;
		char[][] a = new char[n + max + 1][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(a[i], '0');
		}
		for (int i = 0; i <= max; i++) {
			Arrays.fill(a[n + i], 'N');
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < i; j++) {
				a[i][j] = a[j][i] = '1';
				a[i][k + j] = a[k + j][i] = '1';
			}
			a[i][k + i] = a[k + i][i] = '1';
		}
		for (int i = 0; i <= max; i++) {
			int j = 0;
			while ((j + 1) * j / 2 <= i) {
				j++;
			}
			for (int t = 0; t < j; t++) {
				a[n + i][t] = 'Y';
			}
			for (int t = j + 1; t < k; t++) {
				a[n + i][k + t] = 'Y';
			}
			if (j < k) {
				a[n + i][k + j - (i - j * (j - 1) / 2)] = 'Y';
			}
		}
		String[] ans = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			ans[i] = new String(a[i]);
		}
		return ans;
	}
}

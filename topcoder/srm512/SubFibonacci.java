package topcoder.srm512;
import java.util.*;

public class SubFibonacci {
	public int maxElements(int[] set) {
		int n = set.length;
		Arrays.sort(set);
		int m = 42;
		long[] a = new long[m];
		long[] b = new long[m];
		a[0] = 1;
		b[1] = 1;
		for (int i = 2; i < m; i++) {
			a[i] = a[i - 1] + a[i - 2];
			b[i] = b[i - 1] + b[i - 2];
		}
		int[] pos = new int[n];
		int[] left = new int[n + 1];
		int[] right = new int[n + 1];
		left[1] = 1;
		right[n - 1] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int x = 0; x < m; x++) {
					for (int y = x + 1; y < m; y++) {
						// a[x] * p + b[x] * q = set[i]
						// a[y] * p + b[y] * q = set[j]
						// a[x]a[y] * p + b[x]a[y] * q = set[i]a[y]
						// a[y]a[x] * p + b[y]a[x] * q = set[j]a[x]
						long q = ((set[i] * a[y] - set[j] * a[x]) / (b[x] * a[y] - b[y] * a[x]));
						long p = ((set[i] * b[y] - set[j] * b[x]) / (a[x] * b[y] - a[y] * b[x]));
						if (p <= 0 || q <= 0) {
							continue;
						}
						if (a[x] * p + b[x] * q != set[i]) {
							continue;
						}
						if (a[y] * p + b[y] * q != set[j]) {
							continue;
						}
						int k = 0;
						int z = 0;
						for (int t = 0; t < m; t++) {
							long v = a[t] * p + b[t] * q;
							if (v > set[n - 1]) {
								break;
							}
							while (z + 1 < n && set[z + 1] <= v) {
								z++;
							}
							if (set[z] == v && (k == 0 || pos[k - 1] != z)) {
								pos[k] = z;
								k++;
							}
						}
						for (int t = 0; t < k; t++) {
//							System.out.print(pos[t] + " ");
							left[pos[t] + 1] = Math.max(left[pos[t] + 1], t + 1);
							right[pos[t]] = Math.max(right[pos[t]], k - t);
						}
//						System.out.println(" // " + p + " " + q);
					}
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			left[i] = Math.max(left[i], left[i - 1]);
		}
		for (int i = n - 1; i >= 0; i--) {
			right[i] = Math.max(right[i], right[i + 1]);
		}
		for (int i = 0; i <= n; i++) {
			ans = Math.max(ans, left[i] + right[i]);
		}
		return ans;
	}

}

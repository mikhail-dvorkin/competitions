package topcoder.srm761;
import java.util.*;

public class SortArray {
	public int[] verify(int n, int[] commands) {
		int[] p = new int[n];
		int[] temp = new int[n];
		for (int mask = 0; mask < (1 << n); mask++) {
			for (int t = 0, j = 0; t < 2; t++) {
				for (int i = 0; i < n; i++) {
					if (((mask >> i) & 1) == t) {
						p[i] = j++;
					}
				}
			}
			
			int[] q = p.clone();
			for (int command : commands) {
				int j = 0;
				for (int i = 0; i < n; i++) {
					if (((command >> i) & 1) == 1) {
						temp[j++] = q[i];
					}
				}
				Arrays.sort(temp, 0, j);
				j = 0;
				for (int i = 0; i < n; i++) {
					if (((command >> i) & 1) == 1) {
						q[i] = temp[j++];
					}
				}
			}
			for (int i = 0; i < n; i++) {
				if (q[i] != i) {
					return p;
				}
			}
		}
		return new int[0];
	}
}

package topcoder.humblefool2020;

import java.util.*;

public class NonSimilarTriangles {
	public int count(int[] a) {
		Arrays.sort(a);
		int n = a.length;
		HashSet<String> ans = new HashSet<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				for (int k = 0; k < j; k++) {
					int[] b = new int[] {a[i], a[j], a[k]};
					if (b[0] >= b[1] + b[2]) continue;
					int g = gcd(b[0], gcd(b[1], b[2]));
					b[0] /= g; b[1] /= g; b[2] /= g;
					ans.add(Arrays.toString(b));
				}
			}
		}
		return ans.size();
	}

	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
}

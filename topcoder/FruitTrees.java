package topcoder;
import java.util.*;

public class FruitTrees {
	public int maxDist(int apple, int kiwi, int grape) {
		int[] a = new int[]{apple, kiwi, grape};
		Arrays.sort(a);
		int ans = 0;
		for (int i = 0; i < a[2]; i++) {
			for (int j = 0; j < a[2]; j++) {
				int dist = a[0];
				dist = Math.min(dist, shift(a[0], a[2], i));
				dist = Math.min(dist, shift(a[0], a[1], j));
				dist = Math.min(dist, shift(a[1], a[2], Math.abs(i - j)));
				ans = Math.max(ans, dist);
			}
		}
		return ans;
	}

	int shift(int p, int q, int s) {
		int g = gcd(p, q);
		s %= g;
		return Math.min(s, g - s);
	}
	
	int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}

}

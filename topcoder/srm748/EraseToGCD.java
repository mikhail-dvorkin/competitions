package topcoder;
import java.util.*;

public class EraseToGCD {
	private static final int M = 1000_000_007;

	public int countWays(int[] s, int goal) {
		int[] a = new int[Arrays.stream(s).reduce(goal, Integer::max) + 1];
		a[0] = 1;
		for (int i = 0; i < s.length; i++) {
			int[] b = a.clone();
			for (int j = 0; j < a.length; j++) {
				int gcd = gcd(j, s[i]);
				b[gcd] = (b[gcd] + a[j]) % M;
			}
			a = b;
		}
		return a[goal];
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

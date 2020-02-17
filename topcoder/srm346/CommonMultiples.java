package topcoder.srm346;
import java.math.BigInteger;

public class CommonMultiples {
	long infty = 2000000001;

	public int countCommMult(int[] a, int lower, int upper) {
		long l = a[0];
		for (int i = 1; i < a.length; i++) {
			l = lcm(l, a[i]);
		}
		if (l > upper)
			return 0;
		lower--;
		int g = (int) l;
		return upper / g - lower / g;
	}

	private long lcm(long a, long b) {
		long g = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
		g = (a / g) * b;
		if (g > infty)
			g = infty;
		return g;
	}
}

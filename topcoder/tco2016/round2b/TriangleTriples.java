package topcoder;
import java.math.BigInteger;

public class TriangleTriples {
	static final int M = 1000000007;
	static final int INV6 = BigInteger.valueOf(6).modInverse(BigInteger.valueOf(M)).intValue();
	
	public int count(int a, int b, int c) {
		long ans = 1L * a * b % M * c % M;
		ans -= bad(a, b, c);
		ans -= bad(b, c, a);
		ans -= bad(c, a, b);
		return (int) ((ans % M + M) % M);
	}

	int bad(int a, int b, int c) {
		if (b > c) {
			int t = b; b = c; c = t;
		}
		int k1 = Math.min(b, a);
		int k2 = Math.min(c, a);
		int k3 = Math.min(b + c, a);
		long res = 0;
		res += 1L * s1(k1) * a % M;
		res -= s2(k1);
		res += 1L * (k2 - k1) * b % M * a % M;
		res -= 1L * (s1(k2) - s1(k1)) * b % M;
		res += s2(k3) - s2(k2);
		res -= 1L * (s1(k3) - s1(k2)) * (((a + b) % M + c) % M) % M;
		res += 1L * (k3 - k2) * a % M * ((b + c) % M) % M;
		return (int) ((res % M + M) % M);
	}
	
	int s1(int n) {
		return (int) (1L * n * (n + 1) / 2 % M);
	}
	
	int s2(int n) {
		return (int) (1L * n * (n + 1) % M * (2 * n + 1) % M * INV6 % M);
	}
}

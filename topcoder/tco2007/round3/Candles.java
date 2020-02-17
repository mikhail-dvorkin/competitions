package topcoder;
import java.math.BigInteger;

public class Candles {
	int gcd(int a, int b) {
		return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}
	
	int lcm(int a, int b) {
		return a / gcd(a, b) * b;
	}
	
	public int ceremonies(int n, int n1, int r1, int n2, int r2) {
		int r = lcm(r1, r2);
		int s = (r / r1 * n1 + r / r2 * n2);
		int g = gcd(s, n);
		s /= g;
		int a1 = Math.min(n1, n) * r / r1; 
		int a2 = Math.max(n1, n) * r / r1; 
		int b1 = Math.min(n2, n) * r / r2; 
		int b2 = Math.max(n2, n) * r / r2;
		if (Math.min(a1, a2) > Math.max(b1, b2))
			return -1;
		if (Math.min(b1, b2) > Math.max(a1, a2))
			return -1;
		return s;
	}
	
	public static void main(String[] args) {
//		new Candles().ceremonies(19, 10, 1, 10, 10);
//		new Candles().ceremonies(3, 12, 4, 6, 2);
		new Candles().ceremonies(56, 50, 20, 60, 16);
	}
}

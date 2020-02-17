package topcoder;
import java.util.*;

public class BuildCircuit {
	int max = 50000;
	
	class Rational {
		int num, den, hc;
		
		public Rational(int num, int den) {
			this.num = num;
			this.den = den;
			hc = (num << 16) ^ den;
		}
		
		@Override
		public int hashCode() {
			return hc;
		}
		
		@Override
		public boolean equals(Object obj) {
			Rational r = (Rational) obj;
			return num == r.num && den == r.den;
		}
		
		@Override
		public String toString() {
			return num + "/" + den;
		}
	}
	
	int gcd(int a, int b) {
		while (b > 0) {
			int t = a % b;
			a = b;
			b = t;
		}
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public int minimalCount(int a, int b) {
		int g = gcd(a, b);
		a /= g;
		b /= g;
		Rational find = new Rational(a, b);
		HashSet<Rational>[] hs = new HashSet[17];
		HashSet<Rational> all = new HashSet<Rational>();
		for (int i = 1; i <= 16; i++) {
			hs[i] = new HashSet<Rational>();
		}
		hs[1].add(new Rational(1, 1));
		hs[1].add(new Rational(2, 1));
		all.addAll(hs[1]);
		if (hs[1].contains(find))
			return 1;
		for (int sum = 2; sum <= 16; sum++) {
			for (int x = 1; x + x <= sum; x++) {
				int y = sum - x;
				for (Rational p : hs[x]) {
					for (Rational q : hs[y]) {
						Rational r = sum(p.num, p.den, q.num, q.den);
						if (!all.contains(r))
							hs[sum].add(r);
						r = sum1(p.den, p.num, q.den, q.num);
						if (!all.contains(r))
							hs[sum].add(r);
					}
				}
			}
			if (hs[sum].contains(find))
				return sum;
//			if (sum == 11)
			System.out.println(sum + " " + hs[sum].size());
		}
		return -1;
	}

	private Rational sum(int n1, int d1, int n2, int d2) {
		int den = d1 * d2;
		int num = n1 * d2 + n2 * d1;
		int g = gcd(num, den);
		return new Rational(num / g, den / g);
	}
	
	private Rational sum1(int n1, int d1, int n2, int d2) {
		int den = d1 * d2;
		int num = n1 * d2 + n2 * d1;
		int g = gcd(num, den);
		return new Rational(den / g, num / g);
	}
	
	public static void main(String[] args) {
		System.out.println(new BuildCircuit().minimalCount(/*756, 874*/852, 1749));
	}
}
// 842/1749
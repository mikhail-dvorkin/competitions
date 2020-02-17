package topcoder;
import java.math.BigInteger;

public class Neaten {
	public int shortest(int k, String s) {
		s = norm(s);
		if (s.equals("0."))
			return 1;
		String gr;
		if (s.startsWith("0."))
			gr = "1.";
		else
			gr = s;
		for (int i = 0; i < k; i++)
			gr = div10(gr);
		ss = big(s);
		err = big(gr);
		lo = ss.subtract(err);
		if (lo.signum() < 0)
			lo = BigInteger.valueOf(-1);
		hi = ss.add(err);
		String str = ss.toString();
		BigInteger tmp = ss;
		for (int i = 0; i < str.length() + 5; i++) {
			ttry(tmp, i);
			ttry(tmp.add(BigInteger.ONE), i);
			ttry(tmp.subtract(BigInteger.ONE), i);
			tmp = tmp.divide(BigInteger.TEN);
		}
		tmp = BigInteger.ONE;
		for (int i = 0; i < 105; i++) {
			ttry(tmp, n);
			ttry(tmp.subtract(BigInteger.ONE), n);
			tmp = tmp.multiply(BigInteger.TEN);
		}
		return ans;
	}
	
	private void ttry(BigInteger t, int p) {
		if (t.signum() < 0)
			return;
		t = t.multiply(BigInteger.TEN.pow(p));
		if (check(t)) {
			String s = shorten(small(t));
			System.out.println(s);
			ans = Math.min(ans, s.length());
		}
	}

	int ans = 1000;
	BigInteger ss, err, lo, hi;
	
	boolean check(BigInteger t) {
		return t.compareTo(lo) > 0 && t.compareTo(hi) < 0;
	}
	
//	boolean check(String t) {
//		return check(big(t));
//	}
	
	private String div10(String s) {
		int i = s.indexOf('.');
		s = s.substring(0, i - 1) + "." + s.substring(i - 1, i) + s.substring(i + 1);
		return norm(s);
	}
	
	int n = 100;
	
	private BigInteger big(String s) {
		s = norm(s);
		int x = s.length() - s.indexOf('.');
		for (int i = 0; i <= n - x; i++)
			s += "0";
		x = s.indexOf('.');
		s = s.substring(0, x) + s.substring(x + 1);
		while (s.length() > 1 && s.startsWith("0"))
			s = s.substring(1);
		return new BigInteger(s);
	}
	
	private String small(BigInteger b) {
		String s = b.toString();
		while (s.length() < n + 5)
			s = "0" + s;
		int x = s.length() - n;
		s = s.substring(0, x) + "." + s.substring(x);
		return norm(s);
	}

	private String norm(String s) {
		if (!s.contains("."))
			s += ".";
		if (s.startsWith("."))
			s = "0" + s;
		while (s.endsWith("0"))
			s = s.substring(0, s.length() - 1);
		while (s.startsWith("0") && (s.charAt(1) != '.'))
			s = s.substring(1);
		return s;
	}

	private String shorten(String s) {
		s = norm(s);
		if (s.startsWith("0."))
			s = s.substring(1);
		if (s.endsWith("."))
			s = s.substring(0, s.length() - 1);
		if (s.equals(""))
			s = "0";
		return s;
	}

	public static void main(String[] args) {
//		System.out.println(new Neaten().shortest(2, ".20050"));
		System.out.println(new Neaten().shortest(1, "104299.64654124292847088820"));
	}
}

//shushpunchikshushpunchikshushpunchikshushpunchik
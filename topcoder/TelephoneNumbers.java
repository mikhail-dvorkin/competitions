package topcoder;
import java.util.*;

public class TelephoneNumbers {
	int dist(int a, int b) {
		int d = 0;
		for (int i = 0; i < 7; i++) {
			if ((a & 15) != (b & 15))
				d++;
			a >>= 4;
			b >>= 4;
		}
		return d;
	}
	
	char ch(int d) {
		return "0123456789ABCDEF".charAt(d);
	}
	
	String hex(int v) {
		String s = "";
		for (int i = 0; i < 7; i++) {
			int d = v & 15;
			v >>= 4;
			s = ch(d) + s;
		}
		return s;
	}
	
	public String kthNumber(int sep, int k) {
		k--;
		if (sep == 1)
			return hex(k);
		if (sep == 2)
			return hex(k).substring(1) + ch(xor(k));
		k++;
		ArrayList<Integer> in = new ArrayList<Integer>();
		vloop:
		for (int v = 0; in.size() < k; v++) {
			for (int x : in) {
				if (dist(x, v) < sep)
					continue vloop;
			}
			in.add(v);
//			System.out.println(hex(v));
		}
		return hex(in.get(k - 1));
	}
	
	private int xor(int i) {
		int r = 0;
		while (i > 0) {
			r ^= (i & 15);
			i >>= 4;
		}
		return r;
	}

	public static void main(String[] args) {
		String s = new TelephoneNumbers().kthNumber(3, 3000);
		System.out.println(s);
	}
}

package topcoder.srm365;
import java.util.*;

public class PointsOnCircle {
	public long count(int rr) {
		long r = rr;
		while (r % 2 == 0)
			r /= 2;
		int n = (int) r;
		if (n == 1)
			return 4;
		int i = 2;
		TreeMap<Integer, Integer> divs = new TreeMap<Integer, Integer>();
		while (n > 1) {
			if (n % i == 0) {
				int d = 0;
				while (n % i == 0) {
					n /= i;
					d++;
				}
				divs.put(i, d);
				i++;
				continue;
			}
			if (i * i > n) {
				divs.put(n, 1);
				break;
			}
			i++;
		}
		long a1 = 1;
		long a3 = 0;
		for (int p : divs.keySet()) {
			int deg = 2 * divs.get(p);
			long b1, b3;
			if (p % 4 == 1) {
				b1 = a1 * (deg + 1);
				b3 = a3 * (deg + 1);
			} else {
				b1 = a1 * (deg / 2 + 1) + a3 * (deg / 2);
				b3 = a3 * (deg / 2 + 1) + a1 * (deg / 2);
			}
			a1 = b1;
			a3 = b3;
		}
		System.out.println(a1 + a3);
		System.out.println(r);
		return 4 * (a1 - a3);
	}

	public static void main(String[] args) {
		System.out.println(new PointsOnCircle().count(3 * 5 * 7 * 11 * 13 * 5 * 7 * 17 * 3 * 5 * 3));
	}
}

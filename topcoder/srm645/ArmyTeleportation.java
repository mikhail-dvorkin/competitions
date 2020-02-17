package topcoder.srm645;
public class ArmyTeleportation {
	int n;

	public String ifPossible(int[] x1, int[] y1, int[] x2, int[] y2, int[] xt, int[] yt) {
		n = x1.length;
		boolean p1 = possibleEven(x1, y1, x2, y2, xt, yt);
		for (int i = 0; i < n; i++) {
			x1[i] = 2 * xt[0] - x1[i];
			y1[i] = 2 * yt[0] - y1[i];
		}
		boolean p2 = possibleEven(x1, y1, x2, y2, xt, yt);
		return (p1 || p2) ? "possible" : "impossible";
	}

	boolean possibleEven(int[] x1, int[] y1, int[] x2, int[] y2, int[] xt, int[] yt) {
		int minx1 = min(x1);
		int miny1 = min(y1);
		int minx2 = min(x2);
		int miny2 = min(y2);
		for (int i = 0; i < n; i++) {
			boolean found = false;
			for (int j = 0; j < n; j++) {
				if (x1[i] - minx1 == x2[j] - minx2 && y1[i] - miny1 == y2[j] - miny2) {
					found = true;
				}
			}
			if (!found) {
				return false;
			}
		}
		long x = minx2 - minx1;
		long y = miny2 - miny1;
		if (x % 2 != 0 || y % 2 != 0) {
			return false;
		}
		x /= 2;
		y /= 2;
		long xa = xt[1] - xt[0];
		long ya = yt[1] - yt[0];
		long xb = xt[2] - xt[0];
		long yb = yt[2] - yt[0];
		if (xa * yb != xb * ya) {
			long a = (x * yb - y * xb);
			long b = (x * ya - y * xa);
			long c = (xa * yb - ya * xb);
			return (a % c == 0) && (b % c == 0);
		}
		if (x * yb != xb * y) {
			return false;
		}
		if (xa == 0) {
			long t = xa; xa = ya; ya = t;
			t = xb; xb = yb; yb = t;
			t = x; x = y; y = t;
		}
		long g = gcd(Math.abs(xa), Math.abs(xb));
		return x % g == 0;
	}

	public static long gcd(long a, long b) {
		while (a > 0) {
			long t = b % a;
			b = a;
			a = t;
		}
		return b;
	}

	int min(int[] x) {
		int res = Integer.MAX_VALUE;
		for (int i : x) {
			res = Math.min(res, i);
		}
		return res;
	}
}

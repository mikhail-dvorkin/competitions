package topcoder;
public class DigitalCounter {
	static final int[] lit = new int[]{6, 2, 5, 5, 4, 5, 6, 3, 7, 5};
	
	public long nextEvent(String current) {
		int len = current.length();
		int[] a = new int[len];
		int li = 0;
		long ten = 1;
		for (int i = 0; i < len; i++) {
			a[i] = current.charAt(i) - '0';
			li += lit[a[i]];
			ten *= 10;
		}
		long value = Long.parseLong(current);
		long c1 = next(li,len, ten, value + 1);
		if (c1 >= 0) {
			return c1 - value;
		}
		c1 = next(li, len, ten, 0);
		return c1 - value + ten;
	}

	private long next(int tolit, int len, long ten, long val) {
		if (val == ten)
			return -1;
		int[] a = new int[len];
		long vv = val;
		int curlit = 0;
		for (int i = 0; i < len; i++) {
			a[len - 1 - i] = (int) (vv % 10);
			curlit += lit[a[len - 1 - i]];
			vv /= 10;
		}
		if (curlit == tolit)
			return val;
		for (int x = len - 1; x >= 0; x--) {
			for (int d = a[x] + 1; d < 10; d++) {
				int cur = 0;
				for (int i = 0; i < x; i++) {
					cur += lit[a[i]];
				}
				cur += lit[d];
				int m1 = cur + 2 * (len - 1 - x);
				int m2 = cur + 7 * (len - 1 - x);
				if (m1 <= tolit && tolit <= m2) {
					a[x] = d;
					yloop:
					for (int y = x + 1; y < len; y++) {
						for (int v = 0; v < 10; v++) {
							int c1 = cur + lit[v];
							int n1 = c1 + 2 * (len - 1 - y);
							int n2 = c1 + 7 * (len - 1 - y);
							if (n1 <= tolit && tolit <= n2) {
								a[y] = v;
								cur += lit[v];
								continue yloop;
							}
						}
						throw new RuntimeException();
					}
					long ans = 0;
					for (int i = 0; i < len; i++) {
						ans = ans * 10 + a[i];
					}
					return ans;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println(new DigitalCounter().nextEvent("654371"));
		long ten = 1;
		for (int len = 1;; len++) {
			ten *= 10;
			for (long v = 0; v < ten; v++) {
				String s = "" + v;
				while (s.length() < len) {
					s = "0" + s;
				}
				long a1 = new DigitalCounter().nextEvent(s);
				long vv = v;
				int li, litv = 0;
				for (int i = 0; i < len; i++) {
					litv += lit[(int) (vv % 10)];
					vv /= 10;
				}
				long a2 = v;
				do {
					a2++;
					if (a2 == ten)
						a2 = 0;
					vv = a2;
					li = 0;
					for (int i = 0; i < len; i++) {
						li += lit[(int) (vv % 10)];
						vv /= 10;
					}
				} while (li != litv);
				a2 = (a2 + 2 * ten - v - 1) % ten + 1;
				System.out.println(s + " " + a1 + " " + a2);
				if (a1 != a2)
					throw new RuntimeException();
			}
		}
	}
}

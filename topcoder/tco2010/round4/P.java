package topcoder.tco2010.round4;
public class P {
	public long get(int[] p) {
		int n = p.length;
		if (n == 1) {
			if (p[0] == 0) {
				return 10;
			}
			return minWith(p[0]);
		}
		boolean zeros = false;
		boolean nz = false;
		int zz = -1;
		for (int i = 0; i < n; i++) {
			if (p[i] == 0) {
				if (i + 1 < n && p[i + 1] == 0) {
					zeros = true;
					if (zz == -1) {
						zz = i;
					}
				}
			} else {
				nz = true;
			}
		}
		if (zeros) {
			if (!nz) {
				if (n <= 11) {
					return 100;
				} else {
					return 1000;
				}
			}
			if (zz == 0) {
				while (p[zz] == 0) {
					zz++;
				}
				long x = minWith(p[zz]);
				if (zz > 11) {
					return (x * 1000 + 111) - zz;
				}
				return (x * 100 + 11) - zz;
			} else {
				long x = minWith(p[zz - 1] / 9 / 9);
				return (x * 100 + 99) - (zz - 1);
			}
		}
		aloop:
		for (int a = 9; a <= 99; a++) {
			int x = -1;
			for (int i = 0; i < n; i++) {
				int b = a + i;
				if (b > 100) {
					continue aloop;
				}
				int c = (b % 10) * (b / 10);
				if (c == 0) {
					if (p[i] != 0) {
						continue aloop;
					}
					continue;
				}
				if (p[i] % c != 0) {
					continue aloop;
				}
				int y = p[i] / c;
				if (x != -1 && y != x) {
					continue aloop;
				}
				x = y;
			}
			long t = minWith(x);
			return 100 * t + a;
		}
		throw new RuntimeException("2");
	}

	public long minWith(int p) {
		int len = len(p);
		long ans = 0;
		for (int i = 0; i < len; i++) {
			for (int d = 1; d <= 9; d++) {
				if (p % d != 0) {
					continue;
				}
				if (len(p / d) == len - i - 1) {
					ans = ans * 10 + d;
					p /= d;
					break;
				}
			}
		}
		return ans;
	}

	public int len(int p) {
		if (p == 1) {
			return 0;
		}
		if (p < 10) {
			return 1;
		}
		if (p % 7 == 0) {
			return 1 + len(p / 7);
		}
		if (p % 5 == 0) {
			return 1 + len(p / 5);
		}
		if (p % 8 == 0) {
			return 1 + len(p / 8);
		}
		if (p % 4 == 0) {
			return 1 + len(p / 4);
		}
		if (p % 6 == 0) {
			return 1 + len(p / 6);
		}
		if (p % 9 == 0) {
			return 1 + len(p / 9);
		}
		if (p % 2 == 0) {
			return 1 + len(p / 3);
		}
		if (p % 2 == 0) {
			return 1 + len(p / 3);
		}
		throw new RuntimeException("1");
	}

	public static void main(String[] args) {
		System.out.println(new P().get(new int[]{1,2}));
	}
}

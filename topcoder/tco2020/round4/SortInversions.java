package topcoder.tco2020.round4;
public class SortInversions {
	int m;
	int[] s;

	public long count(int n) {
		m = (n + "").length();
		s = new int[m];
		for (int i = 0; i < m; i++) {
			s[i] = (n + "").charAt(i) - '0';
		}
		long ans = 0;
		for (int a = 2; a <= m; a++) {
			for (int b = 1; b < a; b++) {
				for (int d = 0; d < b; d++) {
					ans += count(a, b, d, n);
				}
			}
		}
		return ans;
	}

	long count(int a, int b, int d, int n) {
		if (a == m) return countAM(b, d, n);
		long res = 1;
		for (int i = 0; i < d; i++) {
			if (i == 0) res *= 9; else res *= 10;
		}
		if (d > 0) {
			res *= 10 * 9 / 2;
		} else {
			res *= 9 * 8 / 2;
		}
		for (int i = d + 1; i < a; i++) {
			if (i < b) res *= 100; else res *= 10;
		}
		return res;
	}

	long countAM(int b, int d, int n) {
		long res = 0;
		for (int e = 0; e <= m; e++) {
			res += countAM(b, d, e, n);
		}
		return res;
	}

	long countAM(int b, int d, int e, int n) {
		long res = 1;
		for (int i = 0; i < m; i++) {
			int count = 0;
			for (int x = 0; x < 10; x++) {
				if (i == 0 && x == 0) continue;
				if (i < e && x != s[i]) continue;
				if (i == e && x >= s[i]) continue;
				for (int y = 0; y < 10; y++) {
					if (i == 0 && y == 0) continue;
					if (i >= b && y > 0) break;
					if (i < d && y != x) continue;
					if (i == d && y <= x) continue;
					count++;
				}
			}
			res *= count;
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(new SortInversions().count(1));
	}
}

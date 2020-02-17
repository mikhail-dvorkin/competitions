package topcoder.srm545;
public class StrIIRec {
	public String recovstr(int n, int minInv, String minStr) {
		while (minStr.length() < n) {
			minStr += 'a';
		}
		char[] c = minStr.toCharArray();
		char[] a = new char[n];
		boolean[] were = new boolean[n];
		boolean eq = true;
		for (int i = 0; i < n; i++) {
			for (int v = 0; v < n; v++) {
				if (were[v]) {
					continue;
				}
				a[i] = (char) ('a' + v);
				if (eq && a[i] < c[i]) {
					continue;
				}
				int t = (n - 1);
				were[v] = true;
				for (int j = i + 1; j < n; j++) {
					while (were[t]) {
						t--;
					}
					a[j] = (char) ('a' + t);
					t--;
				}
				were[v] = false;
				int invs = countInv(a);
				if (invs < minInv) {
					continue;
				}

				were[v] = true;
				if (a[i] > c[i]) {
					eq = false;
				}
				break;
			}
		}
		return new String(a);
	}

	private int countInv(char[] a) {
		int res = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < i; j++) {
				if (a[j] > a[i]) {
					res++;
				}
			}
		}
		return res;
	}

}

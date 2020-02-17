package topcoder.tco2011.round2;
public class STable {
	class MyString {
		long length;
		String s;
		MyString a, b;

		public MyString(String s) {
			this.length = s.length();
			this.s = s;
		}

		public MyString(MyString a, MyString b) {
			this.length = a.length + b.length;
			this.a = a;
			this.b = b;
			this.s = a.s;
		}

		public String substr(long pos, long len) {
			len = Math.min(len, length - pos);
			if (a == null) {
				return s.substring((int) pos, (int) (pos + len));
			}
			if (pos >= a.length) {
				return b.substr(pos - a.length, len);
			}
			String res = a.substr(pos, len);
			if (pos + len > a.length) {
				res += b.substr(0, len - res.length());
			}
			return res;
		}

		@Override
		public String toString() {
			return substr(0, 50);
		}
	}

	public String getString(String s, String t, long pos) {
		int x = Integer.MAX_VALUE / 2;
		int n = s.length();
		int m = t.length();
		MyString[][] a = new MyString[n + 1][m + 1];
		int[][] v = new int[n + 2][m + 2];
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				v[i][j] = x;
			}
		}
		v[0][0] = 0;
		for (int i = 0; i < n; i++) {
			a[i + 1][0] = new MyString("" + s.charAt(i));
			v[i + 1][0] = s.charAt(i);
		}
		for (int j = 0; j < m; j++) {
			a[0][j + 1] = new MyString("" + t.charAt(j));
			v[0][j + 1] = t.charAt(j);
		}
		for (int it = 0; it < n * m; it++) {
			int bi = -1;
			int bj = -1;
			long bv = Long.MAX_VALUE;
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++) {
					if (v[i][j] != x) {
						continue;
					}
					int va = v[i - 1][j];
					int vb = v[i][j - 1];
					if (va == x || vb == x) {
						continue;
					}
					if (va < vb) {
						int tt = va; va = vb; vb = tt;
					}
					long vv = va << 32L + vb;
					if (vv < bv) {
						bv = vv;
						bi = i;
						bj = j;
					}
				}
			}

			int i = bi;
			int j = bj;
			MyString aa;
			MyString bb;
			int vv;
			if (v[i - 1][j] < v[i][j - 1]) {
				aa = a[i - 1][j];
				bb = a[i][j - 1];
				vv = v[i - 1][j];
				if (i >= 1 && v[i - 1][j + 1] == v[i - 1][j] + 1) {
					vv = v[i - 1][j + 1];
				}
			} else {
				bb = a[i - 1][j];
				aa = a[i][j - 1];
				vv = v[i][j - 1];
				if (j >= 1 && v[i + 1][j - 1] == v[i][j - 1] + 1) {
					vv = v[i + 1][j - 1];
				}
			}
			a[i][j] = new MyString(aa, bb);
			for (int ii = 0; ii <= n; ii++) {
				for (int jj = 0; jj <= m; jj++) {
					if (v[ii][jj] == x) {
						break;
					}
					if (v[ii][jj] > vv) {
						v[ii][jj]++;
					}
				}
			}
			v[i][j] = vv + 1;
		}
		return a[n][m].substr(pos, 50);
	}

}

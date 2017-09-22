package topcoder;
public class TheLargestString {
	public String find(String s, String t) {
		int n = s.length();
		boolean[] c = new boolean[n];
		char max = 'a';
		int m = 0;
		for (int i = n - 1; i >= 0; i--) {
			max = (char) Math.max(s.charAt(i), max);
			if (s.charAt(i) == max) {
				c[i] = true;
				m++;
			}
		}
		String ss = "";
		String tt = "";
		for (int i = 0; i < n; i++) {
			if (c[i]) {
				ss += s.charAt(i);
				tt += t.charAt(i);
			}
		}
		String ans = "";
		for (int j = 1; j <= m; j++) {
			int k = j - 1;
			while (k > 0 && ss.charAt(k - 1) == ss.charAt(k)) {
				k--;
			}
			int l = j - 1;
			while (l < m - 1 && ss.charAt(l) == ss.charAt(l + 1)) {
				l++;
			}
			String cur = ss.substring(0, j) + tt.substring(0, k) + lexMax(tt.substring(k, l + 1), j - k);
			if (cur.compareTo(ans) > 0) {
				ans = cur;
			}
		}
		return ans;
	}

	String lexMax(String s, int m) {
		int n = s.length();
		String[][] a = new String[n + 1][m + 1];
		a[0][0] = "";
		for (int i = 0; i < n; i++) {
			a[i + 1][0] = "";
			for (int j = 1; j <= m && j <= i + 1; j++) {
				a[i + 1][j] = a[i][j - 1] + s.charAt(i);
				if (j <= i && a[i][j].compareTo(a[i + 1][j]) > 0) {
					a[i + 1][j] = a[i][j];
				}
			}
		}
		return a[n][m];
	}

}

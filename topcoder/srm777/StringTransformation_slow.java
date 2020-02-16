package topcoder.srm777;

public class StringTransformation_slow {
	public boolean solve(String s, String t) {
		int n = s.length();
		if (s.equals(t)) return true;
		if (t.length() >= n || (s.length() + t.length()) % 2 != 0 || s.charAt(0) != t.charAt(0) || t.length() == 1) return false;
		int[] a = new int[n];
		int[] count = new int[3];
		for (int i = 0; i < n; i++) {
			a[i] = "RGB".indexOf(s.charAt(i));
			count[a[i]]++;
		}
		if (count[0] == n || count[1] == n || count[2] == n) return false;
		boolean[][] kill = new boolean[n][n];
		for (int len = 0; len <= n - 2; len += 2) {
			for (int i = 0; i + 1 + len < n; i++) {
				int j = i + 1 + len;
				if (len == 0) {
					kill[i][j] = true;
					continue;
				}
				if (a[i] == a[j]) continue;
				for (int k = i + 1; k + 1 < j; k++) {
					if (a[k] == a[k + 1] && kill[i][k] && kill[k + 1][j]) {
						kill[i][j] = true;
						break;
					}
				}
			}
		}
		boolean[][] read = new boolean[n][t.length()];
		read[0][0] = true;
		for (int j = 1; j < t.length(); j++) {
			for (int i = 1; i < n; i++) {
				if (s.charAt(i) != t.charAt(j)) continue;
				for (int k = 0; k < i; k++) {
					if (read[k][j - 1] && kill[k][i]) {
						read[i][j] = true;
						break;
					}
				}
			}
		}
		return read[n - 1][t.length() - 1];
	}

	public String getResult(String s, String t) {
		return solve(s, t) ? "YES" : "NO";
	}
}

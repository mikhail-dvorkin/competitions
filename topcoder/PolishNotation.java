package topcoder;
public class PolishNotation {
	public long waysToDecode(String s) {
		int n = s.length();
		long[][] a = new long[n + 1][n + 1];
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
				a[i][i + 1] = 1;
		}
		for (int l = 2; l <= n; l++) {
			for (int i = 0; i + l <= n; i++) {
				int j = i + l;
				if (s.charAt(i) == '+' || s.charAt(i) == '*' || s.charAt(i) == '/') {
					for (int k = i; k <= j; k++) {
						a[i][j] += a[i + 1][k] * a[k][j]; 
					}
					continue;
				}
				if (s.charAt(i) == '-') {
					for (int k = i; k <= j; k++) {
						a[i][j] += a[i + 1][k] * a[k][j]; 
					}
					if (s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9')
						a[i][j] += a[i + 1][j];
					continue;
				}
				if (s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9')
					a[i][j] = a[i + 1][j];
			}
		}
		return a[0][n];
	}
}

package topcoder;
public class ArtShift {
	public int maxShifts(String s) {
		int n = s.length();
		int a = 0;
		int b = 0;
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) == 'B') {
				a++;
			} else {
				b++;
			}
		}
		int[][] p = new int[a + 1][b + 1];
		p[0][0] = 1;
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= b; j++) {
				for (int ii = 0; i + ii <= a; ii++) {
					for (int jj = 0; j + jj <= b; jj++) {
						int pp = 1;
						if (ii == 0 || jj == 0) {
							
						} else {
							pp = ii + jj;
						}
						p[i + ii][j + jj] = Math.max(p[i + ii][j + jj], lcm(p[i][j], pp));
					}
				}
			}
		}
		return p[a][b];
	}

	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
	
	public static int lcm(int a, int b) {
		return (a / gcd(a, b)) * b;
	}
	
}

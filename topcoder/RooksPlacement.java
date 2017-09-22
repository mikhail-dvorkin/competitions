package topcoder;
public class RooksPlacement {
	final long mod = 1000001;
	
	public int countPlacements(int A, int B, int num) {
		long[][][] r = new long[A + 1][B + 1][num + 1];
		for (int a = 0; a <= A; a++) {
			for (int b = 0; b <= B; b++) {
				r[a][b][0] = 1;
				for (int k = 1; k <= num && k <= a * b; k++) {
					if (a >= 1)
						r[a][b][k] += r[a - 1][b][k];
					if (a >= 1 && b >= 1 && k >= 1)
						r[a][b][k] += b * r[a - 1][b - 1][k - 1];
					if (a >= 2 && b >= 1 && k >= 2)
						r[a][b][k] += b * (a - 1) * r[a - 2][b - 1][k - 2];
					if (a >= 1 && b >= 2 && k >= 2)
						r[a][b][k] += (b * (b - 1) / 2) * r[a - 1][b - 2][k - 2];
					r[a][b][k] %= mod;
				}
			}
		}
		return (int) r[A][B][num];
	}
}

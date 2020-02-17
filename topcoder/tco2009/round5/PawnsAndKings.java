package topcoder.tco2009.round5;
import java.util.*;

public class PawnsAndKings {
	int d(int x, int y) {
		return Math.max(Math.abs(x), Math.abs(y));
	}

	public int minNumberOfMoves(String[] board) {
		int[] kx = new int[64];
		int[] ky = new int[64];
		int[] px = new int[64];
		int[] py = new int[64];
		int ks = 0;
		int ps = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				char c = board[i].charAt(j);
				if (c == 'K') {
					kx[ks] = i;
					ky[ks] = j;
					ks++;
				}
				if (c == 'P') {
					px[ps] = i;
					py[ps] = j;
					ps++;
				}
			}
		}
		int max = 1 << ps;
		int[][][] a = new int[max][ps][ps];
		int inf = 1000000;
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < ps; j++) {
				for (int k = 0; k < ps; k++) {
					a[i][j][k] = inf;
				}
			}
		}
		for (int i = 0; i < ps; i++) {
			a[1 << i][i][i] = 0;
		}
		for (int mask = 0; mask < max; mask++) {
			for (int i = 0; i < ps; i++) {
				for (int j = 0; j < ps; j++) {
					if (a[mask][i][j] >= inf)
						continue;
					for (int k = 0; k < ps; k++) {
						if ((mask & (1 << k)) == 0) {
							int m1 = mask | (1 << k);
							a[m1][i][k] = Math.min(a[m1][i][k], a[mask][i][j] + d(px[j] - px[k], py[j] - py[k]));
						}
					}
				}
			}
		}
		int[][] b = new int[ks + 1][max];
		int[] c = new int[max];
		Arrays.fill(b[0], inf);
		b[0][0] = 0;
		for (int i = 0; i < ks; i++) {
			Arrays.fill(c, inf);
			b[i + 1] = b[i].clone();
			for (int m1 = 0; m1 < max; m1++) {
				for (int k = 0; k < ps; k++) {
					for (int j = 0; j < ps; j++) {
						c[m1] = Math.min(c[m1], d(px[k] - kx[i], py[k] - ky[i]) + a[m1][k][j]);
					}
				}
			}
			for (int mask = 0; mask < max; mask++) {
				if (b[i][mask] >= inf)
					continue;
				for (int m1 = 0; m1 < max; m1++) {
					if ((mask & m1) > 0)
						continue;
					int m2 = mask | m1;
					b[i + 1][m2] = Math.min(b[i + 1][m2], b[i][mask] + c[m1]);
				}
			}
		}
		return b[ks][max - 1];
	}

	public static void main(String[] args) {
		int a = new PawnsAndKings().minNumberOfMoves(new String[]{"...P....", "..P...P.", "........", "......P.", "....PP..", "....K...", ".P......", ".....K.."});
		System.out.println(a);
	}
}

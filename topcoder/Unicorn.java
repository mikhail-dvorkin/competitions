package topcoder;
public class Unicorn {
	public int countWays(int R, int C, int L, int seed, String word) {
		int[][] a = new int[C][R];
		int y, x = seed;
		int d = (65535 / L)+1;
		for (int r=0; r<R; r++)
		  for (int c=0; c<C; c++) {
		    x = (x * 25173 + 13849) % 65536;
		    a[c][r] = x / d;
		  }
		int mod = 1000000007;
		int[] wrd = new int[word.length()];
		for (int i = 0; i < word.length(); i++) {
			wrd[i] = word.charAt(i) - 'A';
		}
		int[] dx = new int[]{1, -1, -1, 1};
		int[] dy = new int[]{1, 1, -1, -1};
		int[] xs = new int[]{0, C - 1, C - 1, 0};
		int[] ys = new int[]{0, 0, R - 1, R - 1};
		int[][] b = new int[C][R];
		int[][] z = new int[C][R];
		for (x = 0; x < C; x++) {
			for (y = 0; y < R; y++) {
				z[x][y] = (a[x][y] == wrd[0]) ? 1 : 0;
			}
		}
		for (int k = 1; k < word.length(); k++) {
			int[][] zz = new int[C][R];
			int c2 = wrd[k];
			for (d = 0; d < 4; d++) {
				int ddx = dx[d];
				int ddy = dy[d];
				for (int xx = C - 1; xx >= 0; xx--) {
					for (int yy = R - 1; yy >= 0; yy--) {
						x = xs[d] + xx * ddx;
						y = ys[d] + yy * ddy;
						int x2 = x + ddx;
						int y2 = y + ddy;
						int r = 0;
						if (xx < C - 1) {
							r += b[x2][y];
						}
						if (yy < R - 1) {
							r += b[x][y2];
							if (r >= mod) r -= mod;
						}
						if (xx < C - 1 && yy < R - 1) {
							r += mod - b[x2][y2];
							if (r >= mod) r -= mod;
						}
						if (xx >= 2 && yy >= 2) {
							int x1 = x - 2 * ddx;
							int y1 = y - 2 * ddy;
							if (a[x1][y1] == c2) {
								int rr = zz[x1][y1] + r;
								if (rr >= mod) rr -= mod;
								zz[x1][y1] = rr;
							}
						}
						r += z[x][y];
						if (r >= mod) r -= mod;
						b[x][y] = r;
					}
				}
			}
			z = zz;
		}
		int ans = 0;
		for (x = 0; x < C; x++) {
			for (y = 0; y < R; y++) {
				ans += z[x][y];
				ans %= mod;
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		System.out.println(new Unicorn().countWays(300, 300, 1, 42, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	}
}

//QWERTYUIOPASDFGHJKLZXCVBNQWERTYUIOPASDFGHJKLZXCVBN

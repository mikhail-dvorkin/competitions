package topcoder.srm778;

public class DominoPlacement {
	final static int M = 1_000_000_007;

	private int solve(boolean[][] f) {
		int hei = f.length;
		int wid = f[0].length;
		int[] a = new int[1 << (hei + 1)];
		a[0] = 1;
		for (int y = 0; y < wid; y++) {
			for (int x = 0; x < hei; x++) {
				int[] b = new int[a.length];
				for (int mask = 0; mask < a.length; mask++) {
					if (a[mask] == 0) continue;
					boolean ver = ((mask >> hei) & 1) == 1;
					boolean hor = ((mask >> x) & 1) == 1;
					if (x == 0 && ver) continue;
					if (f[x][y] && (ver || hor)) continue;
					int mBase = mask & (~(1 << hei)) & (~(1 << x));
					int mVer = mBase | (1 << hei);
					int mHor = mBase | (1 << x);
					if (ver && hor) continue;
					update(b, mBase, a[mask]);
					if (f[x][y]) continue;
					if (!hor) update(b, mVer, a[mask]);
					if (!ver) update(b, mHor, a[mask]);
				}
				a = b;
			}
		}
		return a[0];
	}

	private void update(int[] b, int newMask, int value) {
		b[newMask] += value;
		if (b[newMask] >= M) b[newMask] -= M;
	}

	public int countWays(String[] field) {
		boolean[][] a = new boolean[field.length][field[0].length()];
		boolean[][] b = new boolean[field[0].length()][field.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = b[j][i] = field[i].charAt(j) == '#';
			}
		}
		return solve(a.length > a[0].length ? b : a);
	}
}

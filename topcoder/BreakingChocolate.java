package topcoder;
import java.util.*;

public class BreakingChocolate {
	public int minSteps(int wid, int hei, int[] sx, int[] sy) {
		int n = sx.length;
		Set<Integer> xs = new TreeSet<Integer>();
		Set<Integer> ys = new TreeSet<Integer>();
		xs.add(0);
		xs.add(wid);
		ys.add(0);
		ys.add(hei);
		for (int i = 0; i < n; i++) {
			sx[i]--;
			sy[i]--;
			xs.add(sx[i]);
			xs.add(sx[i] + 1);
			ys.add(sy[i]);
			ys.add(sy[i] + 1);
		}
		wid = xs.size() - 1;
		hei = ys.size() - 1;
		List<Integer> xss = new ArrayList<Integer>(xs);
		List<Integer> yss = new ArrayList<Integer>(ys);
		boolean[][] a = new boolean[hei][wid];
		for (int i = 0; i < n; i++) {
			int x = 0;
			while (xss.get(x) <= sx[i]) {
				x++;
			}
			x--;
			int y = 0;
			while (yss.get(y) <= sy[i]) {
				y++;
			}
			y--;
			a[y][x] = true;
		}
		int[][] b = new int[hei + 1][wid + 1];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				b[i + 1][j + 1] = b[i][j + 1] + b[i + 1][j] - b[i][j] + (a[i][j] ? 1 : 0);
			}
		}
		
		byte[][][][] ans = new byte[hei][hei + 1][wid][wid + 1];
		for (int ilen = 1; ilen <= hei; ilen++) {
			for (int i1 = 0; i1 < hei; i1++) {
				int i2 = i1 + ilen;
				if (i2 > hei) {
					break;
				}
				for (int jlen = 1; jlen <= wid; jlen++) {
					for (int j1 = 0; j1 < wid; j1++) {
						int j2 = j1 + jlen;
						if (j2 > wid) {
							break;
						}
						int area = (i2 - i1) * (j2 -  j1);
						int s = b[i2][j2] - b[i1][j2] - b[i2][j1] + b[i1][j1];
						if (s == 0 || s == area) {
							continue;
						}
						int best = Integer.MAX_VALUE;
						for (int i = i1 + 1; i < i2; i++) {
							int x = ans[i1][i][j1][j2] + ans[i][i2][j1][j2];
							if (x < 0) {
								x += 256;
							}
							best = Math.min(best, x);
						}
						for (int j = j1 + 1; j < j2; j++) {
							int x = ans[i1][i2][j1][j] + ans[i1][i2][j][j2];
							if (x < 0) {
								x += 256;
							}
							best = Math.min(best, x);
						}
						ans[i1][i2][j1][j2] = (byte) (best + 1);
					}
				}
			}
		}
		int x = ans[0][hei][0][wid];
		if (x < 0) {
			x += 256;
		}
		return x;
	}

}

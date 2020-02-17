package topcoder;
import java.util.*;

public class FourSubstrings {
	public int[] getCoverageCount(String[] text, String e, String b, String c, String d) {
		String t = "";
		for (String tt : text)
			t += tt;
		int n = t.length();
		String[] a = new String[]{e, b, c, d};
		int[] l = new int[4];
		for (int j = 0; j < 4; j++) {
			l[j] = a[j].length();	
		}
		boolean[][] can = new boolean[n + 1][4];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 4; j++) {
				if (i + l[j] <= n && t.substring(i, i + l[j]).equals(a[j])) {
					can[i][j] = true;
				}
			}
		}
		int[][][] min = new int[n + 1][16][51];
		int[][][] max = new int[n + 1][16][51];
		int inf = 100000;
		for (int i = 0; i <= n; i++) {
			for (int m = 0; m < 16; m++) {
				Arrays.fill(min[i][m], inf);
				Arrays.fill(max[i][m], -inf);
			}
		}
		min[0][0][0] = 0;
		max[0][0][0] = 0;
		for (int i = 0; i < n; i++) {
			for (int m = 0; m < 16; m++) {
				for (int f = 0; f <= 50; f++) {
					if (min[i][m][f] == inf)
						continue;
					int take = 0;
					for (int j = 0; j < 4; j++) {
						if (can[i][j] && ((m & (1 << j)) == 0))
							take ^= 1 << j;
					}
					for (int tt = 0; tt < 16; tt++) {
						if ((tt & take) == tt) {
							int ff = f;
							for (int j = 0; j < 4; j++) {
								if ((tt & (1 << j)) != 0)
									ff = Math.max(ff, l[j]);
							}
							int h = (ff > 0) ? 1 : 0;
							ff--;
							if (ff < 0)
								ff = 0;
							min[i + 1][m | tt][ff] = Math.min(min[i][m][f] + h, min[i + 1][m | tt][ff]);
							max[i + 1][m | tt][ff] = Math.max(max[i][m][f] + h, max[i + 1][m | tt][ff]);
						}
					}
				}
			}
		}
		int aa = min[n][15][0];
		int bb = max[n][15][0];
		return new int[]{aa, bb};
	}
	
	public static void main(String[] args) {
		new FourSubstrings().getCoverageCount(
				new String[]{"hello"},
				"he", "l", "l", "o"
//				new String[]{"hello"},
//				"he", "l", "l", "o"
				);
	}
}

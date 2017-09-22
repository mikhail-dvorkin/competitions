package topcoder;
import java.util.Arrays;

public class Ear {
	static int[] toInt(String[] s) {
		StringBuilder sb = new StringBuilder();
		for (String ss : s) {
			sb.append(ss);
		}
		String[] tokens = sb.toString().split(" ");
		int[] a = new int[tokens.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = Integer.parseInt(tokens[i]);
		}
		return a;
	}
	
	public long getCount(String[] redX, String[] blueX, String[] blueY) {
		long ans = 0;
		int[] rx = toInt(redX);
		Arrays.sort(rx);
		int[] bx = toInt(blueX);
		int[] by = toInt(blueY);
		int rs = rx.length;
		int[] nx = new int[rs];
		for (int i = 0; i < rs; i++) {
			nx[i] = -rx[rs - 1 - i];
		}
		int bs = bx.length;
		for (int i = 0; i < bs; i++) {
			for (int j = 0; j < bs; j++) {
				int x1 = bx[i];
				int y1 = by[i];
				int x2 = bx[j];
				int y2 = by[j];
				if (y1 <= y2) {
					continue;
				}
				int mind = Math.max(x1 + 1, (x2 * y1 - x1 * y2) / (y1 - y2) + 1);
				int minc = x2 + 1;
				long cd = count(rx, minc, mind);
				int maxa = Math.min(x1 - 1, (x2 * y1 - x1 * y2 - 1) / (y1 - y2));
				int maxb = x2 - 1;
				long ab = count(nx, -maxb, -maxa);
				ans += ab * cd;
			}
		}
		return ans;
	}

	private static int count(int[] x, int c, int d) {
		int r = 0;
		int n = x.length;
		int cs = 0;
		for (int i = 0; i < n; i++) {
			if (x[i] < c) {
				continue;
			}
			if (x[i] >= d) {
				r += cs;
			}
			cs++;
		}
		return r;
	}

}

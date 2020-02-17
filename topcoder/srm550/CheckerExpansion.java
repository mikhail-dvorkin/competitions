package topcoder.srm550;
import java.util.*;

public class CheckerExpansion {
	char[][] r;
	long x0, y0, x1, y1, t;

	public String[] resultAfter(long tt, long xx0, long yy0, int w, int h) {
		this.t = tt;
		this.x0 = xx0;
		this.y0 = yy0;
		x1 = x0 + w;
		y1 = y0 + h;
		r = new char[h][w];
		for (int i = 0; i < h; i++) {
			Arrays.fill(r[i], '.');
		}
		fill(0, 0, 1L << 50);
		String[] ans = new String[h];
		for (int i = 0; i < h; i++) {
			ans[i] = new String(r[h - 1 - i]);
		}
		return ans;
	}

	private void fill(long xf, long yf, long s) {
		if (xf >= x1 || yf >= y1 || (xf + 2 * s - 2) < x0 || (yf + s - 1) < y0) {
			return;
		}
		if (s == 1) {
			if (xf + yf >= 2 * t) {
				return;
			}
			r[(int) (yf - y0)][(int) (xf - x0)] = ((xf + yf) % 4 != 0) ? 'B' : 'A';
			return;
		}
		s /= 2;
		fill(xf, yf, s);
		fill(xf + s, yf + s, s);
		fill(xf + 2 * s, yf, s);
	}

}

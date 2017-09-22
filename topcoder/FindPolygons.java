package topcoder;
import java.awt.Point;
import java.util.*;

public class FindPolygons {
	public double minimumPolygon(int p) {
		int ans = Integer.MAX_VALUE;
		int[] sqrt = new int[p * p / 4 + 1];
		for (int x = 0; x <= p / 2; x++) {
			sqrt[x * x] = x;
		}
		ArrayList<Point> list = new ArrayList<Point>();
		for (int x = 0; x <= p / 2; x++) {
			for (int y = x; y <= p / 2; y++) {
				int d2 = x * x + y * y;
				if (d2 <= p * p / 4 && sqrt[d2] > 0) {
					list.add(new Point(x, y));
				}
			}
		}
		int m = list.size();
		int[] xs = new int[m];
		int[] ys = new int[m];
		int[] ds = new int[m];
		for (int i = 0; i < m; i++) {
			xs[i] = list.get(i).x;
			ys[i] = list.get(i).y;
			ds[i] = sqrt[xs[i] * xs[i] + ys[i] * ys[i]];
		}
		for (int i = 0; i < m; i++) {
			int x1 = xs[i];
			int y1 = ys[i];
			int a = ds[i];
			for (int j = i; j < m; j++) {
				int x2 = xs[j];
				int y2 = ys[j];
				int b = ds[j];
				if (a + b > p) {
					continue;
				}
				for (int r = 0; r < 2; r++) {
					int d;
					if (r == 0) {
						d = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
					} else {
						d = (x1 - y2) * (x1 - y2) + (y1 - x2) * (y1 - x2);
					}
					if (d > p * p / 4) {
						continue;
					}
					int c = sqrt[d];
					if (c == 0) {
						 continue;
					}
					int per = a + b + c;
					if (per != p) {
						continue;
					}
					int[] s = new int[]{a, b, c};
					Arrays.sort(s);
					if (s[2] == s[0] + s[1]) {
						continue;
					}
					int cur = s[2] - s[0];
					ans = Math.min(ans, cur);
				}
			}
		}
//		int t = 0;
//		for (int p = 1; p <= L; p++) {
//			if (ans[p] != Integer.MAX_VALUE) {
//				System.out.println("if (L==" + p + ") a=" + ans[p] + ";");
//				t++;
//			}
//		}
//		System.out.println(t);
		if (ans != Integer.MAX_VALUE) {
			return ans;
		}
		if (p % 4 == 0) {
			return 0;
		}
		if (p % 2 == 0 && p > 2) {
			return 1;
		}
		return -1;
	}

}

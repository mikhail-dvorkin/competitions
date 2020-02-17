package topcoder.srm346;
import java.util.*;

public class Meteorites {
	class Event implements Comparable<Event> {
		double a;
		int open, id;
		boolean cover;

		public Event(double x, int op, int i) {
			a = x;
			open = op;
			id = i;
			while (a > 2 * Math.PI - eps)
				a -= 2 * Math.PI;
			while (a < -eps)
				a += 2 * Math.PI;
		}

		@Override
		public int compareTo(Event that) {
			if (id == that.id && Math.abs(a - that.a) < eps)
				return that.open - open;
			return Double.compare(a, that.a);
		}
	}

	private static final double eps = 1e-9;

	public double perimeter(int[] x, int[] y, int[] r) {
		int n = x.length;
		boolean[] crap = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					continue;
				double d = Math.hypot(x[i] - x[j], y[i] - y[j]);
				if (r[j] > d + r[i] - eps) {
					if (r[i] == r[j]) {
						crap[Math.min(i, j)] = true;
						continue;
					}
					crap[i] = true;
				}
			}
		}
		double ans = 0;
		for (int i = 0; i < n; i++) {
			if (crap[i])
				continue;
			ArrayList<Event> ev = new ArrayList<Event>();
			for (int j = 0; j < n; j++) {
				if (i == j || crap[j])
					continue;
				double d = Math.hypot(x[i] - x[j], y[i] - y[j]);
				if (d > r[i] + r[j] + eps)
					continue;
				double a = Math.atan2(y[j] - y[i], x[j] - x[i]);
				double le = 0;
				double ri = Math.PI;
				for (int iter = 0; iter < 100; iter++) {
					double mi = (le + ri) * 0.5;
					double xx = x[i] + r[i] * Math.cos(a + mi);
					double yy = y[i] + r[i] * Math.sin(a + mi);
					double dd = Math.hypot(xx - x[j], yy - y[j]);
					if (dd > r[j])
						ri = mi;
					else
						le = mi;
				}
				ev.add(new Event(a - le, +1, j));
				ev.add(new Event(a + le, -1, j));
			}
			Collections.sort(ev);
			int l = ev.size();
			if (l == 0) {
				ans += 2 * Math.PI * r[i];
				continue;
			}
			for (int j = 0; j < l; j++) {
				if (ev.get(j).open > 0) {
					int id = ev.get(j).id;
					for (int k = j;; k = (k + 1) % l) {
						if (ev.get(k).id == id && k != j)
							break;
						ev.get(k).cover = true;
					}
				}
			}
			for (int j = 0; j < l; j++) {
				if (ev.get(j).cover)
					continue;
				double xx = ev.get((j + 1) % l).a - ev.get(j).a;
				if (xx < 0)
					xx += 2 * Math.PI;
				ans += r[i] * xx;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		double a = new Meteorites().perimeter(
				new int[]{0, 1000000},
				new int[]{0, 1000000},
				new int[]{1, 1000000}
				);
		System.out.println(a);
	}
}

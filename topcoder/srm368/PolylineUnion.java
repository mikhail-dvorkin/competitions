package topcoder.srm368;
import java.awt.Point;
import java.util.*;

public class PolylineUnion {
	int ori(Point a, Point b, Point c) {
		int area =  a.x * b.y - a.y * b.x +
		b.x * c.y - b.y * c.x +
		c.x * a.y - c.y * a.x;
		if (area > 0)
			return 1;
		if (area < 0)
			return -1;
		return 0;
	}

	class Polyline {
		ArrayList<Point> al = new ArrayList<Point>();

		public Polyline(String s) {
			String[] ss = s.split("-");
			Point pt = null;
			for (String p : ss) {
				String[] pp = p.split(",");
				pt = new Point(Integer.parseInt(pp[0]), Integer.parseInt(pp[1]));
				al.add(pt);
			}
			al.add(pt);
		}

		public boolean intersects(Polyline that) {
			for (int i = 0; i < al.size() - 1; i++) {
				Point a = al.get(i);
				Point b = al.get(i + 1);
				int dx = b.x - a.x;
				int dy = b.y - a.y;
				seg:
				for (int j = 0; j < that.al.size() - 1; j++) {
					Point c = that.al.get(j);
					Point d = that.al.get(j + 1);
					int ex = d.x - c.x;
					int ey = d.y - c.y;
					if (dx * ey == ex * dy) {
						boolean dp = (dx == 0 && dy == 0);
						boolean ep = (ex == 0 && ey == 0);
						if (dp && ep) {
							if (a.equals(c))
								return true;
							continue seg;
						}
						int o1 = ori(a, b, c);
						int o2 = ori(a, b, d);
						if (o1 != 0 || o2 != 0)
							continue seg;
						o1 = ori(c, d, a);
						o2 = ori(c, d, b);
						if (o1 != 0 || o2 != 0)
							continue seg;
						int t1 = Math.max(Math.min(a.x, b.x), Math.min(c.x, d.x));
						int t2 = Math.min(Math.max(a.x, b.x), Math.max(c.x, d.x));
						if (t1 > t2)
							continue seg;
						t1 = Math.max(Math.min(a.y, b.y), Math.min(c.y, d.y));
						t2 = Math.min(Math.max(a.y, b.y), Math.max(c.y, d.y));
						if (t1 > t2)
							continue seg;
						return true;
					} else {
						int o1 = ori(a, b, c);
						int o2 = ori(a, b, d);
						if (o1 != 0 && o1 == o2)
							continue seg;
						o1 = ori(c, d, a);
						o2 = ori(c, d, b);
						if (o1 != 0 && o1 == o2)
							continue seg;
						return true;
					}
				}
			}
			return false;
		}
	}

	private class DSU {
		int[] a;
		int[] r;

		public DSU(int size) {
			a = new int[size];
			r = new int[size];
			for (int i = 0; i < size; i++)
				a[i] = i;
		}

		public int get(int v) {
			if (a[v] == v)
				return v;
			return a[v] = get(a[v]);
		}

		public boolean union(int u, int v) {
			u = get(u);
			v = get(v);
			if (u == v)
				return false;
			if (r[u] == r[v])
				r[u]++;
			if (r[u] > r[v])
				a[v] = u;
			else
				a[u] = v;
			return true;
		}

		public boolean same(int u, int v) {
			return get(u) == get(v);
		}
	}

	public int countComponents(String[] polylines) {
		StringBuilder sb = new StringBuilder();
		for (String s : polylines)
			sb.append(s);
		String[] s = sb.toString().split(" ");
		int n = s.length;
		Polyline[] lines = new Polyline[n];
		for (int i = 0; i < n; i++) {
			lines[i] = new Polyline(s[i]);
		}
		DSU dsu = new DSU(n);
		int pics = n;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (dsu.same(i, j))
					continue;
				if (lines[i].intersects(lines[j])) {
					dsu.union(i, j);
					pics--;
				}
			}
		}
		return pics;
	}

	public static void main(String[] args) {
		int a = new PolylineUnion().countComponents(new String[]{"0,0-4181,6765 2584,4181-2584,4181"});
		System.out.println(a);
	}
}

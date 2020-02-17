package topcoder.srm350;
import java.math.BigInteger;
import java.util.*;

public class PlaneDivision {
	class Pair {
		int x, y;

		public Pair(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public int hashCode() {
			return 29 * x ^ y;
		}

		@Override
		public boolean equals(Object obj) {
			Pair t = (Pair) obj;
			return x == t.x && y == t.y;
		}
	}

	class Rat implements Comparable<Rat> {
		long num, den;
		double val;

		public Rat(long a, long b) {
			if (b < 0) {
				a = -a;
				b = -b;
			}
			long d = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
			num = a / d;
			den = b / d;
			val = num * 1d / den;
		}

		@Override
		public int hashCode() {
			return (int) (num * 29 ^ den);
		}

		@Override
		public boolean equals(Object obj) {
			Rat that = (Rat) obj;
			return num == that.num && den == that.den;
		}

		@Override
		public int compareTo(Rat o) {
			return Long.signum(num * o.den - o.num * den);
		}
	}

	HashMap<Point, Integer> all = new HashMap<Point, Integer>();
	ArrayList<Point> al = new ArrayList<Point>();

	class Point implements Comparable<Point> {
		Rat x, y;
		int id;

		public Point(Rat a, Rat b) {
			x = a;
			y = b;
			if (all.containsKey(this)) {
				id = all.get(this);
			} else {
				all.put(this, id = all.size());
				al.add(this);
			}
		}

		@Override
		public int hashCode() {
			return x.hashCode() * 29 ^ y.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			Point that = (Point) obj;
			return x.equals(that.x) && y.equals(that.y);
		}

		@Override
		public int compareTo(Point o) {
			if (compx)
				return x.compareTo(o.x);
			return y.compareTo(o.y);
		}
	}

	boolean compx;

	class Line {
		int x1, y1, x2, y2;
		long a, b, c;

		public Line(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			a = y1 - y2;
			b = x2 - x1;
			c = x1 * y2 - y1 * x2;
		}

		public Point inter(Line t) {
			long d = b * t.a - a * t.b;
			if (d == 0)
				return null;
			Rat y = new Rat(a * t.c - c * t.a, b * t.a - a * t.b);
			Rat x = new Rat(b * t.c - c * t.b, a * t.b - b * t.a);
			return new Point(x, y);
		}
	}

	HashMap<Integer, ArrayList<Integer>> nei = new HashMap<Integer, ArrayList<Integer>>();

	public int howManyFiniteParts(int[] x1, int[] y1, int[] x2, int[] y2) {
		int n = x1.length;
		Line[] line = new Line[n];
		for (int i = 0; i < n; i++) {
			line[i] = new Line(x1[i], y1[i], x2[i], y2[i]);
		}
		for (Line a : line) {
			compx = (a.x1 != a.x2);
			TreeSet<Point> pps = new TreeSet<Point>();
			for (Line b : line) {
				Point p = a.inter(b);
				if (p == null)
					continue;
				pps.add(p);
			}
			ArrayList<Point> ps = new ArrayList<Point>(pps);
			for (int i = 0; i < ps.size() - 2; i++) {
				Point p1 = ps.get(i);
				Point p2 = ps.get(i + 1);
				addEdge(p1, p2);
				addEdge(p2, p1);
			}
		}
		int ans = 0;
		for (Point p : al) {
			if (!nei.containsKey(p.id))
				continue;
			for (int tid : nei.get(p.id)) {
				if (were.contains(new Pair(p.id, tid)))
					continue;
				ans++;
				dfs(p.id, tid);
			}
		}
		return ans;
	}

	private void dfs(int id, int tid) {
		were.add(new Pair(id, tid));
		ArrayList<Integer> c = nei.get(tid);
		Point a = al.get(id);
		Point b = al.get(tid);
		int e = 0;
		double ba = 100;
		for (int cc : c) {
			Point d = al.get(cc);
			double a1 = getAngle(b, a);
			double a2 = getAngle(b, d);
			double an = a1 - a2;
			while (an < 0)
				an += 2 * Math.PI;
			while (an > 2 * Math.PI)
				an -= 2 * Math.PI;
			if (an < ba) {
				ba = an;
				e = cc;
			}
		}
		dfs(tid, e);
	}

	private double getAngle(Point a, Point b) {
		return Math.atan2(b.y.val - a.y.val, b.x.val - a.x.val);
	}

	HashSet<Pair> were = new HashSet<Pair>();

	private void addEdge(Point p1, Point p2) {
		if (nei.containsKey(p1.id)) {
			nei.get(p1.id).add(p2.id);
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(p2.id);
			nei.put(p1.id, list);
		}
	}

	public static void main(String[] args) {
		int a = new PlaneDivision().howManyFiniteParts(
				new int[]{0, 1, 2},
				new int[]{0, 1, -1},
				new int[]{1, 2, 0},
				new int[]{0, -1, 2}
		);
		System.out.println(a);
	}
}

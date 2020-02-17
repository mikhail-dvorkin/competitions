package topcoder.tco2009.round2;
import java.util.*;

public class ExtendableTriangles {
	int gcd(int a, int b) {
		while (b > 0) {
			int t = a % b;
			a = b;
			b = t;
		}
		return a;
	}

	class Point implements Comparable<Point> {
		int x, y, z, c;

		public Point(int x, int y, int z, int c) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.c = c;
		}

		@Override
		public int compareTo(Point o) {
			return z - o.z;
		}

		@Override
		public String toString() {
			return z + " " + c + " " + x + " " + y;
		}
	}

	public int getCount(String[] grid) {
		int hei = grid.length;
		int wid = grid[0].length();
		ArrayList<Point> p = new ArrayList<Point>();
		Point[][] pp = new Point[hei][wid];
		int[] number = new int[3];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				pp[i][j] = new Point(i, j, 0, "RGB".indexOf(grid[i].charAt(j)));
				p.add(pp[i][j]);
				number[pp[i][j].c]++;
			}
		}
		if (number[0] == 0 || number[1] == 0 || number[2] == 0)
			return 0;
		int[] minz = new int[3];
		int[] maxz = new int[3];
		int[] minn = new int[3];
		int[] maxn = new int[3];
		int[] cur = new int[3];
		int ans = 0;
		for (int dx = 0; dx < hei; dx++) {
			for (int dy = -wid + 1; dy <= wid - 1; dy++) {
				if ((dx == 0 && dy <= 0) || gcd(dx, Math.abs(dy)) > 1)
					continue;
				for (int i = 0; i < hei; i++) {
					for (int j = 0; j < wid; j++) {
						pp[i][j].z = - dy * i + dx * j;
					}
				}
				Collections.sort(p);
				Arrays.fill(minz, Integer.MAX_VALUE);
				Arrays.fill(maxz, Integer.MIN_VALUE);
				Arrays.fill(minn, 0);
				Arrays.fill(maxn, 0);
				for (Point q : p) {
					if (q.z < minz[q.c]) {
						minz[q.c] = q.z;
						minn[q.c] = 1;
					} else if (q.z == minz[q.c]) {
						minn[q.c]++;
					}
					if (q.z > maxz[q.c]) {
						maxz[q.c] = q.z;
						maxn[q.c] = 1;
					} else if (q.z == maxz[q.c]) {
						maxn[q.c]++;
					}
				}
				int zz = Integer.MIN_VALUE;
				for (int t = 0; t < p.size(); t++) {
					Point q = p.get(t);
					if (q.z == zz)
						continue;
					zz = q.z;
					int tt = t;
					while (tt + 1 < p.size() && p.get(tt + 1).z == zz)
						tt++;
					Arrays.fill(cur, 0);
					for (int i = t; i <= tt; i++) {
						cur[p.get(i).c]++;
					}
					for (int col = 0; col < 3; col++) {
						int num = 1;
						for (int i = 0; i < 3; i++) {
							if (i == col)
								continue;
							num *= cur[i];
						}
						int dist = Math.max(Math.abs(zz - minz[col]), Math.abs(zz - maxz[col]));
						int far = 0;
						if (Math.abs(zz - minz[col]) == dist) {
							far += minn[col];
						}
						if (Math.abs(zz - maxz[col]) == dist && minz[col] != maxz[col]) {
							far += maxn[col];
						}
						ans += num * far;
					}
				}
			}
		}
		return ans;
//		return number[0] * number[1] * number[2] - ans / 3;
	}

	public static void main(String[] args) {
		System.out.println(new ExtendableTriangles().getCount(new String[]{"RGB", "RGB"}));
	}
}

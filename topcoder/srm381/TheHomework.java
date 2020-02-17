package topcoder.srm381;
import java.util.*;

public class TheHomework {
	public int transform(int[] a, int[] b) {
		Arrays.sort(a);
		Arrays.sort(b);
		int bs = b.length;
		int as = a.length;
		ArrayList<Integer> bb = new ArrayList<Integer>();
		for (int x : b)
			bb.add(x);
		int good = 0;
		for (int x : a) {
			if (bb.contains(x)) {
				bb.remove(Integer.valueOf(x));
				good++;
			}
		}
		int m = 14;
		int init = good * m + as;
		int fin = bs * m + bs;
		int[] way = new int[m * m];
		int[] que = new int[m * m];
		int inf = 1000000;
		Arrays.fill(way, inf);
		que[0] = init;
		way[init] = 0;
		int lo = 0;
		int hi = 1;
		while (lo < hi) {
			int state = que[lo++];
			if (state == fin)
				return way[state];
			int gd = state / m;
			int sz = state % m;
			int wy = way[state] + 1;
			for (int i = 1; i <= sz; i++) {
				int sz2 = sz + i;
				int gd2 = Math.min(gd + i, bs);
				if (sz2 >= m)
					continue;
				int st = gd2 * m + sz2;
				if (wy < way[st]) {
					way[st] = wy;
					que[hi++] = st;
				}
			}
			for (int i = 1; 2 * i <= sz; i++) {
				int sz2 = sz - i;
				int bad = Math.max(sz - gd - i, 0);
				int gd2 = sz2 - bad;
				int st = gd2 * m + sz2;
				if (wy < way[st]) {
					way[st] = wy;
					que[hi++] = st;
				}
			}
			for (int i = 1; 2 * i <= sz; i++) {
				int sz2 = sz;
				int gd2 = Math.min(gd + i, bs);
				int st = gd2 * m + sz2;
				if (wy < way[st]) {
					way[st] = wy;
					que[hi++] = st;
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int a = new TheHomework().transform(new int[]{1, 2, 3, 4, 5, 9, 9, 9, 9, 9, 9, 9}, new int[]{1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6});
		System.out.println(a);
	}
}

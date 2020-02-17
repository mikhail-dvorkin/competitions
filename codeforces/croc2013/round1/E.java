package codeforces.croc2013.round1;
import java.util.*;

public class E {
	private static Scanner in;

	final static int none = Integer.MAX_VALUE;
	final static int empty = Integer.MIN_VALUE;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			b[i] = in.nextInt();
		}
		for (int q = 0; q < m; q++) {
			if (in.nextInt() == 1) {
				int x = in.nextInt() - 1;
				int y = in.nextInt() - 1;
				int k = in.nextInt();
				fill(y, y + k, x - y);
			} else {
				int y = in.nextInt() - 1;
				int shift = query(y);
				if (shift == none) {
					System.out.println(b[y]);
				} else {
					System.out.println(a[y + shift]);
				}
			}
		}
	}

	int query(int i) {
		return get(M + i);
	}

	void fill(int from, int to, int value) {
		update(1, M + from, M + to - 1, value, H - 1);
	}

	static final int H = 18;
	static final int M = 1 << (H - 1);
	static final int[] s = new int[2 * M];
	static {
		Arrays.fill(s, empty);
		s[1] = none;
	}

	static int get(int x) {
		for (int h = H - 1; h >= 0; h--) {
			int y = x >> h;
			if (s[y] != empty) {
				return s[y];
			}
		}
		throw new RuntimeException();
	}

	static void update(int v, int from, int to, int value, int h) {
		int v1 = v << h;
		int v2 = ((v + 1) << h) - 1;
		if (from > v2 || to < v1) {
			return;
		}
		from = Math.max(from, v1);
		to = Math.min(to, v2);
		if (from == v1 && to == v2) {
			s[v] = value;
			return;
		}
		if (s[v] != empty) {
			s[2 * v] = s[v];
			s[2 * v + 1] = s[v];
		}
		s[v] = empty;
		update(2 * v, from, to, value, h - 1);
		update(2 * v + 1, from, to, value, h - 1);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}

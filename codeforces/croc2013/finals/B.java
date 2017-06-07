package codeforces.croc2013.finals;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int hei = in.nextInt();
		int wid = in.nextInt();
		in.nextLine();
		String s = in.nextLine().trim();
		int m = s.length();
		int[] w = new int[m];
		int[] pos = new int[n + 1];
		Arrays.fill(pos, -1);
		pos[n] = m;
		for (int i = 0, c = 0; i < m; i++) {
			if (s.charAt(i) == ' ') {
				w[i] = -1;
				c++;
			} else {
				w[i] = c;
				if (pos[c] == -1) {
					pos[c] = i;
				}
			}
		}
//		System.out.println(Arrays.toString(w));
//		System.out.println(Arrays.toString(pos));
		int[] next = new int[n + 1];
		next[n] = n;
		for (int i = 0; i < n; i++) {
			int x = pos[i] + wid;
			if (x >= m) {
				next[i] = n;
			} else {
				int j = w[x];
				if (j == -1) {
					next[i] = w[x + 1];
				} else {
					next[i] = j;
				}
			}
		}
//		System.out.println(Arrays.toString(next));
		int[] a = calc(hei, next);
//		System.out.println(Arrays.toString(a));
		int bi = 0;
		int best = -1;
		for (int i = 0; i < n; i++) {
			int cur = a[i] - i;
			if (cur > best) {
				best = cur;
				bi = i;
			}
		}
		int i = bi;
		int last = a[i];
		while (i < last) {
			int j = next[i];
			System.out.println(s.substring(pos[i], pos[j]).trim());
			i = j;
		}
	}

	private int[] calc(int hei, int[] next) {
		if (hei == 1) {
			return next;
		}
		if (hei % 2 == 0) {
			int[] a = calc(hei / 2, next);
			return combine(a, a);
		} else {
			int[] a = calc(hei - 1, next);
			return combine(a, next);
		}
	}

	private int[] combine(int[] a, int[] b) {
		int[] c = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = b[a[i]];
		}
		return c;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

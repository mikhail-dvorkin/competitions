package rcc.y2011.finals;

import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int[][] a = new int[hei][wid];
		int[] b = new int[hei];
		int[] c = new int[wid];
		int d = 0;
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				a[i][j] = s.charAt(j) - '0';
				d ^= a[i][j];
				b[i] ^= a[i][j];
				c[j] ^= a[i][j];
			}
		}
		int be = even(b);
		int ce = even(c);
		int bo = (hei - 1) * (hei - 2) / 2 - be;
		int co = (wid - 1) * (wid - 2) / 2 - ce;
		long o = be * (long) ce + bo * (long) co;
		if (d == 1) {
			o = (hei - 1L) * (hei - 2L) / 2L * (wid - 1L) * (wid - 2L) / 2L - o;
		}
		System.out.println(o);
	}

	private int even(int[] b) {
		int n = b.length;
		int res = 0;
		int odd = 0;
		int even = 0;
		int s = 0;
		for (int i = 0; i < n - 1; i++) {
			s ^= b[i];
			if (s == 1) {
				res += odd;
				odd++;
			} else {
				res += even;
				even++;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
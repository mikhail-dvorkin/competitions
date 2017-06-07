package rcc.y2011.qual3;
import java.util.*;

public class D {
	private static Scanner in;

	public void run() {
		int t = 1000;
		int n = in.nextInt();
		int p = t - in.nextInt();
		int s = 0;
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			s += a[i];
		}
		int max = 1 << n;
		boolean[] good = new boolean[max];
		for (int m = 1; m < max; m++) {
			int sum = 0;
			for (int i = 0; i < n; i++) {
				if ((m & (1 << i)) != 0) {
					sum += a[i];
				}
			}
			good[m] = (sum == t);
		}
		int[] b = new int[max];
		for (int m = 1; m < max; m++) {
			for (int mm = m; mm != 0; mm = ((mm - 1) & m)) {
				b[m] = Math.max(b[m], b[m ^ mm] + (good[mm] ? 1 : 0));
			}
		}
		System.out.println(s - p * b[max - 1]);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}

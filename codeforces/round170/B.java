package codeforces.round170;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
//		for (int m = 3; m <= 100; m++) {
//			solve(2 * m, m);
//		}
		int n = in.nextInt();
		int m = in.nextInt();
		solve(n, m);
	}

	void solve(int n, int m) {
		if (m == 3) {
			if (n == 3) {
				System.out.println("0 0\n0 3\n3 0");
				return;
			}
			if (n == 4) {
				System.out.println("0 0\n0 3\n3 0\n1 1");
				return;
			}
			System.out.println(-1);
			return;
		}
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; i++) {
			double ang = 2 * Math.PI * i / m;
			double r = 100000000;
			if (i >= m) {
				r *= 0.999;
				ang += 0.0000001;
			}
			x[i] = (int) Math.round(r * Math.cos(ang));
			y[i] = (int) Math.round(r * Math.sin(ang));
			System.out.println(x[i] + " " + y[i]);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				for (int k = 0; k < j; k++) {
					long area = x[i] * 1L * y[j] - x[j] * 1L * y[i] +
							x[j] * 1L * y[k] - x[k] * 1L * y[j] +
							x[k] * 1L * y[i] - x[i] * 1L * y[k];
					if (area == 0) {
						throw new RuntimeException("m=" + m);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

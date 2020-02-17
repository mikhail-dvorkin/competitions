package codeforces.round200;
import java.util.*;

public class C {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		long[] h = new long[n];
		long[] p = new long[m];
		for (int i = 0; i < n; i++) {
			h[i] = in.nextLong();
		}
		for (int i = 0; i < m; i++) {
			p[i] = in.nextLong();
		}
		long low = -1;
		long high = h[n - 1] + p[m - 1];
		while (low + 1 < high) {
			long time = (low + high) / 2;
			int j = 0;
			boolean possible = false;
			for (int i = 0; i < n; i++) {
				if (j == m) {
					possible = true;
					break;
				}
				long p0 = p[j];
				long hh = h[i];
				long r;
				if (p0 >= hh) {
					r = hh + time;
				} else {
					long d = hh - p0;
					if (time < d) {
						break;
					}
					if (time <= 3 * d) {
						r = hh + (time - d) / 2;
					} else {
						r = p0 + time - d;
					}
				}
				while (j < m && p[j] <= r) {
					j++;
				}
				if (i == n - 1) {
					possible = (j == m);
				}
			}
			if (possible) {
				high = time;
			} else {
				low = time;
			}
		}
		System.out.println(high);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}

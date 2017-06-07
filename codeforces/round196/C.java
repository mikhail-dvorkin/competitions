package codeforces.round196;
import java.util.*;

public class C {
	private static Scanner in;
	
	int n;
	long[] a;
	int[] p;
	int sumP;
	int ans;

	public void run() {
		n = in.nextInt();
		a = new long[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextLong();
		}
		Arrays.sort(a);
		p = new int[n];
		sumP = 0;
		for (int i = 0; i < n; i++) {
			long v = a[i];
			for (int t = 2; t * (long) t <= v; t++) {
				while (v % t == 0) {
					v /= t;
					p[i]++;
				}
			}
			if (v > 1) {
				p[i]++;
			}
			if (p[i] > 1) {
				sumP += p[i];
			}
		}
		ans = Integer.MAX_VALUE;
		search(0, 0);
		System.out.println(ans);
	}

	void search(int state, int count) {
		int top = 1;
		if (Integer.bitCount(state) == 2 * n - 1 || n == 1) {
			top = 0;
		}
		ans = Math.min(ans, top + n + sumP - count);
		for (int t = 0; t < n; t++) {
			if (((state >> (2 * t)) & 3) != 0) {
				continue;
			}
			search(state, count, t, 0, a[t], 0);
		}
	}

	void search(int state, int count, int t, int x, long m, int taken) {
		while (x < n && (x == t || ((state >> (2 * x)) & 3) == 3 || m % a[x] != 0)) {
			x++;
		}
		if (x == n) {
			if (taken == 0) {
				return;
			}
			state |= 2 << (2 * t);
			for (int i = 0; i < n; i++) {
				if (((taken >> i) & 1) == 0) {
					continue;
				}
				state |= 3 << (2 * i);
				count += p[i];
			}
			search(state, count);
			return;
		}
		search(state, count, t, x + 1, m, taken);
		search(state, count, t, x + 1, m / a[x], taken | (1 << x));
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}
// 8 2 3 4 5 6 7 8 9
package codeforces.round207;
import java.io.*;
import java.util.*;

public class C {
	private static BufferedReader in;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] a = new int[5];
		for (int i = 0; i < n; i++) {
			a[Integer.parseInt(st.nextToken())]++;
		}
		int m = 0;
		for (int i = 0; i < 5; i++) {
			m += i * a[i];
		}
		if (m < 3 || m == 5) {
			System.out.println(-1);
			return;
		}
		int r = m / 3;
		int f = m % 3;
		n = Math.max(n, r);
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 4, j = 0; i >= 0; i--) {
			for (int k = 0; k < a[i]; k++) {
				x[j] = i;
				j++;
			}
		}
		for (int i = 0; i < r; i++) {
			y[i] = 3;
		}
		for (int i = 0; i < f; i++) {
			y[i]++;
		}
		int cur = 0;
		for (int i = 0; i < n; i++) {
			cur += Math.abs(x[i] - y[i]);
		}
		int ans = cur;
		while (f + 4 <= r) {
			cur -= Math.abs(x[f] - 3);
			cur += Math.abs(x[f] - 4);
			cur -= Math.abs(x[f + 1] - 3);
			cur += Math.abs(x[f + 1] - 4);
			cur -= Math.abs(x[f + 2] - 3);
			cur += Math.abs(x[f + 2] - 4);
			f += 3;
			cur -= Math.abs(x[r - 1] - 3);
			cur += Math.abs(x[r - 1]);
			r--;
			ans = Math.min(ans, cur);
		}
		System.out.println(ans / 2);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new C().run();
		in.close();
	}
}

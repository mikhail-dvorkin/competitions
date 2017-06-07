package codeforces.abbyy2013.round1;
import java.util.*;

public class E {
	private static Scanner in;
	
	int M = 1000000000;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		int[] fib = new int[n + 2];
		fib[0] = fib[1] = 1;
		for (int i = 2; i < n; i++) {
			fib[i] = fib[i - 1] + fib[i - 2];
			if (fib[i] >= M) {
				fib[i] -= M;
			}
		}
		for (int t = 0; t < m; t++) {
			int q = in.nextInt();
			if (q == 1) {
				int x = in.nextInt() - 1;
				int v = in.nextInt();
				a[x] = v;
				continue;
			}
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			long r = 0;
			for (int i = x; i <= y; i++) {
				r = (r + a[i] * (long) fib[i - x]) % M;
			}
			System.out.println(r);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new E().run();
		in.close();
	}
}

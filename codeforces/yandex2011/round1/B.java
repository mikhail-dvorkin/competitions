package codeforces.yandex2011.round1;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int m = 3;
		int[] windows = new int[m];
		int[] process = new int[m];
		for (int i = 0; i < m; i++) {
			windows[i] = in.nextInt();
		}
		for (int i = 0; i < m; i++) {
			process[i] = in.nextInt();
		}
		int n = in.nextInt();
		long[] come = new long[n];
		for (int i = 0; i < n; i++) {
			come[i] = in.nextInt();
		}
		long[] come0 = come.clone();
		long[] ready = new long[n];
		for (int w = 0; w < m; w++) {
			int k = windows[w];
			int p = process[w];
			long[] open = new long[Math.min(k, n)];
			for (int i = 0; i < n; i++) {
				int j = i % k;
				ready[i] = Math.max(come[i], open[j]) + p;
				open[j] = ready[i];
			}
			long[] t = come;
			come = ready;
			ready = t;
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, come[i] - come0[i]);
		}
		System.out.println(ans);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

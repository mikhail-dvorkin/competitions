package codeforces.round179;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		int[] l = new int[m];
		int[] r = new int[m];
		int[] d = new int[m];
		for (int i = 0; i < m; i++) {
			l[i] = in.nextInt() - 1;
			r[i] = in.nextInt();
			d[i] = in.nextInt();
		}
		long[] p = new long[m + 1];
		long[] q = new long[n + 1];
		for (int i = 0; i < k; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt();
			p[x]++;
			p[y]--;
		}
		long pp = 0;
		for (int i = 0; i < m; i++) {
			pp += p[i];
			q[l[i]] += pp * d[i];
			q[r[i]] -= pp * d[i];
		}
		long qq = 0;
		for (int i = 0; i < n; i++) {
			qq += q[i];
			System.out.print(a[i] + qq + " ");
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}

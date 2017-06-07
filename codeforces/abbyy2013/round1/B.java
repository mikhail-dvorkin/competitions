package codeforces.abbyy2013.round1;
import java.util.*;

public class B {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt() - 1;
		int[] a = new int[n];
		int[] b = new int[n];
		Arrays.fill(b, -1);
		boolean[] mark = new boolean[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			if (a[i] >= 0) {
				b[a[i]] = i;
			}
		}
		int p = -1;
		ArrayList<Integer> chains = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (mark[i]) {
				continue;
			}
			int j = i;
			while (a[j] >= 0) {
				j = a[j];
			}
			int size = 0;
			int pos = -1;
			while (j >= 0) {
				mark[j] = true;
				if (j == m) {
					pos = size;
				}
				size++;
				j = b[j];
			}
			if (pos >= 0) {
				p = pos;
			} else {
				chains.add(size);
			}
		}
		boolean[] c = new boolean[n + 1];
		c[0] = true;
		for (int x : chains) {
			for (int i = n; i >= 0; i--) {
				if (c[i]) {
					c[i + x] = true;
				}
			}
		}
		for (int i = 0; i <= n; i++) {
			if (c[i]) {
				System.out.println(i + p + 1);
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

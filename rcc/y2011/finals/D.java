package rcc.y2011.finals;

import java.util.*;

public class D {
	private static Scanner in;

	public void run() {
		int n = in.nextInt();
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++) {
			a[i] = new int[]{in.nextInt()};
		}
		int ans = 0;
		while (a.length > 1) {
			n = a.length;
			int m = (n + 1) / 2;
			int[][] b = new int[m][];
			if (n % 2 == 1) {
				b[m - 1] = a[m - 1];
			}
			for (int i = 0; i < n / 2; i++) {
				int[] x = a[i];
				int[] y = a[n - 1 - i];
				int[] z = inter(x, y);
				if (z.length == 0) {
					ans++;
					z = union(x, y);
				}
				b[i] = z;
			}
			a = b;
		}
		System.out.println(ans);
	}

	private int[] union(int[] x, int[] y) {
		int[] z = new int[x.length + y.length];
		System.arraycopy(x, 0, z, 0, x.length);
		System.arraycopy(y, 0, z, x.length, y.length);
		Arrays.sort(z);
		return z;
	}

	int[] temp = new int[200000];
	
	private int[] inter(int[] x, int[] y) {
		int j = 0;
		int f = 0;
		for (int t : x) {
			while (j + 1 < y.length && y[j] < t) {
				j++;
			}
			if (y[j] == t) {
				temp[f] = t;
				f++;
			}
		}
		return Arrays.copyOf(temp, f);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new D().run();
		in.close();
	}
}
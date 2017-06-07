package codeforces.round219;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(in.readLine());
		}
		Arrays.sort(a);
		int low = 0;
		int high = n / 2 + 1;
		while (low + 1 < high) {
			int m = (low + high) / 2;
			boolean can = true;
			for (int i = 0; i < m; i++) {
				if (2 * a[i] > a[n - m + i]) {
					can = false;
					break;
				}
			}
			if (can) {
				low = m;
			} else {
				high = m;
			}
		}
		System.out.println(n - low);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}

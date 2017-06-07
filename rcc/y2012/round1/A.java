package rcc.y2012.round1;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] p = new int[n];
		int[] c = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < n; i++) {
			p[i] = Integer.parseInt(st.nextToken()) - 1;
			if (p[i] < 0 || p[i] >= n) {
				return;
			}
		}
		Arrays.fill(c, -1);
		for (int i = 0; i < n; i++) {
			if (c[i] >= 0) {
				continue;
			}
			int j = i;
			while (true) {
				c[j] = i;
				j = p[j];
				if (c[j] >= 0) {
					break;
				}
			}
		}
		for (int t = 0; t < m; t++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			if (a < 0 || a >= n || b < 0 || b >= n) {
				return;
			}
			if (c[a] == c[b]) {
				System.out.println("Yes");
				continue;
			}
			if (c[a] == c[0] && c[b] == c[1]) {
				System.out.println("Yes");
				continue;
			}
			if (c[a] == c[1] && c[b] == c[0]) {
				System.out.println("Yes");
				continue;
			}
			System.out.println("No");
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}

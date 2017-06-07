package codeforces.round385;
import java.io.*;
import java.util.*;

public class B {
	private static BufferedReader in;

	public void run() throws IOException {
		int n = Integer.parseInt(in.readLine());
		int[] ans = new int[n];
		Arrays.fill(ans, Integer.MAX_VALUE);
		for (int t = 1; t < n; t *= 2) {
			for (int v = 0; v < 2; v++) {
				TreeSet<Integer> asked = new TreeSet<>();
				for (int i = 0; i < n; i++) {
					if (((i & t) != 0) == (v != 0)) {
						asked.add(i);
					}
				}
				System.out.println(asked.size());
				for (int i : asked) {
					System.out.print((i + 1) + " ");
				}
				System.out.println();
				System.out.flush();
				StringTokenizer st = new StringTokenizer(in.readLine());
				for (int i = 0; i < n; i++) {
					int m = Integer.parseInt(st.nextToken());
					if (asked.contains(i)) {
						continue;
					}
					ans[i] = Math.min(ans[i], m);
				}
			}
		}
		System.out.println(-1);
		for (int i : ans) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.flush();
	}
	
	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new B().run();
		in.close();
	}
}

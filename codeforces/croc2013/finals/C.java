package codeforces.croc2013.finals;
import java.io.*;
import java.util.*;

public class C {
	private static BufferedReader in;

	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		int[] b = new int[m];
		for (int i = 0; i < m; i++) {
			b[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(b);
		int[] f = new int[31];
		for (int i = 0; i < n; i++) {
			int ai = a[i];
			for (int t = 0; t <= 30; t++) {
				if (((ai >> t) & 1) == 1) {
					f[t]++;
				}
			}
		}
		int lo = 0;
		int hi = m + 1;
		while (lo + 1 < hi) {
			int mid = (lo + hi) / 2;
			int[] d = new int[32];
			for (int i = 0; i < mid; i++) {
				d[b[i]]++;
			}
			for (int t = 0; t <= 30; t++) {
				d[t] -= f[t];
				if (d[t] > 0) {
					d[t + 1] += (d[t] + 1) / 2;
				}
			}
			if (d[31] > 0) {
				hi = mid;
			} else {
				lo = mid;
			}
		}
		System.out.println(lo);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new C().run();
		in.close();
	}
}

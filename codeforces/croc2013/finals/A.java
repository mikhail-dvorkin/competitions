package codeforces.croc2013.finals;
import java.io.*;
import java.util.*;

public class A {
	private static BufferedReader in;

	int n, len, t;
	
	public void run() throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		len = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
		}
		double ans = 0;
		ans += calc(a);
		for (int i = 0; i < n; i++) {
			a[i] = len - 1 - a[i];
		}
		Arrays.sort(a);
		ans += calc(a);
		System.out.println(ans / 8);
	}

	private double calc(int[] a) {
		int b = (2 * t) % len;
		double res = 0;
		for (int i = 0, j = 0; i < n; i++) {
			while (j + 1 < i + n && (a[(j + 1) % n] + len - a[i]) % len <= b) {
				j++;
			}
			int x = j - i;
			int y = n - 1 - x;
			res += x * ((2 * t - b) / len + 1) + y * ((2 * t - b) / len);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new A().run();
		in.close();
	}
}

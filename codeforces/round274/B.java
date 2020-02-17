package codeforces.round274;
import java.io.*;
import java.util.*;

public class B {
	int n;
	int len;
	int[] a;

	void run() {
		n = in.nextInt();
		len = in.nextInt();
		int x = in.nextInt();
		int y = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		boolean hasX = check(x);
		boolean hasY = check(y);
		if (hasX && hasY) {
			System.out.println(0);
			return;
		}
		if (hasX) {
			System.out.println(1);
			System.out.println(y);
			return;
		}
		if (hasY) {
			System.out.println(1);
			System.out.println(x);
			return;
		}
		for (int i = 0, j = 0; i < n; i++) {
			while (j < n - 1 && a[j] < (long) a[i] + x + y) {
				j++;
			}
			if (a[j] == (long) a[i] + x + y) {
				System.out.println(1);
				System.out.println(a[i] + x);
				return;
			}
		}
		int d = y - x;
		for (int i = 0, j = 0; i < n; i++) {
			while (j < n - 1 && a[j] < a[i] + d) {
				j++;
			}
			if (a[j] == a[i] + d) {
				if (a[i] - x >= 0) {
					System.out.println(1);
					System.out.println(a[i] - x);
					return;
				}
				if (a[i] + y <= len) {
					System.out.println(1);
					System.out.println(a[i] + y);
					return;
				}
			}
		}
		System.out.println(2);
		System.out.println(x + " " + y);
	}

	boolean check(int z) {
		for (int i = 0, j = 0; i < n; i++) {
			while (j < n - 1 && a[j] < a[i] + z) {
				j++;
			}
			if (a[j] == a[i] + z) {
				return true;
			}
		}
		return false;
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = new MyScanner(br);
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new B().run();
		}
		br.close();
		out.close();
	}

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		MyScanner(BufferedReader br) {
			this.br = br;
		}

		void findToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		String next() {
			findToken();
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}

package codeforces.round320;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		int x = in.nextInt();
		int[] a = new int[n];
		int[] count = new int[32];
		for (int i = 0; i < n; i++) {
			int v = in.nextInt();
			a[i] = v;
			for (int j = 0; j < count.length; j++) {
				count[j] += (v >> j) & 1;
			}
		}
		long mult = 1;
		for (int i = 0; i < k; i++) {
			mult *= x;
		}
		long ans = 0;
		for (int v : a) {
			long orRest = 0;
			for (int j = 0; j < count.length; j++) {
				if (count[j] > ((v >> j) & 1)) {
					orRest |= 1 << j;
				}
			}
			ans = Math.max(ans, orRest | (mult * v));
		}
		System.out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";

		Locale.setDefault(Locale.US);
		BufferedReader br;
		//noinspection ConstantConditions
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
		final BufferedReader br;
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

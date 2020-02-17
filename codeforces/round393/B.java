package codeforces.round393;
import java.io.*;
import java.util.*;

public class B {
	final int[] T = new int[]{1, 90, 1440};
	final int[] C = new int[]{20, 50, 120};

	void run() {
		int n = in.nextInt();
		int[] t = new int[n + 1];
		int[] a = new int[n + 1];
		t[0] = -T[T.length - 1];
		for (int i = 1; i <= n; i++) {
			t[i] = in.nextInt();
			a[i] = Integer.MAX_VALUE;
			for (int d = 0; d < T.length; d++) {
				int j = Arrays.binarySearch(t, 0, i, t[i] - T[d]);
				if (j < 0) {
					j = -2 - j;
				}
				a[i] = Math.min(a[i], a[j] + C[d]);
			}
			out.println(a[i] - a[i - 1]);
		}
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

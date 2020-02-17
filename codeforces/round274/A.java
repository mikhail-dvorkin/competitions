package codeforces.round274;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		int maxA = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			maxA = Math.max(maxA, a[i]);
		}
		boolean[] passed = new boolean[n];
		Arrays.fill(passed, false);
		int curDay = 0;
		for (int i = 0; i < n; i++) {
			int minA = Integer.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				if (passed[j]) {
					continue;
				}
				minA = Math.min(minA, a[j]);
			}
			int pass = -1;
			int day = Integer.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				if (passed[j] || a[j] != minA) {
					continue;
				}
				if (a[j] < day && a[j] >= curDay) {
					day = a[j];
					pass = j;
				}
				if (b[j] < day && b[j] >= curDay) {
					day = b[j];
					pass = j;
				}
			}
			passed[pass] = true;
			curDay = day;
		}
		System.out.println(curDay);
	}

	static final boolean stdStreams = true;
	static final String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static final String inputFileName = fileName + ".in";
	static final String outputFileName = fileName + ".out";
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
			new A().run();
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

package codeforces.round559;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
		}
		int[] p = new int[n];
		if (!solve(a, p, 0, n, 1)) {
			out.println(-1);
			return;
		}
		for (int i = 0; i < n; i++) {
			out.print(p[i]);
			out.print(" ");
		}
		out.println();
	}

	static boolean solve(int[] a, int[] p, int from, int to, int min) {
		if (from == to) {
			return true;
		}
		if (a[from] > to) {
			return false;
		}
		if (a[from] < 0) {
			p[from] = min;
			return solve(a, p, from + 1, to, min + 1);
		}
		p[from] = min + a[from] - from - 1;
		return	solve(a, p, from + 1, a[from], min) &&
				solve(a, p, a[from], to, p[from] + 1);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			//out.print("Case #" + (test + 1) + ": ");
			new C().run();
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

package codeforces.round472;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int u = in.nextInt();
		int[] e = new int[n];
		for (int i = 0; i < n; i++) {
			e[i] = in.nextInt();
		}
		double ans = -1;
		for (int i = 0, k = 0; i <= n - 3; i++) {
			while (k + 1 < n && e[k + 1] - e[i] <= u) {
				k++;
			}
			if (k < i + 2) {
				continue;
			}
			ans = Math.max(ans, (e[k] - e[i + 1] * 1.0) / (e[k] - e[i]));
		}
		if (ans == -1) {
			out.println(-1);
			return;
		}
		out.println(ans);
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

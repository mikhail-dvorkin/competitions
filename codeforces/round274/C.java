package codeforces.round274;
import java.io.*;
import java.util.*;

public class C {
	final int M = 1000000007;

	void run() {
		int n = in.nextInt();
		int a = in.nextInt() - 1;
		int b = in.nextInt() - 1;
		int k = in.nextInt();
		if (b < a) {
			a = n - 1 - a;
			b = n - 1 - b;
		}
		int[] p = new int[b];
		int[] q = new int[b];
		int[] c = new int[b + 1];
		p[a] = 1;
		while (k-- > 0) {
			for (int i = 0; i < b; i++) {
				c[i + 1] = c[i] + p[i];
				if (c[i + 1] >= M) {
					c[i + 1] -= M;
				}
			}
			for (int i = 0; i < b; i++) {
				int m = (i + b - 1) / 2;
				q[i] = c[m + 1] - p[i];
				if (q[i] < 0) {
					q[i] += M;
				}
			}
			int[] t = p; p = q; q = t;
		}
		int ans = 0;
		for (int i = 0; i < b; i++) {
			ans += p[i];
			if (ans >= M) {
				ans -= M;
			}
		}
		System.out.println(ans);
	}

	static final boolean stdStreams = true;
	static final String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

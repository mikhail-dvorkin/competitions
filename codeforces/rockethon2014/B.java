package codeforces.rockethon2014;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		String s = in.next();
		int n = s.length();
		int ans = 1;
		for (char c = 'A'; c <= 'Z'; c++) {
			int[] a = new int[2];
			for (int i = 0; i < n; i++) {
				if (s.charAt(i) != c) {
					continue;
				}
				a[i % 2] = Math.max(a[i % 2], a[1 - (i % 2)] + 1);
			}
			ans = Math.max(ans, a[0]);
			ans = Math.max(ans, a[1]);
		}
		System.out.println(ans);
	}

	static final boolean stdStreams = true;
	static final String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new B().run();
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

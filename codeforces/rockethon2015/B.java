package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		long m = in.nextLong() - 1;
		int[] a = new int[n];
		int low = 0;
		int high = n;
		for (int i = 0; i < n - 1; i++) {
			int j = n - 2 - i;
			if (((m >> j) & 1) != 0) {
				high--;
				a[high] = i + 1;
			} else {
				a[low] = i + 1;
				low++;
			}
		}
		a[low] = n;
		out.println(Arrays.toString(a).replaceAll("[^\\s\\d]", ""));
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

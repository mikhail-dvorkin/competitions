package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class C {
	final int M = 10000;

	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt() + 1;
		}
		double ans = 0;
		for (int i = 1; i <= M; i++) {
			for (int m = 1; m < (1 << n); m++) {
				int bitCount = Integer.bitCount(m);
				loop:
				for (int k = 0; k <= n; k++) {
					if (k < n && ((m >> k) & 1) != 0) {
						continue;
					}
					if (k == n && bitCount == 1) {
						continue;
					}
					double p = 1;
					for (int j = 0; j < n; j++) {
						if (j == k) {
							double q = 1.0 * (b[j] - i - 1) / (b[j] - a[j]);
							if (q <= 0) {
								continue loop;
							}
							if (q >= 1) {
								q = 1;
							}
							p *= q;
							continue;
						}
						if (((m >> j) & 1) != 0) {
							if (b[j] <= i|| a[j] > i) {
								continue loop;
							}
							p *= 1.0 / (b[j] - a[j]);
							continue;
						}
						if (a[j] >= i) {
							continue loop;
						}
						if (i < b[j]) {
							p *= 1.0 * (i - a[j]) / (b[j] - a[j]);
						}
					}
					ans += p * i;
				}
			}
		}
		out.println(ans);
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

package codeforces.round286;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int m = 30000;
		int s = 500;
		int n = in.nextInt();
		int d = in.nextInt();
		int[] p = new int[m + 1];
		for (int i = 0; i < n; i++) {
			p[in.nextInt()]++;
		}
		short[][] a = new short[m + 1][2 * s + 1];
		for (int i = 0; i <= m; i++) {
			Arrays.fill(a[i], (short) -1);
		}
		a[d][s] = (short) p[d];
		int ans = p[d];
		for (int i = d; i <= m; i++) {
			for (int x = 0; x <= 2 * s; x++) {
				if (a[i][x] < 0) {
					continue;
				}
				int s1 = x - s + d;
				for (int z = -1; z <= 1; z++) {
					int k = i + s1 + z;
					if (k == i || k > m) {
						continue;
					}
					a[k][x + z] = (short) Math.max(a[k][x + z], a[i][x] + p[k]);
					ans = Math.max(ans, a[k][x + z]);
				}
			}
		}
		out.println(ans);
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

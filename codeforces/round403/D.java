package codeforces.round403;
import java.io.*;
import java.util.*;

public class D {
	static final long MAX = (long) 1e18;
	static final int T = 2 * (Long.SIZE - Long.numberOfLeadingZeros(MAX));

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		BitSet[][] reach = new BitSet[T][n];
		for (int i = 0; i < n; i++) {
			reach[0][i] = new BitSet();
			reach[1][i] = new BitSet();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int t = in.nextInt();
			reach[t][a].set(b);
		}
		for (int k = 2; k < T; k++) {
			BitSet[] same = reach[k - 2];
			BitSet[] other = reach[(k - 2) ^ 1];
			for (int i = 0; i < n; i++) {
				BitSet set = new BitSet();
				BitSet first = same[i];
				for (int j = 0; j < n; j++) {
					if (first.get(j)) {
						set.or(other[j]);
					}
				}
				reach[k][i] = set;
			}
		}
		BitSet pos = new BitSet();
		pos.set(0);
		long ans = 0;
		for (int k = T - 2; k >= 0; k -= 2) {
			BitSet next = new BitSet();
			for (int i = 0; i < n; i++) {
				if (pos.get(i)) {
					next.or(reach[k][i]);
				}
			}
			if (!next.isEmpty()) {
				pos = next;
				k ^= 1;
				ans += 1L << (k / 2);
			}
		}
		if (ans > MAX) {
			ans = -1;
		}
		out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D().run();
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

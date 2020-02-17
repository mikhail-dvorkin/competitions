package codeforces.globalround3;
import java.io.*;
import java.util.*;

public class F {
	final int M = 62;
	Random rnd = new Random(566);

	void run() {
		int n = in.nextInt();
		int[] val = new int[n];
		long[] mask = new long[n];
		long sum = 0;
		for (int i = 0; i < n; i++) {
			val[i] = in.nextInt();
			mask[i] = in.nextLong();
			sum += val[i];
		}
		if (sum < 0) {
			for (int i = 0; i < n; i++) {
				val[i] *= -1;
			}
		}
		long ans = 0;
		for (int j = 0; j < M; j++) {
			sum = 0;
			for (int i = 0; i < n; i++) {
				if ((mask[i] >> j) == 1) {
					sum += val[i];
				}
			}
			if (sum <= 0) {
				continue;
			}
			ans ^= (1L << j);
			for (int i = 0; i < n; i++) {
				if (((mask[i] >> j) & 1) != 0) {
					val[i] *= -1;
				}
			}
		}
		out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = F.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			//out.print("Case #" + (test + 1) + ": ");
			new F().run();
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

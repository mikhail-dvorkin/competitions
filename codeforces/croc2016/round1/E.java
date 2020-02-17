package codeforces.croc2016.round1;
import java.io.*;
import java.util.*;

public class E {
	final static int M = 1_000_000_007;

	void run() {
		int n = in.nextInt();
		int z = in.nextInt();
		String s = in.next();
		int[] last = new int[z];
		Arrays.fill(last, -1);
		int len = s.length() + n;
		int[] a = new int[len + 1];
		a[0] = 1;
		for (int i = 0; i < len; i++) {
			int c = 0;
			if (i < s.length()) {
				c = s.charAt(i) - 'a';
			} else {
				for (int j = 1; j < z; j++) {
					if (last[j] < last[c]) {
						c = j;
					}
				}
			}
			int newSeq = a[i];
			if (last[c] >= 0) {
				newSeq -= a[last[c]];
				if (newSeq < 0) {
					newSeq += M;
				}
			}
			a[i + 1] = a[i] + newSeq;
			if (a[i + 1] >= M) {
				a[i + 1] -= M;
			}
			last[c] = i;
			/*
  dp[i] = sum[i - 1] - sum[last[a[i]] - 1]
  sum[i] = sum[i - 1] + dp[i]
			 */
		}
		System.out.println(a[len]);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new E().run();
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

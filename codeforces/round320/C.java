package codeforces.round320;
import java.io.*;
import java.util.*;

public class C {
	final double EPS = 1e-12;

	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int max = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			max = Math.max(max, Math.abs(a[i]));
		}
		double low = -max;
		double high = max;
		double ans = 0;
		while (low + EPS < high) {
			double x = (low + high) / 2;
			double minSum = 0, maxSum = 0;
			double minPref = 0, maxPref = 0, pref = 0;
			for (int i = 0; i < n; i++) {
				pref += a[i] - x;
				maxSum = Math.max(maxSum, pref - minPref);
				minSum = Math.min(minSum, pref - maxPref);
				maxPref = Math.max(maxPref, pref);
				minPref = Math.min(minPref, pref);
			}
			ans = (maxSum - minSum) * 0.5;
			if (maxSum > -minSum) {
				low = x;
			} else {
				high = x;
			}
		}
		System.out.println(ans);
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

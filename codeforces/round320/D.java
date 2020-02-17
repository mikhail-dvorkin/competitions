package codeforces.round320;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		in.nextInt();
		int m = in.nextInt();
		String s = in.next();
		int n = s.length();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = s.charAt(i) - 'a';
		}
		long ans = n * (m - 1);
		int[] same = new int[n];
		for (int i = 0; i < n - 1; i++) {
			if (i + 1 >= 2 && a[i + 1] == a[i - 1]) {
				same[i + 1] = same[i - 1] + 1;
			}
			if (a[i] != a[i + 1]) {
				ans += (m - 1) * n;
				int x = same[i];
				int y = same[i + 1];
				int length = (1 + Math.min(x, y)) * 2;
				if (y > x) {
					length++;
				}
				ans -= length - 1;
			}
		}
		System.out.println(ans);
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

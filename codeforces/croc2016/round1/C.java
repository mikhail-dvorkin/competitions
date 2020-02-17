package codeforces.croc2016.round1;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int c = in.nextInt();
		String s = in.next();
		boolean[] free = new boolean[n];
		int[] cum = new int[n + 1];
		for (int i = 0; i < n; i++) {
			free[i] = s.charAt(i) == '0';
			cum[i + 1] = cum[i] + (free[i] ? 1 : 0);
		}
		int low = 0;
		int high = n - 1;
		while (low + 1 < high) {
			int dist = (low + high) / 2;
			boolean ok = false;
			for (int i = 0; i < n; i++) {
				if (!free[i]) {
					continue;
				}
				int left = Math.max(0, i - dist);
				int right = Math.min(n - 1, i + dist);
				int space = cum[right + 1] - cum[left];
				if (space >= c + 1) {
					ok = true;
					break;
				}
			}
			if (ok) {
				high = dist;
			} else {
				low = dist;
			}
		}
		System.out.println(high);
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

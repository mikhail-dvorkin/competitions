package codeforces.round559;
import java.io.*;
import java.util.*;

public class D {
	static final char LEFT = 'L';

	void run() {
		int n = in.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
		}
		String turns = in.next() + LEFT;
		boolean[] taken = new boolean[n];
		int[] ans = new int[n];
		int init = 0;
		for (int i = 1; i < n; i++) {
			if (y[i] < y[init] || y[i] == y[init] && x[i] < x[init]) {
				init = i;
			}
		}
		ans[0] = init;
		taken[init] = true;
		for (int i = 1; i < n; i++) {
			boolean dir = (turns.charAt(i - 1) == LEFT);
			int next = 0;
			while (taken[next]) {
				next++;
			}
			for (int j = next + 1; j < n; j++) {
				if (taken[j]) {
					continue;
				}
				int prev = ans[i - 1];
				int o = orientation(x[prev], y[prev], x[next], y[next], x[j], y[j]);
				if ((o > 0) ^ dir) {
					next = j;
				}
			}
			ans[i] = next;
			taken[next] = true;
		}
		for (int i = 0; i < n; i++) {
			out.print(ans[i] + 1);
			out.print(" ");
		}
		out.println();
	}

	static int orientation(long xA, long yA, long xB, long yB, long xC, long yC) {
		return Long.signum(
				xA * yB - xB * yA +
				xB * yC - xC * yB +
				xC * yA - xA * yC
				);
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
			//out.print("Case #" + (test + 1) + ": ");
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

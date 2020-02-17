package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class D_no {
	int n;
	int[] lMin, lMax, rMin, rMax;
	int[] ans;
	int ansPos;

	void run() {
		n = in.nextInt();
		int c = in.nextInt();
		lMin = new int[n];
		lMax = new int[n];
		rMin = new int[n];
		rMax = new int[n];
		Arrays.fill(lMin, n);
		Arrays.fill(rMin, n);
		Arrays.fill(lMax, -1);
		Arrays.fill(rMax, -1);
		for (int i = 0; i < c; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			boolean right = in.next().length() == 5;
			if (right) {
				rMin[a] = Math.min(rMin[a], b);
				rMax[a] = Math.max(rMax[a], b);
			} else {
				lMin[a] = Math.min(lMin[a], b);
				lMax[a] = Math.max(lMax[a], b);
			}
		}
		ans = new int[n];
		ansPos = 0;
		solve(0, -1, n, true);
		if (ansPos != n) {
			imp();
		}
		for (int i = 0; i < n; i++) {
			out.print(ans[i] + 1 + " ");
		}
	}

	int solve(int v, @SuppressWarnings("unused") int p, int taken, boolean right) {
		int taken2 = taken;
		if (rMin[v] != n) {
			if (rMin[v] >= taken) {
				imp();
			}
			if (rMax[v] >= taken) {
				imp();
			}
			taken2 = rMin[v];
			if (lMin[v] != n) {
				if (lMax[v] >= rMin[v]) {
					imp();
				}
			}
		}
		if (lMin[v] <= v || rMin[v] <= v) {
			imp();
		}
		if (lMax[v] >= taken2) {
			imp();
		}
		if (taken2 == v + 1) {
			if (lMin[v] != n) {
				imp();
			}
			add(v);
			if (taken == v + 1) {
				return v;
			} else {
				return solve(v + 1, v, taken, right);
			}
		} else {
			int u = solve(v + 1, v, taken2, false);
			add(v);
			if (taken == u + 1) {
				return u;
			} else {
				return solve(u + 1, v, taken, true);
			}
		}
	}

	void add(int v) {
		ans[ansPos] = v;
		ansPos++;
	}

	void imp() {
		out.println("IMPOSSIBLE");
		throw new TestSolvedException();
	}

	static final boolean stdStreams = true;
	static final String fileName = D_no.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			try {
				new D_no().run();
			} catch (TestSolvedException ignored) {
			}
		}
		br.close();
		out.close();
	}

	@SuppressWarnings("serial")
	static class TestSolvedException extends RuntimeException {
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

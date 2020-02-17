package codeforces.round459;
import java.io.*;
import java.util.*;

public class B {
	int n;
	int[][] e;
	int[][][] memo;

	void run() {
		n = in.nextInt();
		int m = in.nextInt();
		e = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(e[i], -1);
		}
		for (int i = 0; i < m; i++) {
			int from = in.nextInt() - 1;
			int to = in.nextInt() - 1;
			int color = in.next().charAt(0) - 'a';
			e[from][to] = color;
		}
		memo = new int[n][n][26];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				boolean res = dfs(i, j, 0);
				out.print(res ? 'A' : 'B');
			}
			out.println();
		}
	}

	boolean dfs(int u, int v, int minColor) {
		if (memo[u][v][minColor] != 0) {
			return memo[u][v][minColor] == 2;
		}
		boolean result = false;
		for (int w = 0; w < n; w++) {
			int color = e[u][w];
			if (color >= minColor && !dfs(v, w, color)) {
				result = true;
			}
		}
		memo[u][v][minColor] = result ? 2 : 1;
		return result;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new B().run();
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

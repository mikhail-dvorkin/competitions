package codeforces.round403;
import java.io.*;
import java.util.*;

public class A {
	ArrayList<Integer>[] nei;
	int[] color;
	int ans;

	void run() {
		int n = in.nextInt();
		init(n);
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		color = new int[n];
		dfs(0, 0);
		out.println(ans);
		for (int i = 0; i < n; i++) {
			out.print(color[i] + 1 + " ");
		}
	}

	void dfs(int v, int par) {
		int i = 0;
		for (int u : nei[v]) {
			if (u == par) {
				continue;
			}
			while (i == color[v] || i == color[par]) {
				i++;
			}
			color[u] = i++;
			ans = Math.max(ans, i);
			dfs(u, v);
		}
	}

	@SuppressWarnings("unchecked")
	void init(int n) {
		nei = new ArrayList[n];
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

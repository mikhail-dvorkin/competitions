package codeforces.round403;
import java.io.*;
import java.util.*;

public class C {
	ArrayList<Integer>[] nei;
	boolean[] mark;
	final ArrayList<Integer> path = new ArrayList<>();

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int w = in.nextInt();
		init(n);
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		mark = new boolean[n];
		dfs(0);
		for (int i = 0, j = 0; i < w; i++) {
			int k = (int) (path.size() * (i + 1L) / w);
			out.print(k - j);
			for (; j < k; j++) {
				out.print(" " + (path.get(j) + 1));
			}
			out.println();
		}
	}

	void dfs(int v) {
		path.add(v);
		mark[v] = true;
		for (int u : nei[v]) {
			if (mark[u]) {
				continue;
			}
			dfs(u);
			path.add(v);
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

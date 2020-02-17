package codeforces.round441;
import java.io.*;
import java.util.*;

public class C {
	ArrayList<Integer>[] edges;
	boolean[] mark;

	@SuppressWarnings("unchecked")
	ArrayList<Integer> solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		boolean[] mustFalse = new boolean[m];
		boolean[] mustTrue = new boolean[m];
		edges = new ArrayList[m];
		for (int i = 0; i < m; i++) {
			edges[i] = new ArrayList<>();
		}
		int[] a, b = null;
		for (int k = 0; k < n; k++) {
			a = b;
			b = new int[in.nextInt()];
			for (int i = 0; i < b.length; i++) {
				b[i] = in.nextInt() - 1;
			}
			if (a == null) {
				continue;
			}
			for (int i = 0;; i++) {
				if (i == a.length) {
					break;
				}
				if (i == b.length) {
					return null;
				}
				int c = Integer.compare(a[i], b[i]);
				if (c == 0) {
					continue;
				}
				if (c > 0) {
					mustTrue[a[i]] = true;
					mustFalse[b[i]] = true;
				} else {
					edges[b[i]].add(a[i]);
				}
				break;
			}
		}

		mark = new boolean[m];
		for (int i = 0; i < m; i++) {
			if (mustTrue[i] && !mark[i]) {
				dfs(i);
			}
		}
		ArrayList<Integer> ans = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			if (mark[i]) {
				if (mustFalse[i]) {
					return null;
				}
				ans.add(i);
			}
		}
		return ans;
	}

	void run() {
		ArrayList<Integer> ans = solve();
		if (ans == null) {
			out.println("No");
			return;
		}
		out.println("Yes");
		out.println(ans.size());
		for (int x : ans) {
			out.print((x + 1) + " ");
		}
	}

	void dfs(int v) {
		mark[v] = true;
		for (int u : edges[v]) {
			if (!mark[u]) {
				dfs(u);
			}
		}
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

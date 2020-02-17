package codeforces.round225;
import java.io.*;
import java.util.*;

public class C {
	ArrayList<Integer>[] nei;
	int[] tIn, tOut;
	boolean[] odd;
	int time;

	@SuppressWarnings("unchecked")
	void run() {
		int n = in.nextInt();
		nei = new ArrayList[n];
		tIn = new int[n];
		tOut = new int[n];
		odd = new boolean[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		int m = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 1; i < n; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			nei[u].add(v);
			nei[v].add(u);
		}
		dfs(0, -1, false);
		FenwickTree ft = new FenwickTree(2 * n);
		for (int q = 0; q < m; q++) {
			int type = in.nextInt();
			if (type == 2) {
				int x = in.nextInt() - 1;
				int v = ft.sum(tIn[x]);
				out.println(a[x] + (odd[x] ? (-v) : v));
				continue;
			}
			int x = in.nextInt() - 1;
			int val = in.nextInt();
			if (odd[x]) {
				val *= -1;
			}
			ft.add(tIn[x], val);
			ft.add(tOut[x], -val);
		}
	}

	private void dfs(int v, int par, boolean oddness) {
		odd[v] = oddness;
		tIn[v] = time++;
		for (int u : nei[v]) {
			if (u == par) {
				continue;
			}
			dfs(u, v, !oddness);
		}
		tOut[v] = time++;
	}

	static boolean stdStreams = true;
	static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
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
		new C().run();
		br.close();
		out.close();
	}

	static class FenwickTree {
		int[] t;
		int n;

		public FenwickTree(int n) {
			this.n = n;
			t = new int[n];
		}

		public void add(int i, int value) {
			for (; i < n; i += (i + 1) & -(i + 1)) {
				t[i] += value;
			}
		}

		public int sum(int i) {
			int res = 0;
			for (; i >= 0; i -= (i + 1) & -(i + 1)) {
				res += t[i];
			}
			return res;
		}
	}

	static class MyScanner {
		BufferedReader br;
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

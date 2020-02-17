package codeforces.round385;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] special = new int[k];
		for (int i = 0; i < k; i++) {
			special[i] = in.nextInt() - 1;
		}
		DisjointSetUnion dsu = new DisjointSetUnion(n);
		for (int i = 0; i < m; i++) {
			dsu.unite(in.nextInt() - 1, in.nextInt() - 1);
		}
		int[] count = new int[n];
		for (int i = 0; i < n; i++) {
			count[dsu.get(i)]++;
		}
		long ans = -m;
		int max = 0;
		for (int i : special) {
			i = dsu.get(i);
			int c = count[i];
			ans += c * (c - 1) / 2;
			max = Math.max(max, c);
			count[i] = 0;
		}
		for (int c : count) {
			ans += c * (c - 1) / 2;
			ans += max * c;
			max += c;
		}
		out.println(ans);
	}

	static class DisjointSetUnion {
		int[] p;
		Random r = new Random(0);

		public DisjointSetUnion(int n) {
			p = new int[n];
			clear();
		}

		void clear() {
			for (int i = 0; i < p.length; i++) {
				p[i] = i;
			}
		}

		int get(int v) {
			if (p[v] == v) {
				return v;
			}
			p[v] = get(p[v]);
			return p[v];
		}

		void unite(int v, int u) {
			v = get(v);
			u = get(u);
			if (r.nextBoolean()) {
				p[v] = u;
			} else {
				p[u] = v;
			}
		}
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

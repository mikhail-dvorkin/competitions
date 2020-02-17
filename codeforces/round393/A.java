package codeforces.round393;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		DisjointSetUnion dsu = new DisjointSetUnion(n);
		for (int i = 0; i < n; i++) {
			dsu.unite(i, in.nextInt() - 1);
		}
		int ansP = 0;
		for (int i = 0; i < n; i++) {
			if (dsu.get(i) == i) {
				ansP++;
			}
		}
		if (ansP == 1) {
			ansP--;
		}
		int ansB = 1;
		for (int i = 0; i < n; i++) {
			ansB ^= in.nextInt();
		}
		out.println(ansP + ansB);
	}

	static class DisjointSetUnion {
		final int[] p;
		final Random r = new Random(1);

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

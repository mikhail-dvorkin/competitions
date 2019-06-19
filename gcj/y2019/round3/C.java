package gcj.y2019.round3;
import java.io.*;
import java.util.*;

public class C {
	int hei, wid;
	boolean[][] f;
	int[][] comp;
	boolean[] compColor;
	int comps;
	char[][] ans;
	ArrayList<Edge>[] nei;
	DisjointSetUnion dsu;

	@SuppressWarnings("unchecked")
	boolean solve() {
		hei = in.nextInt();
		wid = in.nextInt();
		f = new boolean[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				f[i][j] = s.charAt(j) == 'A';
			}
		}
		comp = new int[hei][wid];
		for (int i = 0; i < hei; i++) {
			Arrays.fill(comp[i], -1);
		}
		compColor = new boolean[hei * wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (comp[i][j] >= 0) {
					continue;
				}
				dfsField(i, j);
				compColor[comps] = f[i][j];
				comps++;
			}
		}
		ans = new char[hei - 1][wid - 1];
		for (int i = 0; i < hei - 1; i++) {
			Arrays.fill(ans[i], '.');
		}
		if (comps <= 2) {
			return true;
		}
		nei = new ArrayList[comps];
		for (int i = 0; i < comps; i++) {
			nei[i] = new ArrayList<>();
		}
		dsu = new DisjointSetUnion(comps);
		for (int i = 0; i < hei - 1; i++) {
			for (int j = 0; j < wid - 1; j++) {
				if (f[i + 1][j + 1] != f[i][j] || f[i + 1][j] == f[i][j] || f[i][j + 1] == f[i][j]) {
					continue;
				}
				int a = comp[i][j];
				int b = comp[i + 1][j + 1];
				int c = comp[i + 1][j];
				int d = comp[i][j + 1];
				Edge edge = new Edge(a, b, c, d, i, j);
				if (a == b || c == d) {
					edge.apply(a == b);
					continue;
				}
				for (int k : new int[] {a, b, c, d}) {
					nei[k].add(edge);
				}
			}
		}
		return search();
	}

	void run() {
		if (!solve()) {
			out.println("IMPOSSIBLE");
			return;
		}
		out.println("POSSIBLE");
		for (int i = 0; i < hei - 1; i++) {
			out.println(new String(ans[i]));
		}
	}

	boolean search() {
		mainloop:
		while (true) {
			HashSet<Integer> set = new HashSet<>();
			for (int i = 0; i < comps; i++) {
				set.add(dsu.get(i));
			}
			if (set.size() == 2) {
				return true;
			}
			boolean improve = false;
			for (int i = 0; i < comps; i++) {
				Edge only = null;
				int count = 0;
				for (Edge e : nei[i]) {
					if (e.isApplied()) {
						continue;
					}
					int j = e.nei(i);
					if (dsu.get(i) == dsu.get(j)) {
						continue;
					}
					only = (++count == 1) ? e : null;
				}
				if (only != null) {
					only.apply(i);
					improve = true;
				}
			}
			if (improve) {
				continue;
			}
			for (int i = 0; i < comps; i++) {
				for (Edge e : nei[i]) {
					if (e.isApplied()) {
						continue;
					}
					int j = e.nei(i);
					if (dsu.get(i) == dsu.get(j)) {
						continue;
					}
					e.apply(false);
					continue mainloop;
				}
			}
			return false;
		}
	}

	class Edge {
		int a, b, c, d;
		int ii, jj;

		public Edge(int a, int b, int c, int d, int ii, int jj) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.ii = ii;
			this.jj = jj;
		}

		int nei(int x) {
			if (x == a || x == b) {
				return a ^ b ^ x;
			}
			return c ^ d ^ x;
		}

		void apply(int i) {
			apply(i == c || i == d);
		}

		void apply(boolean cd) {
			if (cd) {
				ans[ii][jj] = '/';
				dsu.unite(c, d);
			} else {
				ans[ii][jj] = '\\';
				dsu.unite(a, b);
			}
		}

		boolean isApplied() {
			return ans[ii][jj] != '.';
		}
	}

	public static final int[] DX = new int[]{1, 0, -1, 0};
	public static final int[] DY = new int[]{0, 1, 0, -1};

	void dfsField(int i, int j) {
		comp[i][j] = comps;
		for (int d = 0; d < 4; d++) {
			int ii = i + DX[d];
			int jj = j + DY[d];
			if (ii < 0 || ii >= hei || jj < 0 || jj >= wid || f[ii][jj] != f[i][j] || comp[ii][jj] >= 0) {
				continue;
			}
			dfsField(ii, jj);
		}
	}

	static Random r = new Random(566);

	private static class DisjointSetUnion {
		final int[] p;

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
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
			new C().run();
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

package codeforces.round286;
import java.io.*;
import java.util.*;

public class D {
	@SuppressWarnings("unchecked")
	void run() {
		int n = in.nextInt();
		int T = 200;
		int m = in.nextInt();
		int[] a = new int[m];
		int[] b = new int[m];
		int[] c = new int[m];
		ArrayList<Integer>[] byColor = new ArrayList[m];
		for (int i = 0; i < m; i++) {
			byColor[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt() - 1;
			b[i] = in.nextInt() - 1;
			c[i] = in.nextInt() - 1;
			byColor[c[i]].add(i);
		}
		int queries = in.nextInt();
		int popular = 0;
		nei = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		comp = new int[n];
		Arrays.fill(comp, -1);
		for (int k = 0; k < m; k++) {
			if (byColor[k].size() >= T) {
				popular++;
			}
		}
		int[][] popColor = new int[n][popular];
		Map<Long, Integer> count = new TreeMap<>();
		for (int k = 0, p = 0; k < m; k++) {
			if (byColor[k].size() < T) {
				TreeSet<Integer> actual = new TreeSet<>();
				for (int i : byColor[k]) {
					nei[a[i]].add(b[i]);
					nei[b[i]].add(a[i]);
					actual.add(a[i]);
					actual.add(b[i]);
				}
				comps = 0;
				for (int i : actual) {
					if (comp[i] >= 0) {
						continue;
					}
					dfs(i);
					comps++;
				}
				for (int i : actual) {
					for (int j : actual) {
						if (i >= j || comp[i] != comp[j]) {
							continue;
						}
						long enc = encode(i, j);
						count.put(enc, count.getOrDefault(enc, 0) + 1);
					}
				}
				for (int i : actual) {
					nei[i].clear();
					comp[i] = -1;
				}
			} else {
				for (int i : byColor[k]) {
					nei[a[i]].add(b[i]);
					nei[b[i]].add(a[i]);
				}
				comps = 0;
				for (int i = 0; i < n; i++) {
					if (comp[i] >= 0) {
						continue;
					}
					dfs(i);
					comps++;
				}
				for (int i = 0; i < n; i++) {
					popColor[i][p] = comp[i];
				}
				for (int i = 0; i < n; i++) {
					nei[i].clear();
				}
				Arrays.fill(comp, -1);
				p++;
			}
		}
		for (int q = 0; q < queries; q++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			if (x >= y) {
				int t = x; x = y; y = t;
			}
			long enc = encode(x, y);
			int res = count.getOrDefault(enc, 0);
			int[] cx = popColor[x];
			int[] cy = popColor[y];
			for (int i = 0; i < popular; i++) {
				if (cx[i] == cy[i]) {
					res++;
				}
			}
			out.println(res);
		}
	}

	long encode(int a, int b) {
		return b | ((long) a << 32);
	}

	ArrayList<Integer>[] nei;
	int[] comp;
	int comps;

	void dfs(int v) {
		comp[v] = comps;
		for (int u : nei[v]) {
			if (comp[u] >= 0) {
				continue;
			}
			dfs(u);
		}
	}

	static final boolean stdStreams = true;
	static final String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

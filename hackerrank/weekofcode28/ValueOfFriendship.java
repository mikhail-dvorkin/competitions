package hackerrank.weekofcode28;
import java.io.*;
import java.util.*;

public class ValueOfFriendship {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		DisjointSetUnion dsu = new DisjointSetUnion(n);
		int[] a = new int[m];
		int[] b = new int[m];
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt() - 1; 
			b[i] = in.nextInt() - 1;
			dsu.unite(a[i], b[i]);
		}
		long[] size = new long[n];
		for (int i = 0; i < n; i++) {
			size[dsu.get(i)] += m + 1;
		}
		for (int i = 0; i < m; i++) {
			size[dsu.get(a[i])]++;
		}
		Arrays.sort(size);
		long ans = 0;
		long sum = 0;
		for (int j = n - 1; j >= 0; j--) {
			long s = size[j];
			int v = (int) (s / (m + 1));
			if (v == 0) {
				continue;
			}
			for (int i = 2; i <= v; i++) {
				long cur = i * (i - 1L);
				ans += sum + cur;
			}
			sum += v * (v - 1L);
		}
		for (int j = n - 1; j >= 0; j--) {
			long s = size[j];
			int v = (int) (s / (m + 1));
			if (v == 0) {
				continue;
			}
			int e = (int) (s % (m + 1));
			ans += sum * (e - v + 1);
		}
		out.println(ans);
	}

	static class DisjointSetUnion {
		int[] p;
		Random r = new Random(1);
		
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
		String fileName = ValueOfFriendship.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new ValueOfFriendship().run();
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

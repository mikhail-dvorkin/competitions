package hackerrank.hourrank18;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int q = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		int[] low = new int[q];
		int[] high = new int[q];
		Map<Integer, Long> queries = new TreeMap<>();
		for (int i = 0; i < q; i++) {
			low[i] = in.nextInt();
			high[i] = in.nextInt() + 1;
			queries.put(low[i], -1L);
			queries.put(high[i], -1L);
		}
		SparseTable table = new SparseTable(a);
		for (int query : queries.keySet()) {
			long res = 0;
			for (int x = 0; x < n; x++) {
				int left = x + 1;
				int right = n + 1;
				while (left + 1 < right) {
					int y = (left + right) / 2;
					if (table.diff(x, y) < query) {
						left = y;
					} else {
						right = y;
					}
				}
				res += left - x;
			}
			queries.put(query, res);
		}
		for (int i = 0; i < q; i++) {
			System.out.println(queries.get(high[i]) - queries.get(low[i]));
		}
	}
	
	public static class SparseTable {
		int[][] min;
		int[][] max;
		
		public SparseTable(int[] a) {
			int n = a.length;
			int t = 1;
			while ((1 << t) <= n) {
				t++;
			}
			min = new int[t][n];
			max = new int[t][n];
			System.arraycopy(a, 0, min[0], 0, n);
			System.arraycopy(a, 0, max[0], 0, n);
			for (int j = 1; j < t; j++) {
				for (int i = 0; i + (1 << j) <= n; i++) {
					min[j][i] = Math.min(min[j - 1][i], min[j - 1][i + (1 << (j - 1))]);
					max[j][i] = Math.max(max[j - 1][i], max[j - 1][i + (1 << (j - 1))]);
				}
			}
		}
		
		public int diff(int from, int to) {
			int j = Integer.SIZE - 1 - Integer.numberOfLeadingZeros(to - from);
			int mn = Math.min(min[j][from], min[j][to - (1 << j)]);
			int mx = Math.max(max[j][from], max[j][to - (1 << j)]);
			return mx - mn;
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
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

package codeforces.round441;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		SparseTable table = new SparseTable(a);
		long ans = n * (n + 1L) / 2;
		for (int i = 0; i < n; i++) {
			int low = -1;
			int high = i;
			while (low + 1 < high) {
				int m = (low + high) / 2;
				int max = table.max(m, i);
				int or = table.or(m, i);
				if (max >= a[i] || ((or & ~a[i]) != 0)) {
					low = m;
				} else {
					high = m;
				}
			}
			int left = high;
			low = i;
			high = n;
			while (low + 1 < high) {
				int m = (low + high) / 2;
				int max = table.max(i, m + 1);
				int or = table.or(i, m + 1);
				if (max > a[i] || ((or & ~a[i]) != 0)) {
					high = m;
				} else {
					low = m;
				}
			}
			int right = low;
			ans -= (i - left + 1L) * (right - i + 1);
		}
		out.println(ans);
	}
	
	public static class SparseTable {
		int[][] or;
		int[][] max;
		
		public SparseTable(int[] a) {
			int n = a.length;
			int t = 1;
			while ((1 << t) <= n) {
				t++;
			}
			or = new int[t][n];
			max = new int[t][n];
			System.arraycopy(a, 0, or[0], 0, n);
			System.arraycopy(a, 0, max[0], 0, n);
			for (int j = 1; j < t; j++) {
				for (int i = 0; i + (1 << j) <= n; i++) {
					or[j][i] = or[j - 1][i] | or[j - 1][i + (1 << (j - 1))];
					max[j][i] = Math.max(max[j - 1][i], max[j - 1][i + (1 << (j - 1))]);
				}
			}
		}
		
		public int or(int from, int to) {
			if (from >= to) {
				return 0;
			}
			int j = Integer.SIZE - 1 - Integer.numberOfLeadingZeros(to - from);
			return or[j][from] | or[j][to - (1 << j)];
		}
		
		public int max(int from, int to) {
			if (from >= to) {
				return -1;
			}
			int j = Integer.SIZE - 1 - Integer.numberOfLeadingZeros(to - from);
			return Math.max(max[j][from], max[j][to - (1 << j)]);
		}
	}


	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D().run();
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

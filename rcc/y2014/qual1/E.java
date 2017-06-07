package rcc.y2014.qual1;
import java.io.*;
import java.util.*;

public class E {
	static final int infty = Integer.MAX_VALUE / 3;
	
	void solve() {
		int n = in.nextInt();
		int m = in.nextInt();
		int s = n + m;
		int[] a = new int[s];
		for (int i = 0; i < s; i++) {
			a[i] = in.nextInt();
		}
		int[][] e = new int[n][n * m];
		for (int i = 0; i < n; i++) {
			Arrays.fill(e[i], infty);
		}
		int p = 0;
		for (int x = 0; x < s; x++) {
			int v = a[x];
			if (v == 0) {
				continue;
			}
			if (v > n) {
				v = n;
			}
			for (int y = 0, q = 0; y < s; y++) {
				if (a[y] > 0) {
					continue;
				}
				for (int i = 0; i < v; i++) {
					int dist = Math.abs(x - y);
					dist = Math.min(dist, s - dist);
					e[q][p + i] = dist;
				}
				q++;
			}
			p += v;
		}
		System.out.println(hungarian(e));
	}
	
	void run() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			solve();
		}
	}

	public static int hungarian(int[][] e) {
		int[] ans = new int[e.length];
		Arrays.fill(ans, -1);
		if (e.length == 0 || e[0].length == 0) {
			return -1;
		}
		boolean swap = false;
		if (e.length > e[0].length) {
			swap = true;
			int[][] f = new int[e[0].length][e.length];
			for (int i = 0; i < e.length; i++) {
				for (int j = 0; j < e[0].length; j++) {
					f[j][i] = e[i][j];
				}
			}
			e = f;
		}
		int n1 = e.length;
		int n2 = e[0].length;
		int[] u = new int[n1 + 1];
		int[] v = new int[n2 + 1];
		int[] p = new int[n2 + 1];
		int[] way = new int[n2 + 1];
		for (int i = 1; i <= n1; i++) {
			p[0] = i;
			int j0 = 0;
			int[] minv = new int[n2 + 1]; 
			Arrays.fill(minv, infty);
			boolean[] used = new boolean[n2 + 1];
			do {
				used[j0] = true;
				int i0 = p[j0], j1 = 0;
				int delta = infty;
				for (int j = 1; j <= n2; j++) {
					if (!used[j]) {
						int cur = e[i0 - 1][j - 1] - u[i0] - v[j];
						if (cur < minv[j]) {
							minv[j] = cur;
							way[j] = j0;
						}
						if (minv[j] < delta) {
							delta = minv[j];
							j1 = j;
						}
					}
				}
				for (int j = 0; j <= n2; j++) {
					if (used[j]) {
						u[p[j]] += delta;
						v[j] -= delta;
					} else {
						minv[j] -= delta;
					}
				}
				j0 = j1;
			} while (p[j0] != 0);
			do {
				int j1 = way[j0];
				p[j0] = p[j1];
				j0 = j1;
			} while (j0 > 0);
		}
		int sum = 0;
		for (int j = 1; j <= n2; j++) {
			if (p[j] > 0) {
				sum += e[p[j] - 1][j - 1];
				if (swap) {
					ans[j - 1] = p[j] - 1;
				} else {
					ans[p[j] - 1] = j - 1;
				}
			}
		}
		return sum;
	}

	static boolean stdStreams = true;
	static String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new E().run();
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

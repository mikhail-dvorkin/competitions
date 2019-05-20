package gcj.y2019.round2;
import java.io.*;
import java.util.*;

public class D_medium {
	static final int M = 1000000007;
	
	int n;
	int[][] a;
	boolean[][] e;
	boolean[] inf;
	boolean[] mark;
	int[] gives;
	
	void run() {
		n = in.nextInt();
		a = new int[n][2];
		e = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			a[i][0] = in.nextInt() - 1;
			a[i][1] = in.nextInt() - 1;
			e[i][i] = true;
			e[i][a[i][0]] = true;
			e[i][a[i][1]] = true;
		}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					e[i][j] |= e[i][k] && e[k][j];
				}				
			}
		}
		inf = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int t = 0; t < 2; t++) {
				int j = a[i][t];
				int k = a[i][1 - t];
				if (e[j][i] && e[k][0]) {
					inf[i] = true;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				inf[i] |= e[i][j] && inf[j];
			}
		}
		mark = new boolean[n];
		gives = new int[n];
		gives[0] = 1;
		mark[0] = true;
		for (int i = 1; i < n; i++) {
			dfs(i);
		}
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			v[i] = in.nextInt();
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (v[i] == 0) {
				continue;
			}
			if (inf[i]) {
				out.println("UNBOUNDED");
				return;
			}
			ans = (int) ((ans + v[i] * (long) gives[i]) % M);
		}
		out.println(ans);
	}

	void dfs(int v) {
		if (mark[v] || inf[v]) {
			return;
		}
		mark[v] = true;
		int res = 0;
		for (int t = 0; t < 2; t++) {
			int u = a[v][t];
			dfs(u);
			res += gives[u];
		}
		gives[v] = res % M;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D_medium.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D_medium().run();
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

package gcj.y2018.round1b;
import java.io.*;
import java.util.*;

public class A_medium {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] c = new int[n];
		for (int i = 0; i < m; i++) {
			c[i] = in.nextInt();
		}
		Arrays.sort(c);
		int[][] a = new int[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			Arrays.fill(a[i], -1);
		}
		a[0][0] = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= n; j++) {
				if (a[i][j] < 0) {
					continue;
				}
				for (int k = c[i]; j + k <= n; k++) {
					int cur = a[i][j] + round(k, n);
					a[i + 1][j + k] = Math.max(a[i + 1][j + k], cur);
				}
			}
		}
		out.println(a[n][n]);
	}
	
	static int round(int k, int n) {
		k *= 100;
		int res = k / n;
		if ((k % n) * 2 >= n) {
			res++;
		}
		return res;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = A_medium.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new A_medium().run();
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

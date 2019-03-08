package gcj.y2018.round2;
import java.io.*;
import java.util.*;

public class B {
	static final int MAX = 500;
	static int[][] ans;
	
	static {
		int[][] a = new int[MAX + 1][MAX + 1];
		int[][] b = new int[MAX + 1][MAX + 1];
		for (int i = 0; i <= MAX; i++) {
			Arrays.fill(a[i], -1);
		}
		a[0][0] = 0;
		ans = new int[MAX + 1][MAX + 1];
		for (int k = 0; k <= MAX; k++) {
			for (int i = 0; i <= MAX; i++) {
				Arrays.fill(b[i], -1);
			}
			for (int i = 0; i <= MAX; i++) {
				for (int j = 0; j <= MAX; j++) {
					int count = a[i][j];
					if (count == -1) {
						continue;
					}
					ans[i][j] = Math.max(ans[i][j], a[i][j]);
					int ii = i;
					int jj = j;
					for (int x = 0;; x++) {
						b[ii][jj] = Math.max(b[ii][jj], count + x);
						ii += k;
						jj += x;
						if (ii > MAX || jj > MAX) {
							break;
						}
					}
				}
			}
			int[][] t = a; a = b; b = t;
		}
		for (int i = 0; i <= MAX; i++) {
			for (int j = 0; j <= MAX; j++) {
				if (i > 0) {
					ans[i][j] = Math.max(ans[i][j], ans[i - 1][j]);
				}
				if (j > 0) {
					ans[i][j] = Math.max(ans[i][j], ans[i][j - 1]);
				}
			}
		}
		for (int i = 0; i <= MAX; i++) {
			for (int j = 0; j <= MAX; j++) {
				if (ans[i][j] != ans[j][i]) {
					throw new RuntimeException();
				}
			}
		}
	}
	
	void run() {
		int a = in.nextInt();
		int b = in.nextInt();
		out.println(ans[a][b] - 1);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new B().run();
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

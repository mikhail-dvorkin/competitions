package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class G_12 {
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		int[] p = new int[n];
//		int[] p1 = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = in.nextInt() - 1;
//			p1[p[i]] = i;
		}
		
//		double[][] a = new double[n][n];
//		double[][] b = new double[n][n];
//		for (int x = 0; x < n; x++) {
//			for (int y = x + 1; y < n; y++) {
//				for (int i = 0; i < n; i++) {
//					Arrays.fill(a[i], 0);
//				}
//				a[p1[x]][p1[y]] = 1;
//				for (int t = 0; t < k; t++) {
//					for (int low = 0; low < n; low++) {
//					}
//				}
//			}
//		}
//		int pairs = 0;
//		int[] pairA = new int[n * (n - 1) / 2];
//		int[] pairB = new int[n * (n - 1) / 2];
//		int[][] pairNum = new int[n][n];
//		double[] b = new double[n * (n - 1) / 2];
//		for (int i = 0; i < n; i++) {
//			for (int j = i + 1; j < n; j++) {
//				pairA[pairs] = i;
//				pairB[pairs] = j;
//				pairNum[i][j] = pairs;
//				pairs++;
//			}
//		}
		int pairs = n * (n + 1) / 2;
		double[][] a = new double[n][n];
		double[][] b = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = i > j ? 1 : 0;
			}
		}
		for (int t = 0; t < k; t++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i == j) {
						continue;
					}
					b[i][j] = 0;
					for (int lo = 0; lo < n; lo++) {
						for (int hi = lo; hi < n; hi++) {
							int ii = i;
							int jj = j;
							if (lo <= i && i <= hi) {
								ii = lo + hi - i;
							}
							if (lo <= j && j <= hi) {
								jj = lo + hi - j;
							}
							b[i][j] += a[ii][jj] / pairs;
						}
					}
				}
			}
			double[][] c = a;
			a = b;
			b = c;
		}
		double ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				double prob = a[i][j];
				if (p[i] < p[j]) {
					ans += prob;
				} else {
					ans += 1 - prob;
				}
			}
		}
		out.println(ans);
	}

	static boolean stdStreams = true;
	static String fileName = G_12.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new G_12().run();
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

package yandex.y2018.round2;
import java.io.*;
import java.util.*;

public class D_wrong {
	static final int MOD = 998_244_353;
	
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		
		double[][] asc = new double[n + 1][n + 1];
		asc[1][0] = 1;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				asc[i + 1][j + 1] += asc[i][j] * (1 + i - 1 - j);
				asc[i + 1][j] += asc[i][j] * (1 + j);
			}
			for (int j = 0; j <= i; j++) {
				asc[i + 1][j] /= (i + 1);
			}
		}
		double[][] a = new double[k + 1][n];
		int f = 1;
		for (int i = 2; i <= n; i++) {
			f *= i;
		}
		a[0][n - 1] = f;
		for (int kk = 0; kk < k; kk++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j <= i; j++) {
					a[kk + 1][j] += a[kk][i] * asc[i + 1][j];
				}
			}
		}
		out.println(a[k][0]);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D_wrong.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D_wrong().run();
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

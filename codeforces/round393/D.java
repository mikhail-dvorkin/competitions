package codeforces.round393;
import java.io.*;
import java.util.*;

public class D {
	int A = 26;
	int P = 1_000_000_007;
	
	void run() {
		int n = in.nextInt();
		String s = in.next();
		int[][] a = new int[n + 1][A];
		int[] sum = new int[n + 1];
		int[][] cnk = new int[n][n];
		sum[0] = 1;
		for (int i = 0; i < n; i++) {
			int c = s.charAt(i) - 'a';
			for (int j = 1; j <= n; j++) {
				sum[j] = (sum[j] + P - a[j][c]) % P;
				a[j][c] = (sum[j - 1] + P - a[j - 1][c]) % P;
				sum[j] = (sum[j] + a[j][c]) % P;
			}
			cnk[i][i] = cnk[i][0] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % P;
			}
		}
			
		int ans = 0;
		for (int j = 1; j <= n; j++) {
			ans = (int) ((ans + (long) sum[j] * cnk[n - 1][n - j]) % P);
		}
		out.println(ans);
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

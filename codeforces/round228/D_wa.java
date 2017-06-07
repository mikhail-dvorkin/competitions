package codeforces.round228;
import java.io.*;
import java.util.*;

public class D_wa {
	final long M = 1000000007;
	
	void run() {
		out.println(solve(in.nextInt()));
	}

	final int m = 32;
	final long[] two = new long[m];
	final long[][] cnk = new long[m][m];
	final long[][] stirling = new long[m][m];
	final long[] bell = new long[m];
	
	{
		for (int i = 0; i < m; i++) {
			cnk[i][0] = cnk[i][i] = 1;
			stirling[i][i] = 1;
			bell[i] = 1;
			two[i] = (i == 0) ? 1 : ((two[i - 1] * 2) % M);
			for (int j = 1; j < i; j++) {
				cnk[i][j] = (cnk[i - 1][j - 1] + cnk[i - 1][j]) % M;
				stirling[i][j] = (stirling[i - 1][j - 1] + j * stirling[i - 1][j]) % M;
				bell[i] = (bell[i] + stirling[i][j]) % M;
			}
		}
	}
	
	long solve(long n) {
		n++;
		long ans = 0; //bell[Integer.bitCount(n)];
		for (int i = 0; i < m; i++) {
			if (((n >> i) & 1) == 0) {
				continue;
			}
			int upper = Long.bitCount(n >> i) - 1;
			for (int lower = 0; lower <= i; lower++) {
				ans = (ans + cnk[i][lower] * bell[upper + lower]) % M;
			}
		}
		return ans;
	}
	
	static boolean stdStreams = true;
	static String fileName = D_wa.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new D_wa().run();
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

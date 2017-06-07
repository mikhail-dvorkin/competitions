package hackerrank.weekofcode28;
import java.io.*;
import java.util.*;

public class DefiniteRandomWalks_slow {
	static final int P = 998244353;

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		int[] f = new int[n];
		for (int i = 0; i < n; i++) {
			f[i] = in.nextInt() - 1;
		}
		int[] p = new int[m];
		for (int i = 0; i < m; i++) {
			p[i] = in.nextInt();
		}
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			int ii = i;
			for (int j = 0; j < m; j++) {
				a[ii][i] = (a[ii][i] + p[j]) % P;
				ii = f[ii];
			}
		}
		a = matrixPower(a, k);
		int[][] init = new int[n][];
		Arrays.fill(init, new int[]{modInverse(n)});
		a = matrixMultiply(a, init);
		for (int[] row : a) {
			out.println(row[0]);
		}
	}
	
	public static int[][] matrixPower(int[][] a, long power) {
		if (power == 0) {
			int[][] res = new int[a.length][a.length];
			for (int i = 0; i < a.length; i++) {
				res[i][i] = 1;
			}
			return res;
		}
		if (power == 1) {
			return a;
		}
		if (power % 2 == 0) {
			int[][] b = matrixPower(a, power / 2);
			return matrixMultiply(b, b);
		}
		int[][] b = matrixPower(a, power - 1);
		return matrixMultiply(a, b);
	}

	public static int[][] matrixMultiply(int[][] a, int[][] b) {
		int[][] c = new int[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % P);
				}
			}
		}
		return c;
	}
	
	public static int gcdExtended(int a, int b, int[] xy) {
		if (a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		int d = gcdExtended(b % a, a, xy);
		int t = xy[0];
		xy[0] = xy[1] - (b / a) * xy[0];
		xy[1] = t;
		return d;
	}
	
	public static int modInverse(int x) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, P, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + P);
		}
		int result = xy[0] % P;
		if (result < 0) {
			result += P;
		}
		return result;
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = DefiniteRandomWalks_slow.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new DefiniteRandomWalks_slow().run();
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

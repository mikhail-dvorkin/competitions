package yandex.y2016.round2;
import java.io.*;
import java.util.*;

public class D {
	static final int M = 1000000007;
	
	void run() {
		int a = in.nextInt();
		int b = in.nextInt();
		long c = in.nextLong();
		int n = Math.max(a, b);
		int[][] m = new int[n][n];
		m[0][a - 1]++;
		m[0][b - 1]++;
		for (int i = 1; i < n; i++) {
			m[i][i - 1] = 1;
		}
		out.println(matrixPower(m, c)[0][0]);
	}

	int[][] matrixPower(int[][] a, long power) {
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

	int[][] matrixMultiply(int[][] a, int[][] b) {
		int[][] c = new int[a.length][b[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] = (int) ((c[i][j] + (long) a[i][k] * b[k][j]) % M);
				}
			}
		}
		return c;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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

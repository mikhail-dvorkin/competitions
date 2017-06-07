package yandex.y2016.qual;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		double[][] p = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				p[i][j] = in.nextDouble();
			}
		}
		double[] a = new double[1 << n];
		a[0] = 1;
		for (int mask = 1; mask < (1 << n); mask++) {
			for (int i = 0; i < n; i++) {
				if (((mask >> i) & 1) == 0) {
					continue;
				}
				double pi = a[mask ^ (1 << i)];
				for (int j = 0; j < n; j++) {
					if (i == j || ((mask >> j) & 1) == 0) {
						continue;
					}
					pi *= p[i][j];
				}
				a[mask] += pi;
			}
		}
		out.println(a[(1 << n) - 1]);
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
			new C().run();
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

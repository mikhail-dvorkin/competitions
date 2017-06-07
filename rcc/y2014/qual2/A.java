package rcc.y2014.qual2;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int p = in.nextInt();
		int q = in.nextInt();
		int t = in.nextInt();
		int pp = t / p;
		int qq = t / q;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i <= n; i++) {
			if (i * p > t) {
				break;
			}
			int j = (t - i * p) / q;
			if (j > m) {
				j = m;
			}
			int cur = 1 + (n - i + pp - 1) / pp + (m - j + qq - 1) / qq;
			ans = Math.min(ans, cur);
		}
		out.println(ans);
	}

	static boolean stdStreams = true;
	static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new A().run();
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

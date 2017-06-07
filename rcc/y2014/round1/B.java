package rcc.y2014.round1;
import java.io.*;
import java.util.*;

public class B {
	String imp = "IMPOSSIBLE";
	
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int d = in.nextInt();
		if (m > d || m >= n || d >= n) {
			out.println(imp);
			return;
		}
		for (int i = 0; i < n; i++) {
			if (i >= m) {
				out.println(0);
				continue;
			}
			if (i == m - 1) {
				out.print((d - m + 1));
				for (int j = 0; j < d - m + 1; j++) {
					out.print(" " + (i + 2 + j));
				}
				out.println();
				continue;
			}
			out.println("1 " + (i + 2));
		}
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

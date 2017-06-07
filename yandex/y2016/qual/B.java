package yandex.y2016.qual;
import java.io.*;
import java.util.*;

public class B {
	static final double PRECISION = 0.001;

	void run() {
		long v = in.nextLong();
		long tCommon = readTime();
		int n = in.nextInt();
		long dBest = Long.MAX_VALUE;
		int iBest = -1;
		for (int i = 0; i < n; i++) {
			long x = in.nextLong();
			long t = readTime();
			long d = 1000 * x + v * (tCommon + t);
			if (d < dBest) {
				dBest = d;
				iBest = i;
			}
		}
		out.println(dBest * PRECISION + " " + (iBest + 1));
	}

	long readTime() {
		return Math.round(in.nextDouble() / PRECISION);
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

package yandex.y2014.round2;

import java.io.*;
import java.util.*;

public class D {
	void run() {
		int k = in.nextInt();
		int n = 1;
		while (n * (n - 1) / 2 < k) {
			n++;
		}
		out.println(n);
		int f = k - (n - 1) * (n - 2) / 2;
		out.print(f + 1);
		for (int i = n - 1; i >= 0; i--) {
			if (i != f) {
				out.print(" " + (i + 1));
			}
		}
		out.println();
	}

	static boolean stdStreams = false;
	static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = "input.txt";
	static String outputFileName = "output.txt";
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
		new D().run();
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

package yandex.y2014.round2;

import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		int d = (k + 1) / 2;
		long ans = 0;
		long bestInc = 0;
		for (int i = 0; i < n; i++) {
			int c = in.nextInt();
			int m = c + 2 - 2 * d;
			int black = (m + k - 1) / k;
			ans += black;
			if (k % 2 == 1) {
				int black2 = (m + k) / k;
				if (black2 > black) {
					bestInc = 1;
				}
			}
		}
		out.println(ans + bestInc);
	}

	static boolean stdStreams = false;
	static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new A().run();
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

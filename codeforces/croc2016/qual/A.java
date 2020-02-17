package codeforces.croc2016.qual;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		if (n > a * b) {
			System.out.println(-1);
			return;
		}
		int[][] f = new int[a][b];
		int k = n;
		loop:
		for (int i = 0; i < a; i++) {
			int jStart = 0;
			int jShift = 1;
			if (i % 2 == 1) {
				jStart = b - 1;
				jShift = -1;
			}
			for (int j = jStart; j >= 0 && j < b; j += jShift) {
				f[i][j] = k;
				k--;
				if (k == 0) {
					break loop;
				}
			}
		}
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				System.out.print(f[i][j]);
				if (j < b - 1) {
					System.out.print(" ");
				} else {
					System.out.println();
				}
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";

		Locale.setDefault(Locale.US);
		BufferedReader br;
		//noinspection ConstantConditions
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
			new A().run();
		}
		br.close();
		out.close();
	}

	static class MyScanner {
		final BufferedReader br;
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

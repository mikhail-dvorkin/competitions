package yandex.y2018.round2;
import java.io.*;
import java.util.*;

public class B {
	static final int MULT = 1_000_000_000;

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = new int[n];
		int aMax = -1;
		int aLeft = -1, aRight = -1;
		int aSum = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			aSum += a[i];
			if (a[i] > aMax) {
				aMax = a[i];
				aLeft = aRight = i;
			} else if (a[i] == aMax) {
				aRight = i;
			}
		}
		int[] b = new int[m];
		int bMax = -1;
		int bSum = 0;
		for (int i = 0; i < m; i++) {
			b[i] = in.nextInt();
			bSum += b[i];
			bMax = Math.max(bMax, b[i]);
		}
		int aAns = aSum + (m - 1) * aMax;
		int bAns = bSum + aLeft * b[0] + (n - 1 - aRight) * b[m - 1] + (aRight - aLeft) * bMax;
		out.println(aAns * 1L * MULT + bAns);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

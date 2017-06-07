package rcc.y2014.round1;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int jury = in.nextInt();
		int tasks = in.nextInt();
		int t = in.nextInt();
		int tSwap = in.nextInt();
		boolean[][] want = new boolean[tasks][jury];
		for (int i = 0; i < jury; i++) {
			String s = in.next();
			for (int j = 0; j < tasks; j++) {
				want[j][i] = (s.charAt(j) == '1');
			}
		}
		int[][] a = new int[tasks][jury];
		int inf = Integer.MAX_VALUE / 3;
		for (int i = 0; i < jury; i++) {
			if (want[0][i]) {
				a[0][i] = 0;
			} else {
				a[0][i] = inf;
			}
		}
		int b = 0;
		for (int j = 1; j < tasks; j++) {
			int bNew = inf;
			for (int i = 0; i < jury; i++) {
				if (!want[j][i]) {
					a[j][i] = inf;
					continue;
				}
				a[j][i] = Math.min(a[j - 1][i], b + 1);
				bNew = Math.min(bNew, a[j][i]);
			}
			b = bNew;
		}
		out.println(b * tSwap + t * tasks);
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

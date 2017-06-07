package codeforces.round283;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		char[][] c = new char[n][];
		for (int i = 0; i < n; i++) {
			c[i] = in.next().toCharArray();
		}
		int[] group = new int[n];
		int ans = 0;
		for (int j = 0; j < m; j++) {
			boolean take = true;
			for (int i = 1; i < n; i++) {
				if (group[i - 1] == group[i]) {
					if (c[i - 1][j] > c[i][j]) {
						take = false;
					}
				}
			}
			if (!take) {
				ans++;
				continue;
			}
			int[] newGroup = new int[n];
			int g = -1;
			for (int i = 0; i < n; i++) {
				if (i == 0 || group[i - 1] != group[i] || c[i - 1][j] < c[i][j]) {
					g++;
				}
				newGroup[i] = g;
			}
			group = newGroup;
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
		int tests = 1;//in.nextInt();
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

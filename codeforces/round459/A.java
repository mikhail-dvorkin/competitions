package codeforces.round459;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		String s = in.next();
		int n = s.length();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int min = 0;
			int max = 0;
			for (int j = i; j < n; j++) {
				if (s.charAt(j) == ')') {
					if (max == 0) {
						break;
					}
					min -= 1;
					max -= 1;
				} else if (s.charAt(j) == '(') {
					min += 1;
					max += 1;
				} else {
					min -= 1;
					max += 1;
				}
				if (min == -1) {
					min = 1;
				}
				if (min == 0) {
					ans++;
				}
			}
		}
		out.println(ans);
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

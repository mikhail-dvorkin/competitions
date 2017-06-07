package codeforces.rockethon2014;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		String s = in.next();
		char prev = 0;
		int len = 0;
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == prev) {
				len++;
			} else {
				if (len > 0 && len % 2 == 0) {
					ans++;
				}
				prev = s.charAt(i);
				len = 1;
			}
		}
		if (len > 0 && len % 2 == 0) {
			ans++;
		}
		System.out.println(ans);
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

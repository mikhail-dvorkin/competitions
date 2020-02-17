package codeforces.round539;
import java.io.*;
import java.util.*;

public class B {
	String solve(String s) {
		int n = s.length();
		for (int i = 1; i < n; i++) {
			String t = s.substring(i) + s.substring(0, i);
			if (!t.equals(s) && isPalindrome(t)) {
				return "1";
			}
		}
		for (int i = 2; 2 * i < n; i++) {
			if (s.charAt(0) != s.charAt(i - 1)) {
				return "2";
			}
		}
		return "Impossible";
	}

	void run() {
		String s = in.next();
		out.println(solve(s));
	}

	public static boolean isPalindrome(String s) {
		int len = s.length();
		for (int i = 0; i < len / 2; i++)
			if (s.charAt(i) != s.charAt(len - 1 - i))
				return false;
		return true;
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
			//out.print("Case #" + (test + 1) + ": ");
			new B().run();
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

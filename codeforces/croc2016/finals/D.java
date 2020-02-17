package codeforces.croc2016.finals;
import java.io.*;
import java.util.*;

public class D {
	static final int START = 1989;

	void run() {
		String s = in.next().substring(4);
		System.out.println(solve(s));
	}

	static long solve(String s) {
		long n = Long.parseLong(s);
		int len = s.length();
		long x = START;
		long t = 1;
		for (int i = 1; i < len; i++) {
			t *= 10;
			x += t;
		}
		t *= 10;
		long y = (x - x % t) + n;
		while (y < x) {
			y += t;
		}
		return y;
	}

	static void stress() {
		Set<String> set = new TreeSet<>();
		for (int y = START;; y++) {
			String s = "" + y;
			for (int i = 1; i <= s.length(); i++) {
				String t = s.substring(s.length() - i);
				if (set.contains(t)) {
					continue;
				}
				set.add(t);
				long ySolved = solve(t);
				System.out.println(y + " " + t + " " + ySolved);
				if (y != ySolved) {
					System.exit(1);
				}
				break;
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
//		stress();
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new D().run();
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

package yandex.y2017.round3;
import java.io.*;
import java.util.*;

public class B {
	static String solve(String s, int x, int y) {
		int n = s.length();
		boolean less = false;
		int[] ans = new int[n];
		loop:
		for (int i = 0; i < n; i++) {
			int d = s.charAt(i) - '0';
			if (less || d >= x) {
				ans[i] = less || d >= y ? y : x;
				less |= d > ans[i];
				continue;
			}
			less = true;
			while (i > 0) {
				i--;
				if (ans[i] == y) {
					ans[i] = x;
					continue loop;
				}
			}
			ans[i] = 0;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			if (ans[i] > 0 || sb.length() > 0) {
				sb.append(ans[i]);
			}
		}
		if (sb.length() == 0) {
			sb.append(-1);
		}
		return sb.toString();
	}
	
	void run() {
		String s = in.next();
		int x = in.nextInt();
		int y = in.nextInt();
		out.println(solve(s, x, y));
	}

	static String solveDumb(String s, int x, int y) {
		for (int i = Integer.parseInt(s); i >= 1; i--) {
			boolean ok = true;
			String t = "" + i;
			for (int j = 0; j < t.length(); j++) {
				int d = t.charAt(j) - '0';
				if (d != x && d != y) {
					ok = false;
					break;
				}
			}
			if (ok) {
				return "" + i;
			}
		}
		return "-1";
	}

	static void stressTest() {
		for (int s = 1; s < 2500; s++) {
			for (int x = 0; x < 9; x++) {
				for (int y = x + 1; y <= 9; y++) {
					String a1 = solve("" + s, x, y);
					String a2 = solveDumb("" + s, x, y);
					if (!a1.equals(a2)) {
						System.out.println(s + " " + x + " " + y + " " + a1 + " " + a2);
						System.exit(1);
					}
				}
			}
		}
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
//		stressTest();
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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

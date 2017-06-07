package rcc.y2016.qual2;
import java.io.*;
import java.util.*;

public class C {
	static final String NO = "NO";
	static final String YES = "YES";

	void run() {
		String s = in.next();
		int k = in.nextInt();
		int n = s.length();
		if (n >= k * (k + 1) / 2) {
			out.println(YES);
			int from = 0;
			for (int i = 0; i < k; i++) {
				int to = (i == k - 1) ? n : from + i + 1;
				out.println(s.substring(from, to));
				from = to;
			}
			return;
		}
		String[] ans = new String[k];
		main:
		for (int mask = 0; mask < (1 << (n - 1)); mask++) {
			if (Integer.bitCount(mask) != k - 1) {
				continue;
			}
			Arrays.fill(ans, "");
			int m = 0;
			for (int i = 0; i < n; i++) {
				if (i > 0 && (((mask >> (i - 1)) & 1) == 1)) {
					m++;
				}
				ans[m] += s.charAt(i);
			}
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < i; j++) {
					if (ans[i].equals(ans[j])) {
						continue main;
					}
				}
			}
			out.println(YES);
			for (String r : ans) {
				out.println(r);
			}
			return;
		}
		out.println(NO);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new C().run();
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

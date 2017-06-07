package codeforces.round403;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		String[] s = new String[n];
		String[] t = new String[n];
		Map<String, Integer> count = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			s[i] = in.next().substring(0, 3);
			t[i] = s[i].substring(0, 2) + in.next().charAt(0);
			count.put(s[i], count.getOrDefault(s[i], 0) + 1);
		}
		Set<String> names = new TreeSet<>();
		String[] ans = new String[n];
		for (int j = 0; j <= n; j++) {
			for (int i = 0; i < n; i++) {
				if (ans[i] != null) {
					continue;
				}
				if (j < n) {
					if (count.get(s[i]) > 1 || names.contains(s[i])) {
						ans[i] = t[i];
					}
				} else {
					ans[i] = s[i];
				}
				if (ans[i] != null && !names.add(ans[i])) {
					out.println("NO");
					return;
				}
			}
		}
		out.println("YES");
		for (String x : ans) {
			out.println(x);
		}
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

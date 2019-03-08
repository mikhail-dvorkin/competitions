package codeforces.round472;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] m = new int[n];
		for (int i = 0; i < n; i++) {
			m[i] = in.nextInt();
		}
		int[] min = new int[n];
		int seen = 0;
		for (int i = 0; i < n; i++) {
			seen = Math.max(seen, m[i] + 1);
			min[i] = seen;
		}
		long ans = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (i < n - 1) {
				min[i] = Math.max(min[i], min[i + 1] - 1);
			}
			ans += min[i] - m[i] - 1;
		}
		out.println(ans);
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
		int tests = 1;//in.nextInt();
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

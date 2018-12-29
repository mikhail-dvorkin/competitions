package atcoder.agc030;
import java.io.*;
import java.util.*;

public class B {
	int m, n;
	int[] a;
	
	void run() {
		m = in.nextInt();
		n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = Math.max(ans, a[i - 1] + walk(a[i - 1], i, n, true));
			ans = Math.max(ans, m - a[n - i] + walk(a[n - i], 0, n - i, false));
		}
		out.println(ans);
	}
	
	long walk(int pos, int from, int to, boolean right) {
		if (from == to) {
			return 0;
		}
		if (right) {
			int walk = (pos - a[to - 1] + m) % m;
			return walk + walk(a[to - 1], from, to - 1, !right);
		} else {
			int walk = (a[from] - pos + m) % m;
			return walk + walk(a[from], from + 1, to, !right);
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
			//out.print("Case #" + (test + 1) + ": ");
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

package atcoder.keyence2019;
import java.io.*;
import java.util.*;

public class D {
	static final int M = 1_000_000_007;

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[m];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 0; i < m; i++) {
			b[i] = in.nextInt();
		}
		Arrays.sort(a);
		Arrays.sort(b);
		if (n < m) {
			int t = n; n = m; m = t;
			int[] tt = a; a = b; b = tt;
		}
		for (int i = 1; i < n; i++) {
			if (a[i] == a[i - 1]) {
				out.println(0);
				return;
			}
		}
		for (int i = 1; i < m; i++) {
			if (b[i] == b[i - 1]) {
				out.println(0);
				return;
			}
		}
		if (a[n - 1] != n * m || b[m - 1] != n * m) {
			out.println(0);
			return;
		}
		int i = n;
		int j = m;
		for (int v = n * m; v >= 1; v--) {
			if (i - 1 >= 0 && a[i - 1] >= v) {
				i--;
			}
			if (j - 1 >= 0 && b[j - 1] >= v) {
				j--;
			}
			if (a[i] == v && b[j] == v) {
				continue;
			}
			if (a[i] == v) {
				mul(m - j);
				continue;
			}
			if (b[j] == v) {
				mul(n - i);
				continue;
			}
			int free = (m - j) * (n - i) - (n * m - v);
			if (free <= 0) {
				out.println(0);
				return;
			}
			mul(free);
		}
		out.println(ans);
	}
	
	int ans = 1;
	
	void mul(long x) {
		ans = (int) (ans * x % M);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D().run();
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

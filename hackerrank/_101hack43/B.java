package hackerrank._101hack43;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		Arrays.sort(a);
		long[] sum = new long[n + 1];
		long[] sum2 = new long[n + 1];
		for (int i = 0; i < n; i++) {
			sum[i + 1] = sum[i] + a[i];
			sum2[i + 1] = sum2[i] + a[i] * a[i];
		}
		long cur = 0;
		for (int i = 0; i < k; i++) {
			cur += k * a[i] * a[i] + sum2[k] - 2 * a[i] * sum[k];
		}
		long ans = cur;
		for (int i = k; i < n; i++) {
			int j = i - k;
			cur -= 2 * (k * a[j] * a[j] + (sum2[i] - sum2[j]) - 2 * a[j] * (sum[i] - sum[j]));
			cur += 2 * (k * a[i] * a[i] + (sum2[i + 1] - sum2[j + 1]) - 2 * a[i] * (sum[i + 1] - sum[j + 1]));
			ans = Math.min(ans, cur);
		}
		out.println(ans);
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

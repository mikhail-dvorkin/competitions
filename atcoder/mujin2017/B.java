package atcoder.mujin2017;
import java.io.*;
import java.util.*;

public class B {
	int run() {
		int n = in.nextInt();
		boolean[][] a = new boolean[n][n];
		int[] row = new int[n];
		int[] col = new int[n];
		int sum = 0;
		for (int i = 0; i < n; i++) {
			String s = in.next();
			for (int j = 0; j < n; j++) {
				if (s.charAt(j) == '#') {
					a[i][j] = true;
					row[i]++;
					col[j]++;
					sum++;
				}
			}
		}
		if (sum == 0) {
			return -1;
		}
		for (int i = 0; i < n; i++) {
			if (row[i] < n) {
				continue;
			}
			int ans = 0;
			for (int j = 0; j < n; j++) {
				if (col[j] < n) {
					ans++;
				}
			}
			return ans;
		}
		int ans = 3 * n;
		for (int r = 0; r < n; r++) {
			int cur = (col[r] == 0) ? 1 : 0;
			for (int j = 0; j < n; j++) {
				if (!a[r][j]) {
					cur += 2;
				} else if (col[j] < n) {
					cur++;
				}
			}
			ans = Math.min(ans, cur);
		}
		return ans;
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
			out.println(new B().run());
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

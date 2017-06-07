package hackerrank.weekofcode28;
import java.io.*;
import java.util.*;

public class ChoosingWhiteBalls {
	int m;
	Map<Integer, Double> memo = new HashMap<>();
	
	double solve(int s, int n) {
		if (n == m) {
			return 0;
		}
		int t = (Integer.reverse(s) >>> (32 - n)) | (1 << n);
		s = Math.min(s, t);
		if (memo.containsKey(s)) {
			return memo.get(s);
		}
		double result = 0;
		for (int i = (n - 1) / 2; i >= 0; i--) {
			int j = n - 1 - i;
			int low = (s >> i) & 1;
			int lowNext = (s & ((1 << i) - 1)) | ((s >> (i + 1)) << i);
			if (i == j) {
				result += low + solve(lowNext, n - 1);
				continue;
			}
			int high = (s >> j) & 1;
			int highNext = (s & ((1 << j) - 1)) | ((s >> (j + 1)) << j);
			result += 2 * Math.max(low + solve(lowNext, n - 1), high + solve(highNext, n - 1));
		}
		result /= n;
		memo.put(s, result);
		return result;
	}
	
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		m = n - k;
		String s = in.next();
		int start = 1 << n;
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) == 'W') {
				start |= 1 << i;
			}
		}
		out.println(solve(start, n));
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = ChoosingWhiteBalls.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new ChoosingWhiteBalls().run();
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

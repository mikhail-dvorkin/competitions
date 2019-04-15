package roi.y2019;
import java.io.*;
import java.util.*;

public class E {
	void run() {
		int n = in.nextInt();
		String s = in.next();
		ArrayList<Integer> a = new ArrayList<>();
		for (int i = 0, j = 0; i <= n; i++) {
			if (i == n || s.charAt(i) == '1') {
				a.add(i - j);
				j = i + 1;
			}
		}
		int k = a.size() - 1;
		ans = k;
		ansOnes = k;
		ansZeros = 0;
		int limit = (int) (Math.sqrt(n) + 1);
		for (int ones = 1; ones <= Math.min(limit, k); ones++) {
			int low = 0;
			int high = n;
			while (low + 1 < high) {
				int zeros = (low + high) / 2;
				if (take(a, zeros) > ones) {
					low = zeros;
				} else {
					high = zeros;
				}
			}
		}
		for (int zeros = 1; zeros <= limit; zeros++) {
			take(a, zeros);
		}
		out.println(ans);
		for (int i = 0; i < ansOnes + 1; i++) {
			for (int j = 0; j < ansZeros; j++) {
				out.print("0");
			}
			if (i < ansOnes) {
				out.print("1");
			}
		}
		out.println();
	}
	
	int ans, ansZeros, ansOnes;
	
	void relax(int zeros, int ones) {
		int cur = ones + (ones + 1) * zeros;
		if (cur > ans) {
			ans = cur;
			ansZeros = zeros;
			ansOnes = ones;
		}
	}

	int take(ArrayList<Integer> a, int zeros) {
		for (int i = 0, x = 0;; i++) {
			int taken = 0;
			while (taken < zeros) {
				if (x == a.size()) {
					relax(zeros, i - 1);
					return i;
				}
				taken += a.get(x);
				x++;
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new E().run();
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

package gcj.y2019.round3;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class B {
	static final int M = 1000000007;
	
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		long[] cum = new long[n + 1];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			cum[i + 1] = cum[i] + a[i];
		}
		int[] leftMax = leftMax(a, true);
		int[] aReversed = IntStream.range(0, n).map(i -> a[n - 1 - i]).toArray();
		int[] rightMaxReversed = leftMax(aReversed, false);
		int[] rightMax = IntStream.range(0, n).map(i -> n - 1 - rightMaxReversed[n - 1 - i]).toArray();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int t = 0; t < 2; t++) {
				int farFrom, from, to, farTo;
				if (t == 0) {
					farFrom = 0;
					from = leftMax[i] + 1;
					to = i + 1;
					farTo = rightMax[i] + 1;
				} else {
					farFrom = leftMax[i];
					from = i;
					to = rightMax[i];
					farTo = n;
				}
				long x = ((long) to - from) * a[i] - cum[to] + cum[from];
				long y = ((long) from - farFrom) * (farTo - to);
				ans = (int) ((ans + x % M * y) % M);
			}
		}
		out.println(ans);
	}

	int[] leftMax(int[] a, boolean strict) {
		int[] leftMax = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			leftMax[i] = i - 1;
			while (leftMax[i] >= 0 && a[leftMax[i]] < a[i] + (strict ? 1 : 0)) {
				leftMax[i] = leftMax[leftMax[i]];
			}
		}
		return leftMax;
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
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

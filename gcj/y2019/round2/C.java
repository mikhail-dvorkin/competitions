package gcj.y2019.round2;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
		}
		int minA = 0;
		int minB = 1;
		int maxA = 1;
		int maxB = 0;
		for (int i = 1; i < n; i++) {
			int a = x[i - 1] - x[i];
			int b = y[i] - y[i - 1];
			// ax < by
			if (a == 0 && b < 0 || b == 0 && a > 0) {
				impossible();
				return;
			}
			if (a == 0 || b == 0) {
				continue;
			}
			if (a > 0) {
				// a/b > minA/minB
				if (a * (long) minB > minA * (long) b) {
					minA = a;
					minB = b;
				}
				continue;
			}
			a = -a;
			b = -b;
			// a/b < maxA/maxB
			if (a * (long) maxB < maxA * (long) b) {
				maxA = a;
				maxB = b;
			}
		}
		//  minA/minB >= maxA/maxB
		if (minA * (long) maxB >= maxA * (long) minB) {
			impossible();
			return;
		}
		solve(minA, minB, maxA, maxB);
	}

	void solve(int minA, int minB, int maxA, int maxB) {
		int g = gcd(minA, minB);
		minA /= g;
		minB /= g;
		g = gcd(maxA, maxB);
		maxA /= g;
		maxB /= g;
		if (minB * maxA - minA * maxB == 1) {
			out.println((minB + maxB) + " " + (minA + maxA));
			return;
		}
		for (long x = 1;; x++) {
			long y = x * minA / minB + 1;
			if (y * maxB < x * maxA) {
				out.println(x + " " + y);
				return;
			}
		}
	}

	void impossible() {
		out.println("IMPOSSIBLE");
	}
	
	static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
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
			out.print("Case #" + (test + 1) + ": ");
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

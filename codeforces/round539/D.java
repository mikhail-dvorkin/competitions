package codeforces.round539;
import java.io.*;
import java.util.*;

public class D {
	static final int M = 1_000_000_007;

	static int solve(int n, int m) {
		int ans = 0;
		int f = 1; // c(n-2, p) * c(m-1, p) * p!
		for (int p = 0; p <= Math.min(m - 1, n - 2); p++) {
			if (p > 0) {
				f = mul(f, n - p - 1);
				f = mul(f, m - p);
				f = div(f, p);
			}
			int t = mul(f, pow(m, n - p - 2));
			t = mul(t, pow(n, n - p - 3));
			t = mul(t, p + 2);
			ans = add(ans, t);
		}
		return ans;
	}

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		out.println(solve(n, m));
	}

	static int add(int a, int b) {
		return (a + b) % M;
	}

	static int mul(int a, int b) {
		return (int) ((a * (long) b) % M);
	}

	static int div(int a, int b) {
		return mul(a, modInverse(b));
	}

	static int pow(int base, int p) {
		if (p < 0) {
			return pow(modInverse(base), -p);
		}
		if (p == 0) {
			return 1;
		}
		int v = pow(base, p / 2);
		v = mul(v, v);
		return (p & 1) == 0 ? v : mul(v, base);
	}

	static int gcdExtended(int a, int b, int[] xy) {
		if (a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		int d = gcdExtended(b % a, a, xy);
		int t = xy[0];
		xy[0] = xy[1] - (b / a) * xy[0];
		xy[1] = t;
		return d;
	}

	static int modInverse(int x) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, M, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + M);
		}
		int result = xy[0] % M;
		if (result < 0) {
			result += M;
		}
		return result;
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
		//noinspection ConstantConditions
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
		final BufferedReader br;
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

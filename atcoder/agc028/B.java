package atcoder.agc028;
import java.io.*;
import java.util.*;

public class B {
	private static final int M = 1_000_000_007;

	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		long ans = 0;
		int f = 1;
		int[] cum = new int[n + 1];
		int[] up = new int[n + 1];
		int[] down = new int[n + 1];
		int[] mi = new int[n + 1];
		for (int i = 0; i < n; i++) {
			f = (int) (f * (i + 1L) % M);
			cum[i + 1] = (cum[i] + a[i]) % M;
			up[i + 1] = (int) ((up[i] + a[i] * (long) i) % M);
			down[i + 1] = (int) ((down[i] + a[i] * (long) (n - 1 - i)) % M);
			mi[i + 1] = modInverse(i + 1, M);
		}
		int[] mic = new int[n + 1];
		for (int i = 0; i < n; i++) {
			mic[i + 1] = (mic[i] + mi[n - i]) % M;
		}
		for (int i = 0; i < n; i++) {
			int s = (1 + mic[n - 1 - i] + mic[i]) % M;
			s = (int) ((s * (long) f) % M);
			s = (int) ((s * (long) a[i]) % M);
			ans = (ans + s) % M;
		}
		for (int len = 1; len <= n - 2; len++) {
//			for (int i = 1; i + len < n; i++) {
//				for (int k = i; k < i + len; k++) {
//					s = (s + a[k]) % M;
//				}
//			}
			int b = (cum[n - len] + M - cum[len]) % M;
			b = (int) (b * (long) len % M);
			int s = (down[n] + M - down[n - len]) % M;
			s = (s + b) % M;
			s = (s + up[len]) % M;
			s = (int) ((s * 2 * (long) f) % M);
			s = (int) ((s * (long) mi[len + 1]) % M);
			s = (int) ((s * (long) mi[len + 2]) % M);
			ans = (ans + s) % M;
		}
		out.println(ans);
	}
	
	public static int gcdExtended(int a, int b, int[] xy) {
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
	
	public static int modInverse(int x, int p) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, p, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + p);
		}
		int result = xy[0] % p;
		if (result < 0) {
			result += p;
		}
		return result;
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

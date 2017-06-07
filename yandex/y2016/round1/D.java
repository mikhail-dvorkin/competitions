package yandex.y2016.round1;
import java.io.*;
import java.util.*;

public class D {
	static final int M = 1000000007;
	
	void run() {
		int m = in.nextInt();
		int w = in.nextInt();
		int p = in.nextInt();
		
		precalcFactorials(Math.max(m, w) * 2);
		
		int ans = 0;
		for (int g = 1; g <= p + 1; g++) {
			for (int first = 0; first < 2; first++) {
				int gm = (g + first) / 2;
				int gw = g - gm;
				if (gw == 0 && w > 0 || gm == 0 && m > 0) {
					continue;
				}
				long r = a(p, g - 1);
				if (gw >= 2) {
					r *= c(w - g + gw, gw - 1);
					r %= M;
				}
				if (gm >= 2) {
					r *= c(m - g + gm, gm - 1);
					r %= M;
				}
				r *= fact[w - g + 1];
				r %= M;
				r *= fact[m - g + 1];
				r %= M;
				ans += r;
				ans %= M;
			}
		}
		out.println(ans);
	}
	
	public static int[] fact, factInv;
	
	public static void precalcFactorials(int n) {
		fact = new int[n];
		factInv = new int[n];
		fact[0] = factInv[0] = 1;
		for (int i = 1; i < n; i++) {
			fact[i] = (int) (1L * fact[i - 1] * i % M);
			factInv[i] = modInverse(fact[i], M);
		}
	}
	
	public static int a(int n, int k) {
		return (int) (1L * fact[n] * factInv[n - k] % M);
	}
	
	public static int c(int n, int k) {
		return (int) (1L * a(n, k) * factInv[k] % M);
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
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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

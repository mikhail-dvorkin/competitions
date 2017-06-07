package rcc.y2014.finals;
import java.io.*;
import java.util.*;

public class B {
	final int M = 1000000007;
	
	void run() {
		String s = in.next();
		int n = s.length();
		List<Integer> lens = new ArrayList<Integer>();
		char c = s.charAt(0);
		int curLen = 1;
		for (int i = 1; i < n; i++) {
			char d = s.charAt(i);
			if (d == c) {
				curLen++;
			} else {
				lens.add(curLen);
				c = d;
				curLen = 1;
			}
		}
		lens.add(curLen);
		int m = lens.size();
		int mm = (m + 1) / 2;
//		int[][] cnk = new int[mm + 1][mm + 1];
//		for (int i = 0; i <= mm; i++) {
//			cnk[i][0] = cnk[i][i] = 1;
//			for (int j = 1; j < i; j++) {
//				cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
//			}
//		}
		int[] a = new int[mm + 1];
		a[0] = 1;
//		for (int i = 1; i <= mm; i++) {
//			for (int j = 0; j < i; j++) {
//				a[i] += a[j] * a[i - 1 - j] * cnk[i][j + 1];
//			}
//		}
		for (int i = 1; i <= mm; i++) {
			long x = a[i - 1];
			x *= 2 * i - 1;
			x %= M;
			a[i] = (int) x;
		}
		if (m % 2 == 1) {
			out.println(a[mm - 1]);
			return;
		}
		long ans = 0;
		for (int t = 0; t < 2; t++) {
			int cum = 0;
			long cnk = 1;
			for (int i = 0; i < m; i++) {
				if (i % 2 == 1) {
					int j = i / 2;
					long x = n - cum + 1;
					x *= a[j];
					x %= M;
					x *= a[mm - j - 1];
					x %= M;
					x *= cnk;
					x %= M;
					ans += x;
					ans %= M;
					cnk *= mm - j;
					cnk %= M;
					cnk *= modInverse(j + 1, M);
					cnk %= M;
				}
				cum += lens.get(i);
			}
			Collections.reverse(lens);
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

	static boolean stdStreams = !true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = "09";//fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
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

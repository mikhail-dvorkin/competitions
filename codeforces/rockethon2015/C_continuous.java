package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class C_continuous {
	int M = 10000;
	
	void run() {
		int n = in.nextInt();
		double[] a = new double[n];
		double[] b = new double[n];
		double[] c = new double[2 * n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			if (a[i] == b[i]) {
				a[i] -= 1e-9;
				b[i] += 1e-9;
			}
			c[2 * i] = a[i];
			c[2 * i + 1] = b[i];
		}
		Arrays.sort(c);
		double[] coef1 = new double[n + 1];
		double[] coef2 = new double[n + 1];
		for (int i = 1; i <= n; i++) {
			coef1[i] = 1 - 1.0 / (i + 1);
			coef2[i] = 1 - 2.0 / (i + 1);
		}
		double ans = 0;
//		double check = 0;
		for (int i = 0; i + 1 < 2 * n; i++) {
			double from = c[i];
			double to = c[i + 1];
			if (from == to) {
				continue;
			}
			for (int m = 1; m < (1 << n); m++) {
				int bitCount = Integer.bitCount(m);
				loop:
				for (int k = 0; k <= n; k++) {
					if (k < n && ((m >> k) & 1) != 0) {
						continue;
					}
					if (k == n && bitCount == 1) {
						continue;
					}
					double p = 1;
					for (int j = 0; j < n; j++) {
						if (j == k) {
							if (b[j] <= to) {
								continue loop;
							}
							if (to > a[j]) {
								p *= 1.0 * (b[j] - to) / (b[j] - a[j]);
							}
							continue;
						}
						if (((m >> j) & 1) != 0) {
							if (b[j] < to || a[j] > from) {
								continue loop;
							}
							p *= 1.0 * (to - from) / (b[j] - a[j]);
							continue;
						}
						if (a[j] >= from) {
							continue loop;
						}
						if (from < b[j]) {
							p *= 1.0 * (from - a[j]) / (b[j] - a[j]);
						}
					}
					double coef = k == n ? coef2[bitCount] : coef1[bitCount];
					out.println(p + " " + m + " " + k + " " + coef);
					ans += p * (from + coef * (to - from));
//					check += p;
				}
			}
		}
//		out.println(check);
		out.println(ans);
	}
	
	static boolean stdStreams = true;
	static String fileName = C_continuous.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new C_continuous().run();
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

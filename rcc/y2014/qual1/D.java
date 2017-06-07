package rcc.y2014.qual1;
import java.io.*;
import java.util.*;

public class D {
	final int MOD = 1000000007;
	
	void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int[][] f = new int[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				f[i][j] = "BGW.".indexOf(s.charAt(j));
			}
		}
		int masks = 9 << (hei - 1);
		int[] a = new int[masks];
		int[] b = new int[masks];
		int[] c = new int[hei];
		a[0] = 1;
		for (int j = 0; j < wid; j++) {
			for (int i = 0; i < hei; i++) {
				int fij = f[i][j];
				for (int mask = 0; mask < masks; mask++) {
					int aMask = a[mask];
					if (aMask == 0) {
						continue;
					}
					decode(mask, c, i);
					int oldci = c[i];
					for (c[i] = 0; c[i] < 3; c[i]++) {
						if (fij < 3 && fij != c[i]) {
							continue;
						}
						if (i > 0 && c[i] == c[i - 1]) {
							continue;
						}
						if (j > 0 && c[i] == oldci) {
							continue;
						}
						int m = encode(c, (i + 1) % hei);
						b[m] += aMask;
						if (b[m] >= MOD) {
							b[m] -= MOD;
						}
					}
				}
				System.arraycopy(b, 0, a, 0, b.length);
				Arrays.fill(b, 0);
			}
		}
		int ans = 0;
		for (int x : a) {
			ans = (ans + x) % MOD;
		}
		System.out.println(ans);
	}
	
	int encode(int[] c, int s) {
		int hei = c.length;
		int mask = c[0] << (hei - 1);
		for (int i = 1; i < hei; i++) {
			if (i == s) {
				mask += (3 * c[i]) << (hei - 1);
				continue;
			}
			int v = c[i];
			if (v > c[i - 1]) {
				v--;
			}
			mask |= v << (i - 1);
		}
		return mask;
	}

	void decode(int mask, int[] c, int s) {
		int hei = c.length;
		c[0] = (mask >> (hei - 1)) % 3;
		for (int i = 1; i < hei; i++) {
			if (i == s) {
				c[s] = (mask >> (hei - 1)) / 3;
				continue;
			}
			c[i] = (mask >> (i - 1)) & 1;
			if (c[i] >= c[i - 1]) {
				c[i]++;
			}
		}
	}

	static boolean stdStreams = true;
	static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new D().run();
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

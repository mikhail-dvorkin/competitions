package hackerrank.weekofcode24;
import java.io.*;
import java.util.*;

public class ShashankAndPalindromicStrings {
	static final int A = 26 * 4;
	static final int MASKS = A + 4;
	static final int MOD = 1_000_000_007;

	void run() {
		int m = in.nextInt();
		String[] parts = new String[m];
		int n = 0;
		for (int i = 0; i < m; i++) {
			parts[i] = in.next();
			n += parts[i].length();
		}
		int[] s = new int[n];
		boolean[] edge = new boolean[n + 1];
		edge[0] = true;
		for (int i = 0, j = 0; i < m; i++) {
			for (int k = 0; k < parts[i].length(); k++) {
				s[j++] = (parts[i].charAt(k) - 'a') << 2;
			}
			edge[j] = true;
		}
		int[][] a = new int[n + 1][MASKS];
		int[][] b = new int[n + 1][MASKS];
		a[0][A + 3] = 1;
		for (int len = n; len > 0; len--) {
			Arrays.fill(b[0], 0);
			for (int i = 0; i <= n - len; i++) {
				int j = i + len;
				int leftNext = edge[i + 1] ? 2 : 0;
				int rightNext = edge[j - 1] ? 1 : 0;
				int[] ai = a[i];
				int[] bi = b[i];
				int[] bi1 = b[i + 1];
				Arrays.fill(bi1, 0);
				int si = s[i];
				int sj = s[j - 1];
				for (int mask = 0; mask < MASKS; mask++) {
					int value = ai[mask];
					if (value == 0) {
						continue;
					}
					int letter = mask & ~3;
					int left = mask & 2;
					int right = mask & 1;
					int index;
					if (letter == A) {
						index = si | leftNext | right;
						bi1[index] += value; if (bi1[index] >= MOD) bi1[index] -= MOD;
						if ((leftNext & left) == 0) {
							index = A | leftNext | left | right;
							bi1[index] += value; if (bi1[index] >= MOD) bi1[index] -= MOD;
						}
					} else {
						if (sj == letter) {
							index = A | left | rightNext;
							bi[index] += value; if (bi[index] >= MOD) bi[index] -= MOD;
						}
						if ((rightNext & right) == 0) {
							index = letter | left | rightNext | right;
							bi[index] += value; if (bi[index] >= MOD) bi[index] -= MOD;
						}
					}
				}
			}
			int[][] t = a; a = b; b = t;
		}
		int ans = 0;
		for (int i = 0; i <= n; i++) {
			for (int mask = 0; mask < MASKS; mask++) {
				if (!edge[i] && (mask & 3) == 3) {
					continue;
				}
				ans += a[i][mask];
				ans %= MOD;
			}
		}
		System.out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = ShashankAndPalindromicStrings.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new ShashankAndPalindromicStrings().run();
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

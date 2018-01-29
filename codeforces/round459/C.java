package codeforces.round459;
import java.io.*;
import java.util.*;

public class C {
	static final int PERIOD = 840;

	void run() {
		int x = in.nextInt();
		int k = in.nextInt();
		int n = in.nextInt();
		int q = in.nextInt();
		int[] c = new int[k];
		for (int i = 0; i < k; i++) {
			c[i] = in.nextInt();
		}
		TreeMap<Integer, Integer> w = new TreeMap<>();
		for (int i = 0; i < q; i++) {
			int p = in.nextInt();
			int wp = in.nextInt();
			w.put(p, wp);
		}
		long inf = (long) 1e18;
		int masks = 1 << k;
		long[] a = new long[masks];
		long[] b = new long[masks];
		Arrays.fill(a, inf);
		int minMask = (1 << x) - 1;
		a[minMask] = 0;
		long memo = 0;
		int prev = 0;
		for (int left = 1; left <= n - x; left++) {
			if (w.containsKey(left)) {
				prev = left;
			}
			Arrays.fill(b, inf);
			for (int mask = 0; mask < masks; mask++) {
				if (a[mask] == inf) {
					continue;
				}
				if ((mask & 1) == 0) {
					b[mask >> 1] = Math.min(b[mask >> 1], a[mask]);
					continue;
				}
				for (int j = 1; j <= k; j++) {
					if (((mask >> j) & 1) != 0) {
						continue;
					}
					long score = a[mask] + c[j - 1];
					if (w.containsKey(left + j)) {
						score += w.get(left + j);
					}
					int newMask = (mask | (1 << j)) >> 1;
					b[newMask] = Math.min(b[newMask], score);
				}
			}
			if (left == prev + PERIOD) {
				memo = a[minMask];
			}
			if (left == prev + 2 * PERIOD) {
				Integer nextKey = w.ceilingKey(left);
				int next = (nextKey == null) ? n - x : nextKey;
				int steps = (next - left) / PERIOD - 1;
				if (steps > 0) {
					memo = a[minMask] - memo;
					for (int mask = 0; mask < masks; mask++) {
						if (a[mask] != inf) {
							a[mask] += steps * memo;
						}
					}
					left += steps * PERIOD - 1;
					continue;
				}
			}
			long[] temp = a; a = b; b = temp;
		}
		out.println(a[minMask]);
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
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

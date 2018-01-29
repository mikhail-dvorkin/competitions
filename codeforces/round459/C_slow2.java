package codeforces.round459;
import java.io.*;
import java.util.*;

public class C_slow2 {
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
		long inf = (long) 3e18;
		int masks = 1 << k;
		long[] a = new long[masks];
		long[] b = new long[masks];
		Arrays.fill(a, inf);
		int minMask = (1 << x) - 1;
		a[minMask] = 0;
		for (int left = 1; left <= n - x; left++) {
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
			boolean stable = true;
			long diff = inf;
			for (int mask = 0; mask < masks; mask++) {
				if ((a[mask] == inf) ^ (b[mask] == inf)) {
					stable = false;
					break;
				}
				if (a[mask] == inf) {
					continue;
				}
				long d = b[mask] - a[mask];
				if (diff == inf) {
					diff = d;
					continue;
				}
				if (diff != d) {
					System.out.println(mask + " " + diff + " " + d + " " + a[mask] + " " + b[mask]);
					stable = false;
					break;
				}
			}
			System.out.println(left);
			System.out.println(Arrays.toString(a));
			System.out.println(Arrays.toString(b));
			if (stable) {
				int steps = n - x - left - 2;
				for (int p : w.keySet()) {
					if (p < left) {
						continue;
					}
					steps = Math.min(steps, p - x - left - 2);
				}
				if (steps > 1) {
					for (int mask = 0; mask < masks; mask++) {
						if (a[mask] == inf) {
							continue;
						}
						a[mask] += steps * diff;
					}
					left += steps - 1;
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
		String fileName = C_slow2.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new C_slow2().run();
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

package atcoder.arc066;
import java.io.*;
import java.util.*;

public class D {
	static final int MOD = 1_000_000_007;
	static final boolean VISUALIZE = false;
	
	long n;
	Map<Long, Map<Long, Integer>> memo = new TreeMap<>();
	Map<Long, Integer> size = new TreeMap<>();

	void run() {
		n = in.nextLong() + 1;
		if (VISUALIZE) {
			visualize();
		}
		out.println(solve());
	}

	int solve() {
		long level = 1;
		size.put(level, 1);
		while (level < n) {
			size.put(2 * level, (int) (3L * size.get(level) % MOD));
			memo.put(level, new TreeMap<>());
			level *= 2;
		}
		return solve(level, 0);
	}

	int solve(long level, long x) {
		if (x >= n) {
			return 0;
		}
		if (x + 2 * level - 1 <= n) {
			return size.get(level);
		}
		level /= 2;
		if (memo.get(level).containsKey(x)) {
			return memo.get(level).get(x);
		}
		int res = solve(level, x);
		res += solve(level, x + level);
		res %= MOD;
		res += solve(level, x + 2 * level);
		res %= MOD;
		memo.get(level).put(x, res);
		return res;
	}
	
	void visualize() {
		boolean[][] nice = new boolean[(int) n][(int) n];
		for (int a = 0; a < n; a++) {
			for (int b = 0; a + b < n; b++) {
				int u = a ^ b;
				if (u < n) {
					nice[u][a + b] = true;
				}
			}
		}
		for (int u = 0; u < n; u++) {
			for (int v = 0; v < n; v++) {
				System.err.print(nice[u][v] ? "#" : ".");
			}			
			System.err.println();
		}
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

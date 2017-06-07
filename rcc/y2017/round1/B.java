package rcc.y2017.round1;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		boolean[][] sets = new boolean[n][26];
		for (int i = 0; i < n; i++) {
			String s = in.next();
			for (int j = 0; j < s.length(); j++) {
				sets[i][s.charAt(j) - 'a'] = true;
			}
		}
		String s = in.next();
		long[] cost = new long[n];
		long[] cost2 = new long[n];
		long inf = Long.MAX_VALUE / 3;
		Arrays.fill(cost, inf);
		cost[0] = 0;
		for (int i = 0; i < s.length(); i++) {
			Arrays.fill(cost2, inf);
			int symbol = s.charAt(i) - 'a';
			for (int j = 0; j < n; j++) {
				cost2[(j + 1) % n] = cost[j] + a;
			}
			for (int t = 0; t < 2; t++) {
				for (int j = 0; j < n; j++) {
					cost2[(j + 1) % n] = Math.min(cost2[(j + 1) % n], cost2[j] + b);
				}
			}
			for (int j = 0; j < n; j++) {
				if (!sets[j][symbol]) {
					cost[j] = inf;
					continue;
				}
				cost[j] = Math.min(cost[j], cost2[j]);
			}
		}
		Arrays.sort(cost);
		if (cost[0] >= inf) {
			System.out.println(-1);
			return;
		}
		System.out.println(cost[0] + s.length() * 1L * c);
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

package rcc.y2016.qual2;
import java.io.*;
import java.util.*;

public class D {
	int T = 3;
	
	void run() {
		int n = in.nextInt();
		int[][] a = new int[T][n];
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < n; j++) {
				a[i][in.nextInt() - 1] = j;
			}
		}
		int[] selected = new int[1 << n];
		for (int mask = 1; mask < (1 << n); mask++) {
			int m = Integer.bitCount(mask);
			if (m == 1) {
				for (int i = 0; i < n; i++) {
					if (((mask >> i) & 1) == 0) {
						continue;
					}
					selected[mask] = i;
					break;
				}			
				continue;
			}
			int t = (n - m) % T;
			int best = -1;
			for (int i = 0; i < n; i++) {
				if (((mask >> i) & 1) == 0) {
					continue;
				}
				int s = selected[mask ^ (1 << i)];
				if (best == -1 || a[t][s] < a[t][best]) {
					best = s;
				}
			}
			selected[mask] = best;
		}
		out.println(selected[(1 << n) - 1] + 1);
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
		int tests = in.nextInt();
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

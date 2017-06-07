package hackerrank._101hack43;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int r = in.nextInt();
		int[][] a = new int[hei][wid];
		long[][] s = new long[hei][wid + 1];
		int[] h = new int[hei];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = in.nextInt();
				s[i][j + 1] = s[i][j] + a[i][j];
				if (i * i + j * j <= r * r) {
					h[i] = j;
				}
			}
		}
		long ans = 0;
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				long f = 0;
				for (int k = 0; k < hei; k++) {
					int di = Math.abs(k - i);
					if (di > r) {
						continue;
					}
					int jLow = Math.max(j - h[di], 0);
					int jHigh = Math.min(j + h[di], wid - 1);
					f += s[k][jHigh + 1] - s[k][jLow];
				}
				ans ^= f;
			}
		}
		out.println(ans);
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

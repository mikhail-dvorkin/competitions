package hackerrank.hourrank18;
import java.io.*;
import java.util.*;

public class D_slow {
	void run() {
		int n = in.nextInt();
		int[] a = new int[3];
		for (int i = 0; i < 3; i++) {
			a[i] = in.nextInt();
		}
		Arrays.sort(a);
		int[][] e = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(e[i], n);
		}
		for (int i = 0; i < n - 1; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			e[u][v] = e[v][u] = 1;
		}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (e[i][j] > e[i][k] + e[k][j]) {
						e[i][j] = e[i][k] + e[k][j];
					}
				}
			}
		}
		int[] d = new int[3];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					d[0] = e[i][j];
					d[1] = e[j][k];
					d[2] = e[k][i];
					Arrays.sort(d);
					if (d[0] * a[1] == d[1] * a[0] &&
						d[0] * a[2] == d[2] * a[0]) {
						ans++;
					}
				}
			}
		}
		System.out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D_slow.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D_slow().run();
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

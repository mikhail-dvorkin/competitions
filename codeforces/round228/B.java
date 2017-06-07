package codeforces.round228;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int k = in.nextInt();
		int n = 1;
		while ((1 << n) <= k) {
			n++;
		}
		int m = 5 * n + 1;
		out.println(m);
		e = new boolean[m][m];
		for (int i = 0; i < n - 1; i++) {
			addEdge(i, n + i);
			addEdge(i, 2 * n + i);
			addEdge(i + 1, n + i);
			addEdge(i + 1, 2 * n + i);
			addEdge(3 * n + 2 * i, 3 * n + 2 * i + 1);
			addEdge(3 * n + 2 * i + 1, 3 * n + 2 * i + 2);
		}
		addEdge(-1, 3 * n + 2 * (n - 2) + 2);
		for (int i = 0; i < n; i++) {
			if (((k >> i) & 1) == 0) {
				continue;
			}
			addEdge(i, 3 * n + 2 * i);
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				out.print(e[i][j] ? "Y" : "N");
			}
			out.println();
		}
	}
	
	boolean[][] e;
	
	void addEdge(int a, int b) {
		a++;
		b++;
		e[a][b] = e[b][a] = true;
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new B().run();
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

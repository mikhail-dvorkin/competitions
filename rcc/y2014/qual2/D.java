package rcc.y2014.qual2;
import java.io.*;
import java.util.*;

public class D {
	int inf = Integer.MAX_VALUE / 3;
	
	void run() {
		int n = in.nextInt();
		int[] deg = new int[n];
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			deg[a]++;
			deg[b]++;
		}
		long oper = 0;
		int bestDec = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			int usual = oper(deg[i] - 1);
			int root = oper(deg[i]);
			oper += usual;
			bestDec = Math.min(root - usual, bestDec);
		}
		long ans = oper + bestDec;
		if (ans >= inf) {
			out.println("-1");
			return;
		}
		out.println(ans);
	}
	
	int oper(int edges) {
		if (edges >= 3) {
			return inf;
		}
		if (edges % 2 == 0) {
			return edges / 2;
		}
		return edges / 2 + 2;
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

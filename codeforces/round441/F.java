package codeforces.round441;
import java.io.*;
import java.util.*;

public class F {
	static final int MAX_W = 10000;
	
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		Random r = new Random(566);
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] a = new ArrayList[MAX_W + 1];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] b = new ArrayList[MAX_W + 1];
		for (int i = 0; i < a.length; i++) {
			a[i] = new ArrayList<>();
			b[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			int w = in.nextInt();
			a[w].add(x);
			b[w].add(y);
		}
		
		p = new int[n];
		boolean[] full = new boolean[n];
		for (int i = 0; i < p.length; i++) {
			p[i] = i;
		}
		long ans = 0;
		for (int w = MAX_W; w >= 0; w--) {
			for (int i = 0; i < a[w].size(); i++) {
				int x = a[w].get(i);
				int y = b[w].get(i);
				x = get(x);
				y = get(y);
				if (x == y) {
					if (!full[x]) {
						ans += w;
						full[x] = true;
					}
					continue;
				}
				if (full[x] && full[y]) {
					continue;
				}
				ans += w;
				if (r.nextBoolean()) {
					p[x] = y;
				} else {
					p[y] = x;
				}
				full[x] = full[y] = full[x] || full[y];
			}
		}
		out.println(ans);
	}
	
	int[] p;

	int get(int v) {
		if (p[v] == v) {
			return v;
		}
		p[v] = get(p[v]);
		return p[v];
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = F.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new F().run();
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

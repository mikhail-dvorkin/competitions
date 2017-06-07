package rcc.y2014.round1;

import java.io.*;
import java.util.*;

public class E {
	int n;
	List<Integer>[] nei;
	List<Boolean>[] neg;
	boolean imp;
	boolean[] mark;
	boolean[] color;
	List<Integer> a, b;
	
	@SuppressWarnings("unchecked")
	void init() {
		nei = new ArrayList[n];
		neg = new ArrayList[n];
	}

	void run() {
		n = in.nextInt();
		int m = in.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		init();
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			nei[i] = new ArrayList<Integer>();
			neg[i] = new ArrayList<Boolean>();
		}
		imp = false;
		for (int i = 0; i < m; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			boolean ok0 = x[v] < x[u] || y[v] < y[u];
			boolean ok1 = x[v] < y[u] || y[v] < x[u];
			if (!ok0 && !ok1) {
				imp = true;
				continue;
			}
			if (ok0 && ok1) {
				continue;
			}
			nei[u].add(v);
			neg[u].add(ok1);
			nei[v].add(u);
			neg[v].add(ok1);
		}
		mark = new boolean[n];
		color = new boolean[n];
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (mark[i]) {
				continue;
			}
			a = new ArrayList<Integer>();
			b = new ArrayList<Integer>();
			dfs(i);
			if (b.size() < a.size()) {
				a = b;
			}
			ans.addAll(a);
		}
		if (imp) {
			out.println(-1);
			return;
		}
		out.print(ans.size());
		for (int v : ans) {
			out.print(" " + (v + 1));
		}
		out.println();
	}

	void dfs(int v) {
		mark[v] = true;
		boolean c = color[v];
		if (c) {
			b.add(v);
		} else {
			a.add(v);
		}
		for (int i = 0; i < nei[v].size(); i++) {
			int u = nei[v].get(i);
			boolean col = c ^ neg[v].get(i);
			if (mark[u]) {
				if (color[u] != col) {
					imp = true;
					return;
				}
				continue;
			}
			color[u] = col;
			dfs(u);
		}
	}

	static boolean stdStreams = true;
	static String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new E().run();
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

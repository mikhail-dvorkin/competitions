package codeforces.round286;
import java.io.*;
import java.util.*;

public class B {
	ArrayList<Integer>[] from;
	ArrayList<Integer>[] to;
	int[] color;
	int[] tOut;
	boolean[] mark;
	int[] size;
	int time;
	
	@SuppressWarnings("unchecked")
	void run() {
		int n = in.nextInt();
		int pairs = in.nextInt();
		int[] a = new int[pairs];
		int[] b = new int[pairs];
		from = new ArrayList[n];
		to = new ArrayList[n];
		color = new int[n];
		tOut = new int[n];
		mark = new boolean[n];
		size = new int[n];
		for (int i = 0; i < n; i++) {
			from[i] = new ArrayList<Integer>();
			to[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < pairs; i++) {
			a[i] = in.nextInt() - 1;
			b[i] = in.nextInt() - 1;
			from[a[i]].add(b[i]);
			to[b[i]].add(a[i]);
		}
		Arrays.fill(color, -1);
		int comp = 0;
		for (int i = 0; i < n; i++) {
			if (color[i] >= 0) {
				continue;
			}
			dfs1(i, comp);
			comp++;
		}
		for (int i = 0; i < n; i++) {
			dfs2(i);
		}
		boolean[] cycle = new boolean[comp];
		for (int i = 0; i < pairs; i++) {
			if (tOut[a[i]] > tOut[b[i]]) {
				continue;
			}
			cycle[color[a[i]]] = true;
		}
		int ans = 0;
		for (int i = 0; i < comp; i++) {
			ans += size[i] - (cycle[i] ? 0 : 1);
		}
		out.println(ans);
	}
	
	void dfs1(int v, int comp) {
		if (color[v] == comp) {
			return;
		}
		color[v] = comp;
		size[comp]++;
		for (int u : from[v]) {
			dfs1(u, comp);
		}
		for (int u : to[v]) {
			dfs1(u, comp);
		}
	}
	
	void dfs2(int v) {
		if (mark[v]) {
			return;
		}
		mark[v] = true;
		for (int u : from[v]) {
			dfs2(u);
		}
		tOut[v] = time;
		time++;
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

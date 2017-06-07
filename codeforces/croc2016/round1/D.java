package codeforces.croc2016.round1;
import java.io.*;
import java.util.*;

public class D {
	int n;
	int m;
	int[] won;
	int[] lost;
	List<Integer>[] beat;
	boolean[] mark;
	int[] order;
	int[] orderRev;
	int orderCount;
	
	@SuppressWarnings("unchecked")
	void run() {
		n = in.nextInt();
		m = in.nextInt();
		won = new int[m];
		lost = new int[m];
		beat = new List[n];
		for (int i = 0; i < n; i++) {
			beat[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			won[i] = in.nextInt() - 1;
			lost[i] = in.nextInt() - 1;
			beat[won[i]].add(lost[i]);
		}
		mark = new boolean[n];
		order = new int[n];
		orderRev = new int[n];
		orderCount = 0;
		for (int i = 0; i < n; i++) {
			dfs(i);
		}
		int seen = 0;
		int max = -1;
		for (int i = 0; i < m; i++) {
			int x = won[i];
			int y = lost[i];
			if (orderRev[x] == orderRev[y] + 1) {
				seen++;
				max = Math.max(max, i);
			}
		}
		if (seen < n - 1) {
			System.out.println(-1);
		} else {
			System.out.println(max + 1);
		}
	}

	void dfs(int v) {
		if (mark[v]) {
			return;
		}
		mark[v] = true;
		for (int u : beat[v]) {
			dfs(u);
		}
		order[orderCount] = v;
		orderRev[v] = orderCount;
		orderCount++;
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

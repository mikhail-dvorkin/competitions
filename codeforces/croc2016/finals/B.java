package codeforces.croc2016.finals;
import java.io.*;
import java.util.*;

public class B {
	ArrayList<Integer>[] nei;
	ArrayList<Boolean>[] col;
	int color;
	boolean[][] mark;
	boolean[][] inv;
	boolean[] possible = new boolean[2];
	ArrayList<Integer> yes = new ArrayList<>();
	ArrayList<Integer> no = new ArrayList<>();
	@SuppressWarnings("unchecked")
	ArrayList<Integer>[] ans = new ArrayList[2];

	@SuppressWarnings("unchecked")
	void init(int n) {
		nei = new ArrayList[n];
		col = new ArrayList[n];
	}

	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		init(n);
		mark = new boolean[2][n];
		inv = new boolean[2][n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
			col[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			boolean red = in.next().equals("R");
			nei[a].add(b);
			col[a].add(red);
			nei[b].add(a);
			col[b].add(red);
		}
		Arrays.fill(possible, true);
		ans[0] = new ArrayList<>();
		ans[1] = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (mark[0][i]) {
				continue;
			}
			for (color = 0; color < 2; color++) {
				no.clear();
				yes.clear();
				dfs(i);
				if (yes.size() < no.size()) {
					ans[color].addAll(yes);
				} else {
					ans[color].addAll(no);
				}
			}
		}
		if (!possible[0] && !possible[1]) {
			System.out.println(-1);
			return;
		}
		if (!possible[0] || possible[1] && ans[1].size() < ans[0].size()) {
			ans[0] = ans[1];
		}
		System.out.println(ans[0].size());
		for (int i : ans[0]) {
			System.out.print((i + 1) + " ");
		}
		System.out.println();
	}

	private void dfs(int v) {
		mark[color][v] = true;
		if (inv[color][v]) {
			yes.add(v);
		} else {
			no.add(v);
		}
		for (int i = 0; i < nei[v].size(); i++) {
			int u = nei[v].get(i);
			boolean c = col[v].get(i);
			boolean uInv = inv[color][v] ^ (color > 0) ^ c;
			if (mark[color][u]) {
				if (inv[color][u] != uInv) {
					possible[color] = false;
				}
				continue;
			}
			inv[color][u] = uInv;
			dfs(u);
		}
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

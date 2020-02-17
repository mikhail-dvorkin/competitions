package codeforces.round383;
import java.io.*;
import java.util.*;

public class B {
	int n;
	boolean[] mark;
	ArrayList<Integer>[] nei;
	int sum;
	final ArrayList<Integer> component = new ArrayList<>();

	@SuppressWarnings("unchecked")
	void run() {
		n = in.nextInt();
		int m = in.nextInt();
		int s = in.nextInt();
		int[] weights = new int[n];
		int[] beauties = new int[n];
		nei = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			weights[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			beauties[i] = in.nextInt();
			nei[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			nei[x].add(y);
			nei[y].add(x);
		}
		mark = new boolean[n];
		int[] a = new int[s + 1];
		Arrays.fill(a, Integer.MIN_VALUE);
		a[0] = 0;
		for (int i = 0; i < n; i++) {
			if (mark[i]) {
				continue;
			}
			component.clear();
			dfs(i);
			int[] b = a.clone();
			int totalW = 0;
			int totalB = 0;
			for (int p = 0; p < component.size() + 2; p++) {
				int weight = 0;
				int beauty = 0;
				if (p < component.size()) {
					weight = weights[component.get(p)];
					beauty = beauties[component.get(p)];
					totalW += weight;
					totalB += beauty;
				} else if (p == component.size()) {
					weight = totalW;
					beauty = totalB;
				}
				for (int j = s; j >= weight; j--) {
					b[j] = Math.max(b[j], a[j - weight] + beauty);
				}
			}
			a = b;
		}
		Arrays.sort(a);
		out.println(a[s]);
	}

	void dfs(int v) {
		mark[v] = true;
		component.add(v);
		for (int u : nei[v]) {
			if (mark[u]) {
				continue;
			}
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
		final BufferedReader br;
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

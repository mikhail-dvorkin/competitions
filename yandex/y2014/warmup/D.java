package yandex.y2014.warmup;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] nei = new ArrayList[n];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] weight = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<Integer>();
			weight[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int c = in.nextInt();
			nei[a].add(b);
			weight[a].add(c);
			nei[b].add(a);
			weight[b].add(c);
		}
		long inf = Long.MAX_VALUE / 3;
		long[] dist = new long[n];
		boolean[] mark = new boolean[n];
		Arrays.fill(dist, inf);
		dist[0] = 0;
		for (;;) {
			int v = -1;
			for (int i = 0; i < n; i++) {
				if (mark[i]) {
					continue;
				}
				if (v == -1 || dist[i] < dist[v]) {
					v = i;
				}
			}
			if (v == -1 || dist[v] == inf) {
				break;
			}
			mark[v] = true;
			for (int i = 0; i < nei[v].size(); i++) {
				int u = nei[v].get(i);
				int c = weight[v].get(i);
				long cur = dist[v] + c * n - 1;
				if (cur < dist[u]) {
					dist[u] = cur;
				}
			}
		}
		long d = dist[n - 1];
		int edges = (int) (n - (d % n));
		d = (d + edges) / n;
		out.println(d + " " + edges);
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

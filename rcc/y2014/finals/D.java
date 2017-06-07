package rcc.y2014.finals;
import java.io.*;
import java.util.*;

public class D {
	int n;
	ArrayList<Integer>[] nei;
	
	@SuppressWarnings("unchecked")
	void run() {
		n = in.nextInt();
		nei = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		if (n == 2) {
			System.out.println(1);
			System.out.println(1);
			return;
		}
		int root = -1;
		for (int i = 0; i < n; i++) {
			if (root == -1 || nei[i].size() > nei[root].size()) {
				root = i;
			}
		}
		bamboo = new boolean[n];
		bottom = new int[n];
		dfs(root, -1);
		System.out.println(answer.size());
		for (int i : answer) {
			System.out.print(i + 1 + " ");
		}
		System.out.println();
	}
	
	boolean[] bamboo;
	int[] bottom;
	List<Integer> answer = new ArrayList<Integer>();

	void dfs(int v, int p) {
		int kids = 0;
		int bamboos = 0;
		bottom[v] = v;
		for (int u : nei[v]) {
			if (u == p) {
				continue;
			}
			dfs(u, v);
			kids++;
			if (bamboo[u]) {
				bamboos++;
				bottom[v] = bottom[u];
				if (bamboos > 1) {
					answer.add(bottom[u]);
				}
			}
		}
		bamboo[v] = (kids == 0) || (kids == 1 && bamboos == 1);
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
// 5 1 2 1 5 3 5 4 5
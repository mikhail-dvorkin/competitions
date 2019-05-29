package gcj.y2019.round2;
import java.io.*;
import java.util.*;

public class D {
	static final int M = 1000000007;
	
	int n;
	ArrayList<Integer>[] nei, neiBack;
	boolean[] inf;
	int[] gives;
	
	@SuppressWarnings("unchecked")
	void run() {
		n = in.nextInt();
		nei = new ArrayList[n];
		neiBack = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
			neiBack[i] = new ArrayList<>();
		}
		for (int i = 0; i < n; i++) {
			for (int t = 0; t < 2; t++) {
				int j = in.nextInt() - 1;
				nei[i].add(j);
				neiBack[j].add(i);
			}
		}
		findStronglyConnectedComponents();
		Arrays.fill(mark, false);
		dfsBack(0);
		boolean[] generates0 = mark.clone();
		inf = new boolean[n];
		for (int i = 0; i < n; i++) {
			for (int t = 0; t < 2; t++) {
				int j = nei[i].get(t);
				int k = nei[i].get(t ^ 1);
				inf[i] |= scc[j] == scc[i] && generates0[k];
			}
		}
		Arrays.fill(mark, false);
		for (int i = 0; i < n; i++) {
			if (inf[i]) {
				dfsBack(i);
			}
		}
		inf = mark.clone();
		gives = new int[n];
		gives[0] = 1;
		Arrays.fill(mark, false);
		mark[0] = true;
		for (int i = 1; i < n; i++) {
			dfs(i);
		}
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			v[i] = in.nextInt();
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (v[i] == 0) {
				continue;
			}
			if (inf[i]) {
				out.println("UNBOUNDED");
				return;
			}
			ans = (int) ((ans + v[i] * (long) gives[i]) % M);
		}
		out.println(ans);
	}

	boolean[] mark;
	int[] scc;
	int[] timeOutOrder;
	int time;
	int sccAmount;

	void findStronglyConnectedComponents() {
		mark = new boolean[n];
		timeOutOrder = new int[n];
		time = 0;
		for (int i = 0; i < n; i++) {
			if (!mark[i]) {
				dfsSCC1(i);
			}
		}
		scc = new int[n];
		Arrays.fill(scc, -1);
		sccAmount = 0;
		for (int i = n - 1; i >= 0; i--) {
			int v = timeOutOrder[i];
			if (scc[v] == -1) {
				dfsSCC2(v);
				sccAmount++;
			}
		}
	}

	void dfsSCC1(int v) {
		mark[v] = true;
		for (int u : nei[v]) {
			if (!mark[u]) {
				dfsSCC1(u);
			}
		}
		timeOutOrder[time++] = v;
	}

	void dfsSCC2(int v) {
		scc[v] = sccAmount;
		for (int u : neiBack[v]) {
			if (scc[u] == -1) {
				dfsSCC2(u);
			}
		}
	}
	
	void dfsBack(int v) {
		mark[v] = true;
		for (int u : neiBack[v]) {
			if (!mark[u]) {
				dfsBack(u);
			}
		}
	}

	void dfs(int v) {
		if (mark[v] || inf[v]) {
			return;
		}
		mark[v] = true;
		int res = 0;
		for (int u : nei[v]) {
			dfs(u);
			res += gives[u];
		}
		gives[v] = res % M;
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
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

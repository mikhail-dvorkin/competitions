package roi.y2019;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int q = in.nextInt();
		int p = in.nextInt() - 1;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] nei = new ArrayList[n];
		@SuppressWarnings("unchecked")
		ArrayList<Long>[] len = new ArrayList[n];
		@SuppressWarnings("unchecked")
		ArrayList<Long>[] routes = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
			len[i] = new ArrayList<>();
			routes[i] = new ArrayList<>();
		}
		for (int i = 0; i < m; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			long d = in.nextLong();
			nei[v].add(u);
			len[v].add(d);
		}
		routes[0].add(0L);
		for (int v = 1; v < n; v++) {
			ArrayList<Long> rs = routes[v];
			for (int i = 0; i < nei[v].size(); i++) {
				int u = nei[v].get(i);
				long d = len[v].get(i);
				for (long route : routes[u]) {
					rs.add(route + d);
				}
			}
			Collections.sort(rs);			
			double lim = 1.02;
			if (rs.size() > 512) {
				ArrayList<Long> shorter = new ArrayList<>();
				long prev = 0;
				for (int i = 0; i < rs.size(); i++) {
					long r = rs.get(i);
					if (r < prev * lim && (i == rs.size() - 1 || rs.get(i + 1) < r * lim)) {
						continue;
					}
					shorter.add(r);
					prev = r;
				}
				routes[v] = shorter;
			}
		}
		for (int i = 0; i < q; i++) {
			int v = in.nextInt() - 1;
			long desired = in.nextLong();
			int k = Collections.binarySearch(routes[v], desired);
			if (k < 0) {
				k = -1 - k;
			}
			long r = (k < routes[v].size() ? routes[v].get(k) : (Long.MAX_VALUE / p));
			out.print((r * p <= desired * (p + 1)) ? 1 : 0);
		}
		out.println();
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			//out.print("Case #" + (test + 1) + ": ");
			new C().run();
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

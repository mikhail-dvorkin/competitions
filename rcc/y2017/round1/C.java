package rcc.y2017.round1;
import java.io.*;
import java.util.*;

public class C {
	public static final int[] DX = new int[]{1, 0, -1, 0};
	public static final int[] DY = new int[]{0, 1, 0, -1};

	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		Map<Long, Integer> map = new TreeMap<>();
		int low = 0;
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			if (y[i] < y[low]) {
				low = i;
			}
			map.put(code(x[i], y[i]), i);
		}
		out.println("D " + y[low]);
		for (int i = 0; i < n; i++) {
			out.println(x[i] + " " + (2 * y[low] - 1 - y[i]));
		}
		int[] queue = new int[n];
		boolean[] mark = new boolean[n];
		queue[0] = low;
		mark[low] = true;
		int from = 0;
		int to = 1;
		for (int i = 0; i < k - n; i++) {
			int v = queue[from++];
			out.println(x[v] + " " + y[v]);
			for (int d = 0; d < 4; d++) {
				int xx = x[v] + DX[d];
				int yy = y[v] + DY[d];
				Integer u = map.get(code(xx, yy));
				if (u == null || mark[u]) {
					continue;
				}
				queue[to++] = u;
				mark[u] = true;
			}
		}
	}

	static long code(int x, int y) {
		return (((long) x) << 32L) + y;
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

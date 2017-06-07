package hackerrank.hourrank8;
import java.io.*;
import java.util.*;

public class C_slow {
	static final int INF = Integer.MAX_VALUE / 3;
	static final int[] dx = new int[]{1, 0, -1, 0};
	static final int[] dy = new int[]{0, 1, 0, -1};
	static final int G = 256;

	int hei, wid;
	int[][] a;
	int[][] dist;
	boolean[][] mark;

	void run() {
		hei = in.nextInt();
		wid = in.nextInt();
		a = new int[hei][wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = in.nextInt();
			}
		}
		dist = new int[hei][wid];
		mark = new boolean[hei][wid];
		
		int[][][][] s = new int[hei][wid][][];
		for (int j = G - 1; j < wid; j += G) {
			for (int i = 0; i < hei; i++) {
				dijkstra(i, j, 0, wid);
				s[i][j] = new int[hei][wid];
				for (int k = 0; k < hei; k++) {
					System.arraycopy(dist[k], 0, s[i][j][k], 0, wid);
				}
			}
		}
		
		int queries = in.nextInt();
		for (int q = 0; q < queries; q++) {
			int x1 = in.nextInt();
			int y1 = in.nextInt();
			int x2 = in.nextInt();
			int y2 = in.nextInt();
			if (y1 > y2) {
				int t = x1; x1 = x2; x2 = t;
				t = y1; y1 = y2; y2 = t;
			}
			
			int yLeft = (y2 + 1) - (y2 + 1) % G - 1;
			int yRight = yLeft + G;
			
			int ans = INF;
			for (int i = 0; i < hei; i++) {
				if (yLeft >= 0 && yLeft < wid) {
					ans = Math.min(ans, s[i][yLeft][x1][y1] + s[i][yLeft][x2][y2] - a[i][yLeft]);
				}
				if (yRight >= 0 && yRight < wid) {
					ans = Math.min(ans, s[i][yRight][x1][y1] + s[i][yRight][x2][y2] - a[i][yRight]);
				}
			}
			if (y1 > yLeft) {
				dijkstra(x1, y1, Math.max(yLeft + 1, 0), Math.min(yRight, wid));
				ans = Math.min(ans, dist[x2][y2]);
			}
			
			System.out.println(ans);
		}
	}

	void dijkstra(int x0, int y0, int from, int to) {
		for (int i = 0; i < hei; i++) {
			Arrays.fill(dist[i], from, to, INF);
			Arrays.fill(mark[i], from, to, false);
		}
		dist[x0][y0] = a[x0][y0];
		TreeSet<Long> queue = new TreeSet<>();
		queue.add(dist[x0][y0] * (long) hei * wid + x0 * wid + y0);
		while (!queue.isEmpty()) {
			long q = queue.pollFirst();
			int y = (int) (q % wid);
			q /= wid;
			int x = (int) (q % hei);
			q /= hei;
			if (mark[x][y]) {
				continue;
			}
			mark[x][y] = true;
			for (int d = 0; d < 4; d++) {
				int xx = x + dx[d];
				int yy = y + dy[d];
				if (xx < 0 || yy < from || xx >= hei || yy >= to || mark[xx][yy]) {
					continue;
				}
				int newDist = (int) q + a[xx][yy];
				if (newDist < dist[xx][yy]) {
					if (dist[xx][yy] < INF) {
						queue.remove(dist[xx][yy] * (long) hei * wid + xx * wid + yy);
					}
					queue.add(newDist * (long) hei * wid + xx * wid + yy);
					dist[xx][yy] = newDist;
				}
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C_slow.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new C_slow().run();
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

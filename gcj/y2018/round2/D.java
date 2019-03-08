package gcj.y2018.round2;
import java.io.*;
import java.util.*;

public class D {
	public static final int[] DX = new int[]{1, 0, -1, 0};
	public static final int[] DY = new int[]{0, 1, 0, -1};
	
	void run() {
		hei = in.nextInt();
		wid = in.nextInt();
		f = new boolean[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				f[i][j] = s.charAt(j) == 'B';
			}
		}
		boolean[] patternPresent = new boolean[16];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				int xx = Math.min(i + 1, hei - 1);
				int yy = Math.min(j + 1, wid - 1);
				int pattern = 0;
				if (f[i][j]) pattern += 1;
				if (f[i][yy]) pattern += 2;
				if (f[xx][j]) pattern += 4;
				if (f[xx][yy]) pattern += 8;
				patternPresent[pattern] = true;
			}
		}
		int ans = 0;
		mark = new boolean[hei][wid];
		for (x = 0; x <= hei; x++) {
			for (y = 0; y <= wid; y++) {
				for (int pattern = 0; pattern < 16; pattern++) {
					if (!patternPresent[pattern]) {
						continue;
					}
					small[0][0] = (pattern & 1) > 0;
					small[0][1] = ((pattern >> 1) & 1) > 0;
					small[1][0] = ((pattern >> 2) & 1) > 0;
					small[1][1] = ((pattern >> 3) & 1) > 0;
					for (int i = 0; i < hei; i++) {
						Arrays.fill(mark[i], false);
					}
					for (int i = 0; i < hei; i++) {
						for (int j = 0; j < wid; j++) {
							if (mark[i][j]) {
								continue;
							}
							comp = 0;
							dfs(i, j);
							ans = Math.max(ans, comp);
						}
					}
				}
			}
		}
		out.println(ans);
	}
	
	int hei, wid, x, y, comp;
	boolean[][] f;
	boolean[][] small = new boolean[2][2];
	boolean[][] mark;
	
	void dfs(int i, int j) {
		boolean color = small[i >= x ? 1 : 0][j >= y ? 1 : 0];
		if (f[i][j] != color) {
			return;
		}
		comp++;
		mark[i][j] = true;
		for (int d = 0; d < 4; d++) {
			int ii = i + DX[d];
			int jj = j + DY[d];
			if (ii < 0 || jj < 0 || ii >= hei || jj >= wid) {
				continue;
			}
			if (mark[ii][jj]) {
				continue;
			}
			dfs(ii, jj);
		}
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

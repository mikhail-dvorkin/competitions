package codeforces.abbyy2013.round1;
import java.io.*;
import java.util.*;

public class F {
	private static BufferedReader in;

	public static final int[] dx = new int[]{1, 0, -1, 0};
	public static final int[] dy = new int[]{0, 1, 0, -1};

	int hei, wid;
	boolean[][] a;
	boolean[][] b;
	boolean[][] mark;
	boolean[][] mark2;

	public void run() throws IOException {
		final int sqSize = 7;
		final int sqBrush = 8;
		StringTokenizer st = new StringTokenizer(in.readLine());
		hei = Integer.parseInt(st.nextToken());
		wid = Integer.parseInt(st.nextToken());
		a = new boolean[hei][wid];
		b = new boolean[hei][wid];
		mark = new boolean[hei][wid];
		mark2 = new boolean[hei][wid];
		for (int i = 0; i < hei; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j = 0; j < wid; j++) {
				a[i][j] = (Integer.parseInt(st.nextToken()) > 0);
			}
		}
		int[][] sq = new int[hei][wid];
		for (int i = 1; i < hei; i++) {
			for (int j = 1; j < wid; j++) {
				if (a[i][j]) {
					sq[i][j] = 1 + Math.min(sq[i - 1][j], Math.min(sq[i][j - 1], sq[i - 1][j - 1]));
					if (sq[i][j] >= sqSize) {
						int ic = i - sqSize / 2;
						int jc = j - sqSize / 2;
						for (int di = -sqBrush; di <= sqBrush; di++) {
							for (int dj = -sqBrush; dj <= sqBrush; dj++) {
								if (di * di + dj * dj > sqBrush * sqBrush) {
									continue;
								}
								int ii = ic + di;
								int jj = jc + dj;
								if (ii >= 0 && jj >= 0 && ii < hei && jj < wid) {
									b[ii][jj] = true;
								}
							}
						}
					}
				}
			}
		}
		ArrayList<Integer> rays = new ArrayList<>();
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (!a[i][j] || mark[i][j]) {
					continue;
				}
				ray = 0;
				dfs1(i, j);
				rays.add(ray);
			}
		}
		Collections.sort(rays);
		System.out.println(rays.size());
		for (int r : rays) {
			System.out.print(r + " ");
		}
		System.out.println();
	}

	int ray;

	private void dfs1(int i, int j) {
		mark[i][j] = true;
		if (!b[i][j] && !mark2[i][j]) {
			dfs2(i, j);
			ray++;
		}
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int jj = j + dy[d];
			if (ii >= 0 && jj >= 0 && ii < hei && jj < wid && a[ii][jj] && !mark[ii][jj]) {
				dfs1(ii, jj);
			}
		}
	}

	final int s = 1;

	private void dfs2(int i, int j) {
		mark2[i][j] = true;
		for (int d = 0; d < 4; d++) {
			for (int di = -s; di <= s; di++) {
				for (int dj = -s; dj <= s; dj++) {
					int ii = i + di;
					int jj = j + dj;
					if (ii >= 0 && jj >= 0 && ii < hei && jj < wid && a[ii][jj] && !b[ii][jj] && !mark2[ii][jj]) {
						dfs2(ii, jj);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new BufferedReader(new InputStreamReader(System.in));
		new F().run();
		in.close();
	}
}

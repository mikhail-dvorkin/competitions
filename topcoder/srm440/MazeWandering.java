package topcoder.srm440;
public class MazeWandering {
	char[][] f;
	boolean[][] mark;
	boolean[][] mark2;
	double[][] exp;
	int hei, wid, ci, cj;
	double answer = 0;
	int cells = 0;
	int[] dx = new int[]{1, 0, -1, 0};
	int[] dy = new int[]{0, 1, 0, -1};

	public double expectedTime(String[] maze) {
		hei = maze.length;
		wid = maze[0].length();
		f = new char[hei][];
		mark = new boolean[hei][wid];
		mark2 = new boolean[hei][wid];
		exp = new double[hei][wid];
		for (int i = 0; i < hei; i++) {
			f[i] = maze[i].toCharArray();
			for (int j = 0; j < wid; j++) {
				if (f[i][j] == '*') {
					ci = i;
					cj = j;
				}
			}
		}
		dfs(ci, cj);
		dfs2(ci, cj, 0);
		return answer / cells;
	}

	private void dfs(int i, int j) {
		mark[i][j] = true;
		int kids = 0;
		double sum = 0;
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int jj = j + dy[d];
			if (ii < 0 || jj < 0 || ii >= hei || jj >= wid)
				continue;
			if (f[ii][jj] == 'X')
				continue;
			if (mark[ii][jj])
				continue;
			dfs(ii, jj);
			kids++;
			sum += exp[ii][jj];
		}
		if (kids == 0) {
			exp[i][j] = 1;
		} else {
			exp[i][j] = 1 + kids + sum;
		}
		if (f[i][j] == '*')
			exp[i][j] = 0;
	}

	private void dfs2(int i, int j, double e) {
		mark2[i][j] = true;
		cells++;
		answer += exp[i][j] + e;
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int jj = j + dy[d];
			if (ii < 0 || jj < 0 || ii >= hei || jj >= wid)
				continue;
			if (f[ii][jj] == 'X')
				continue;
			if (mark2[ii][jj])
				continue;
			dfs2(ii, jj, exp[i][j] + e);
		}
	}
}

package topcoder.srm374;
import java.util.*;

public class PlayerExtraction {
	int hei;
	int wid;
	boolean[][] mark;
	String[] a;
	char find;
	int xmin, xmax, ymin, ymax, area;

	class Pair implements Comparable<Pair> {
		int x, y;

		public Pair(int xx, int yy) {
			x = xx;
			y = yy;
		}

		@Override
		public int compareTo(Pair o) {
			if (x == o.x)
				return y - o.y;
			return x - o.x;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	public String[] extractPlayers(String[] photo, int k, int threshold) {
		a = photo;
		hei = photo.length;
		wid = photo[0].length();
		mark = new boolean[hei][wid];
		find = (char) ('0' + k);
		ArrayList<Pair> answer = new ArrayList<Pair>();
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (!mark[i][j] && photo[i].charAt(j) == find) {
					xmin = ymin = 1000;
					xmax = ymax = -1000;
					area = 0;
					dfs(i, j);
					if (area * 4 >= threshold) {
						xmax++;
						ymax++;
						answer.add(new Pair(ymin + ymax, xmin + xmax));
					}
				}
			}
		}
		Collections.sort(answer);
		String[] ans = new String[answer.size()];
		for (int i = 0; i < ans.length; i++)
			ans[i] = answer.get(i).toString();
		return ans;
	}

	int[] dx = new int[]{1, 0, -1, 0};
	int[] dy = new int[]{0, 1, 0, -1};

	private void dfs(int i, int j) {
		if (i >= hei || j >= wid || i < 0 || j < 0 || mark[i][j] || a[i].charAt(j) != find)
			return;
		mark[i][j] = true;
		area++;
		xmin = Math.min(xmin, i);
		xmax = Math.max(xmax, i);
		ymin = Math.min(ymin, j);
		ymax = Math.max(ymax, j);
		for (int d = 0; d < 4; d++) {
			int ii = i + dx[d];
			int jj = j + dy[d];
			dfs(ii, jj);
		}
	}
}

package topcoder;
import java.util.*;

public class TheTilesDivOne {
	public static final int[] dx = new int[]{1, 0, -1, 0};
	public static final int[] dy = new int[]{0, 1, 0, -1};

	public int find(String[] board) {
		int hei = board.length;
		int wid = board[0].length();
		boolean[][] a = new boolean[hei][wid];
		Vertex[][] ver = new Vertex[hei][wid];
		Vertex[][] ver2 = new Vertex[hei][wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = board[i].charAt(j) == 'X';
				ver[i][j] = new Vertex();
				if ((i + j) % 2 == 0) {
					ver2[i][j] = new Vertex();
				}
			}
		}

		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (a[i][j]) {
					continue;
				}
				if ((i + j) % 2 == 0) {
					add(ver[i][j], ver2[i][j]);
					for (int d = 0; d < 4; d++) {
						int ii = i + dx[d];
						int jj = j + dy[d];
						if (ii < 0 || ii >= hei || jj < 0 || jj >= wid || a[ii][jj]) {
							continue;
						}
						if (ii % 2 == 0) {
							add(ver[ii][jj], ver[i][j]);

						} else {
							add(ver2[i][j], ver[ii][jj]);
						}
					}
				} else {
					if (i % 2 == 0) {
						add(s, ver[i][j]);
					} else {
						add(ver[i][j], t);
					}
				}
			}
		}
		
		return fordFulkerson();
	}
	
	void add(Vertex v, Vertex u) {
		Edge vu = new Edge(v, u, (byte) 1);
		Edge uv = new Edge(u, v, (byte) 0);
		vu.rev = uv;
		uv.rev = vu;
		v.adj.add(vu);
		u.adj.add(uv);
	}

	Vertex s = new Vertex(), t = new Vertex();
	int curTime;
	
	static class Vertex {
		List<Edge> adj = new ArrayList<Edge>();
		int time;
	}
	
	static class Edge {
		Vertex from, to;
		byte c, f;
		Edge rev;

		public Edge(Vertex from, Vertex to, byte c) {
			this.from = from;
			this.to = to;
			this.c = c;
		}
	}
	
	public int fordFulkerson() {
		for (int ans = 0;;) {
			curTime++;
			if (dfsFordFulkerson(s)) {
				ans++;
				continue;
			}
			return ans;
		}
	}
	
	public boolean dfsFordFulkerson(Vertex v) {
		if (v == t) {
			return true;
		}
		v.time = curTime;
		for (Edge e : v.adj) {
			Vertex u = e.to;
			if (e.f == e.c || u.time == curTime) {
				continue;
			}
			if (dfsFordFulkerson(u)) {
				e.f++;
				e.rev.f--;
				return true;
			}
		}
		return false;
	}
	
}

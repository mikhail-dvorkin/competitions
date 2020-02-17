package topcoder.srm479;
import java.util.*;

public class TheAirTripDivOne {
	class Edge {
		int a, b, f, t, p;

		public Edge(String s) {
			this(
					Integer.parseInt(s.split(",")[0]),
					Integer.parseInt(s.split(",")[1]),
					Integer.parseInt(s.split(",")[2]),
					Integer.parseInt(s.split(",")[3]),
					Integer.parseInt(s.split(",")[4])
				);
		}

		public Edge(int a, int b, int f, int t, int p) {
			this.a = a - 1;
			this.b = b - 1;
			this.f = f;
			this.t = t;
			this.p = p;
		}
	}

	@SuppressWarnings("unchecked")
	public int find(int n, String[] flights, int time) {
		ArrayList<Edge>[] edges = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<Edge>();
		}
		StringBuilder sb = new StringBuilder();
		for (String s : flights) {
			sb.append(s);
		}
		for (String s : sb.toString().split(" ")) {
			Edge e = new Edge(s);
			edges[e.a].add(e);
		}

		int[] dist = new int[n];
		boolean[] mark = new boolean[n];
		int le = 0;
		int ri = time;
		while (le + 1 < ri) {
			int wait = (le + ri) / 2;
			int inf = time + wait + 1;
			Arrays.fill(dist, inf);
			Arrays.fill(mark, false);
			dist[0] = 0;
			while (!mark[n - 1]) {
				int a = -1;
				for (int i = 0; i < n; i++) {
					if (!mark[i] && ((a == -1) || (dist[i] < dist[a]))) {
						a = i;
					}
				}
				int d = dist[a];
				if (d == inf) {
					break;
				}
				mark[a] = true;
				for (Edge e : edges[a]) {
					int ip = Math.max(d - e.f, 0);
					int mod = ip % e.p;
					if (mod != 0) {
						ip += e.p - mod;
					}
					long cand = (long) e.f + ip + e.t + wait;
					dist[e.b] = (int) Math.min(dist[e.b], cand);
				}
			}
			if (dist[n - 1] <= time + wait) {
				le = wait;
			} else {
				ri = wait;
			}
		}
		if (le == 0) {
			le = -1;
		}
		return le;
	}

}

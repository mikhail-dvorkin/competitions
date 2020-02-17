package topcoder;
import java.util.*;

public class ByteLand {
	int[] road;
	int[] distance;
	boolean[] castle;
	int k;
	int n;
	int d;
	
	public int buildCastles(int[] road_, int[] distance_, int[] cast, int k_) {
		this.road = road_;
		this.distance = distance_;
		this.k = k_;
		n = road.length;
		castle = new boolean[n];
		for (int x : cast)
			castle[x] = true;
		int le = 0; //imposs
		int ri = 100000000; // poss;
		while (le + 1 < ri) {
			d = (le + ri) / 2;
			if (poss())
				ri = d;
			else
				le = d;
		}
		System.out.println(ri);
		return ri;
	}

	boolean[] were;
	boolean[] were2;
	boolean[] gray;
	boolean[] inc;
	int[] tail;
	ArrayList<Integer> path;
	ArrayList<Integer> cycle;
	
	private boolean poss() {
		were = new boolean[n];
		were2 = new boolean[n];
		gray = new boolean[n];
		inc = new boolean[n];
		tail = new int[n];
		for (int i = 0; i < n; i++) {
			if (were[i])
				continue;
			path = new ArrayList<Integer>();
			cycle = new ArrayList<Integer>();
			dfs0(i, -1);
			for (int j : cycle) {
				inc[j] = true;
			}
			for (int j : cycle) {
				dfs1(j);
			}
		}
		return false;
	}
	
	private void dfs1(int v) {
		were2[v] = true;
		ArrayList<Integer> tls = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (road[v] == i || road[i] == v) {
//				int dst = (road[v] == i) ? distance[v] : distance[i];
				if (inc[i] || were2[v])
					continue;
				dfs1(i);
				tls.add(tail[i]);
			}
		}
	}

	private void dfs0(int v, int par) {
		were[v] = true;
		gray[v] = true;
		path.add(v);
		for (int i = 0; i < n; i++) {
			if (road[v] == i || road[i] == v) {
				if (road[v] == i && road[i] == v) {
					cycle.add(v);
					cycle.add(i);
					return;
				}
				if (i == par)
					continue;
				if (gray[i]) {
					boolean c = false;
					for (int x : path) {
						if (x == i)
							c = true;
						if (c)
							cycle.add(x);
					}
				}
				if (cycle.size() > 0)
					return;
				if (were[i])
					continue;
				dfs0(i, v);
				if (cycle.size() > 0)
					return;
			}
		}
		gray[v] = false;
		path.remove(path.size() - 1);
	}

	public static void main(String[] args) {
		new ByteLand().buildCastles(
				new int[]{1, 2, 3, 4, 0},
				new int[]{1, 1, 1, 1, 1},
				new int[]{},
				1
				);
	}
}

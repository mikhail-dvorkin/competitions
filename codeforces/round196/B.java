package codeforces.round196;
import java.util.*;

public class B {
	private static Scanner in;

	int n, d;
	boolean[] special;
	ArrayList<Integer>[] nei;
	int[] down;
	int ans = 0;

	@SuppressWarnings("unchecked")
	public void run() {
		n = in.nextInt();
		int m = in.nextInt();
		d = in.nextInt();
		nei = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		special = new boolean[n];
		for (int i = 0; i < m; i++) {
			special[in.nextInt() - 1] = true;
		}
		for (int i = 1; i < n; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		down = new int[n];
		Arrays.fill(down, -1);
		dfs1(0, -1);
		dfs2(0, -1, -1);
		System.out.println(ans);
	}

	void dfs2(int v, int p, int dist) {
		if (dist <= d && down[v] <= d) {
			ans++;
		}
		if (dist >= 0) {
			dist++;
		} else if (special[v]) {
			dist = 1;
		}
		int d1 = -1;
		int u1 = -1;
		int d2 = -1;
		for (int u : nei[v]) {
			if (u == p) {
				continue;
			}
			if (down[u] >= d1) {
				d2 = d1;
				d1 = down[u];
				u1 = u;
			} else if (down[u] >= d2) {
				d2 = down[u];
			}
		}
		for (int u : nei[v]) {
			if (u == p) {
				continue;
			}
			int di = d1;
			if (u == u1) {
				di = d2;
			}
			if (di >= 0) {
				di = Math.max(dist, di + 2);
			} else {
				di = dist;
			}
			dfs2(u, v, di);
		}
	}

	void dfs1(int v, int p) {
		for (int u : nei[v]) {
			if (u == p) {
				continue;
			}
			dfs1(u, v);
			down[v] = Math.max(down[v], down[u]);
		}
		if (down[v] >= 0) {
			down[v]++;
		} else if (special[v]) {
			down[v] = 0;
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

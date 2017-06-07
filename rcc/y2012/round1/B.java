package rcc.y2012.round1;
import java.util.*;

public class B {
	private static Scanner in;
	
	ArrayList<Integer>[] nei;
	boolean[] were;
	int[] p;
	
	HashMap<Long, Integer> cap = new HashMap<Long, Integer>();
	
	void put(long a, long b, int c) {
		long t = (a << 32L) + b;
		cap.put(t, c);
		t = (b << 32L) + a;
		cap.put(t, c);
	}
	
	int get(long a, long b) {
		long t = (a << 32L) + b;
		return cap.get(t);
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		int n = in.nextInt();
		int c1 = in.nextInt() - 1;
		int c2 = in.nextInt() - 1;
		nei = new ArrayList[n];
		were = new boolean[n];
		p = new int[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int c = in.nextInt();
			nei[a].add(b);
			nei[b].add(a);
			put(a, b, c);
		}
		dfs(c1);
		int straight = Integer.MAX_VALUE;
		int s1 = Integer.MAX_VALUE;
		int s2 = Integer.MAX_VALUE;
		int d1 = -1;
		int d2 = p[c2];
		for (int i = c2; i != c1; i = p[i]) {
			int j = p[i];
			if (j == c1) {
				d1 = i;
			}
			straight = Math.min(straight, get(i, j));
			if (i != c2) {
				s1 = Math.min(s1, get(i, j));
			}
			if (j != c1) {
				s2 = Math.min(s2, get(i, j));
			}
		}
		int best = straight;
		if (d2 != c1 && d2 != d1) {
			int cur = Math.min(get(c1, d1), get(c2, d2));
			best = Math.max(best, cur);
		}
		int e1 = -1;
		int e2 = -1;
		int bestEdge = -1;
		for (int v : nei[c1]) {
			if (v == d1) {
				continue;
			}
			if (get(c1, v) > bestEdge) {
				bestEdge = get(c1, v);
				e1 = v;
			}
		}
		bestEdge = -1;
		for (int v : nei[c2]) {
			if (v == d2) {
				continue;
			}
			if (get(c2, v) > bestEdge) {
				bestEdge = get(c2, v);
				e2 = v;
			}
		}
		if (e1 != -1 && e2 != -1){
			int cur = straight + Math.min(get(c1, e1), get(c2, e2));
			best = Math.max(best, cur);
		}
		if (d1 != c1) {
			if (e2 != -1) {
				int cur = Math.min(get(c1, d1), s2 + get(c2, e2));
				best = Math.max(best, cur);
			}
			if (e1 != -1) {
				int cur = Math.min(get(c2, d2), s1 + get(c1, e1));
				best = Math.max(best, cur);
			}
		}
		System.out.println(best);
	}

	private void dfs(int v) {
		were[v] = true;
		for (int u : nei[v]) {
			if (were[u]) {
				continue;
			}
			p[u] = v;
			dfs(u);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

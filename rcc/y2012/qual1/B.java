package rcc.y2012.qual1;
import java.util.*;

public class B {
	private static Scanner in;
	
	int n, m;
	ArrayList<Integer>[] nei;
	int time;
	boolean[] were;
	int[] size;
	int[] tIn;
	int[] f;
	int bridges;
	long ans;

	@SuppressWarnings("unchecked")
	public void run() {
		n = in.nextInt();
		m = in.nextInt();
		nei = new ArrayList[n];
		were = new boolean[n];
		size = new int[n];
		tIn = new int[n];
		f = new int[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<Integer>();
		}
		if (m < n - 1) {
			System.out.println(0);
			return;
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		ArrayList<Integer> roots = new ArrayList<Integer>();
		for (int i=0; i<n; ++i) {
			if (!were[i]) {
				roots.add(i);
				dfs(i, -1);
			}
		}
		if (roots.size() > 2) {
			System.out.println(0);
			return;
		}
		if (roots.size() == 2) {
			System.out.println((m - bridges) * 1L * size[roots.get(0)] * size[roots.get(1)]);
			return;
		}
		ans += (m - bridges) * 1L * ((n * 1L * (n - 1) / 2) - m);
		System.out.println(ans);
	}

	private void dfs(int v, int p) {
		were[v] = true;
		size[v] = 1;
		tIn[v] = time;
		f[v] = time;
		time++;
		for (int u : nei[v]) {
			if (u == p) {
				continue;
			}
			if (were[u]) {
				f[v] = Math.min(f[v], tIn[u]);
			} else {
				dfs(u, v);
				size[v] += size[u];
				f[v] = Math.min(f[v], f[u]);
				if (f[u] > tIn[v]) {
					bridges++;
					ans += size[u] * 1L * (n - size[u]) - 1;
				}
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new B().run();
		in.close();
	}
}

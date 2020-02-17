package codeforces.yandex2011.qual2;
import java.util.*;

public class C {
	private static Scanner in;

	int n;
	int[] p;
	Set<Integer>[] nei;
	TreeSet<Integer>[] city;
	final Map<Integer, Integer> from = new TreeMap<>();
	int[] level;
	int[] parent;
	int[] list;
	int len;

	@SuppressWarnings("unchecked")
	public void run() {
		n = in.nextInt();
		p = new int[n];
		level = new int[n];
		parent = new int[n];
		nei = new Set[n];
		city = new TreeSet[n];
		for (int i = 0; i < n; i++) {
			p[i] = in.nextInt();
			nei[i] = new HashSet<>();
			city[i] = new TreeSet<>();
			city[i].add(p[i]);
			from.put(p[i], i);
		}
		int[] u = new int[n - 1];
		int[] v = new int[n - 1];
		int[] c = new int[n - 1];
		for (int i = 0; i < n - 1; i++) {
			u[i] = in.nextInt() - 1;
			v[i] = in.nextInt() - 1;
			nei[v[i]].add(u[i]);
			nei[u[i]].add(v[i]);
			c[i] = in.nextInt();
		}
		Arrays.fill(level, -1);
		parent[0] = -1;
		level[0] = 0;
		list = new int[n];
		len = 0;
		dfs(0);
		int[] cap = new int[n];
		for (int i = 0; i < n - 1; i++) {
			int w;
			if (u[i] == parent[v[i]]) {
				w = v[i];
			} else {
				w = u[i];
			}
			cap[w] = c[i];
		}
		int[] ans = new int[n];
		for (int t = 0; city[0].size() < n; t++) {
			for (int i = 1; i < n; i++) {
				int w = list[i];
				for (int j = 0; j < cap[w]; j++) {
					if (city[w].isEmpty()) {
						break;
					}
					int f = city[w].first();
					city[w].remove(f);
					city[parent[w]].add(f);
					if (parent[w] == 0) {
						ans[from.get(f)] = t + 1;
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			System.out.print(ans[i]);
			if (i < n - 1) {
				System.out.print(" ");
			} else
				System.out.println();
		}
	}

	private void dfs(int v) {
		list[len++] = v;
		for (int u : nei[v]) {
			if (level[u] < 0) {
				level[u] = level[v] + 1;
				parent[u] = v;
				dfs(u);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}

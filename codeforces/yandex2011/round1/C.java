package codeforces.yandex2011.round1;
import java.util.*;

public class C {
	private static Scanner in;

	int n;
	int[] key;
	int[] parent;
	int[] left;
	int[] right;
	int[] from;
	int[] to;
	int[] size;
	int root;

	public void run() {
		n = in.nextInt();
		key = new int[n];
		parent = new int[n];
		left = new int[n];
		right = new int[n];
		from = new int[n];
		to = new int[n];
		size = new int[n];
		Arrays.fill(parent, -1);
		Arrays.fill(left, -1);
		Arrays.fill(right, -1);
		for (int i = 0; i < n; i++) {
			int p = parent[i] = in.nextInt() - 1;
			key[i] = in.nextInt();
			if (p < 0) {
				root = i;
			} else {
				if (left[p] == -1) {
					left[p] = i;
				} else {
					if (key[i] < key[left[p]]) {
						right[p] = left[p];
						left[p] = i;
					} else {
						right[p] = i;
					}
				}
			}
		}
		dfs1(root);
		int m = in.nextInt();
		int[] a = new int[m];
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt();
		}
		int[] aa = a.clone();
		Arrays.sort(a);
		amount = new int[m + 1];
		sum = new long[m + 1];
		dfs2(root, a, 0, m);
		int am = 0;
		long s = 0;
		HashMap<Integer, Double> answer = new HashMap<>();
		for (int i = 0; i < m; i++) {
			am += amount[i];
			s += sum[i];
			answer.put(a[i], s * 1d / am);
		}
		for (int x : aa) {
			System.out.println(answer.get(x));
		}
	}

	@SuppressWarnings("SuspiciousNameCombination")
	private void dfs2(int v, int[] a, int x, int z) {
		if (left[v] == -1) {
			return;
		}
		int y = - 1 - Arrays.binarySearch(a, /*x, z,*/ key[v]);
		y = Math.max(x, y);
		y = Math.min(z, y);
		add(x, y, from[right[v]]);
		add(y, z, to[left[v]]);
		dfs2(left[v], a, x, y);
		dfs2(right[v], a, y, z);
	}

	int[] amount;
	long[] sum;

	private void add(int inc, int dec, int value) {
		amount[inc]++;
		sum[inc] += value;
		amount[dec]--;
		sum[dec] -= value;
	}

	private void dfs1(int v) {
		if (left[v] == -1) {
			from[v] = to[v] = key[v];
			size[v] = 1;
			return;
		}
		dfs1(left[v]);
		dfs1(right[v]);
		from[v] = from[left[v]];
		to[v] = to[right[v]];
		size[v] = 1 + size[left[v]] + size[right[v]];
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}

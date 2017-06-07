package codeforces.round170;
import java.util.*;

public class A {
	private static Scanner in;

	public void run() {
		n = in.nextInt();
		m = in.nextInt();
		knows = new boolean[n][m];
		boolean any = false;
		for (int i = 0; i < n; i++) {
			int k = in.nextInt();
			for (int j = 0; j < k; j++) {
				knows[i][in.nextInt() - 1] = true;
				any = true;
			}
		}
		if (!any) {
			System.out.println(n);
			return;
		}
		were = new boolean[n];
		int comps = 0;
		for (int i = 0; i < n; i++) {
			if (were[i]) {
				continue;
			}
			comps++;
			dfs(i);
		}
		System.out.println(comps - 1);
	}
	
	void dfs(int v) {
		if (were[v]) {
			return;
		}
		were[v] = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (knows[v][j] && knows[i][j]) {
					dfs(i);
				}
			}
		}
	}

	int n, m;
	boolean[][] knows;
	boolean[] were;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new A().run();
		in.close();
	}
}

package codeforces.beta_round80;
import java.util.*;

public class B {
	private static Scanner in;

	final String no = "NO";
	final String yes = "FHTAGN!";

	int n;
	ArrayList<Integer>[] nei;
	boolean[] mark;

	@SuppressWarnings("unchecked")
	public void run() {
		n = in.nextInt();
		if (n != in.nextInt()) {
			System.out.println(no);
			return;
		}
		nei = new ArrayList[n];
		mark = new boolean[n];
		for (int i = 0; i < n; i++) {
			nei[i] = new ArrayList<>();
		}
		for (int i = 0; i < n; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			nei[a].add(b);
			nei[b].add(a);
		}
		dfs(0);
		for (int i = 0; i < n; i++) {
			if (!mark[i]) {
				System.out.println(no);
				return;
			}
		}
		System.out.println(yes);
	}

	private void dfs(int v) {
		mark[v] = true;
		for (int u : nei[v]) {
			if (!mark[u]) {
				dfs(u);
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

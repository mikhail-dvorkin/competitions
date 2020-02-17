package codeforces.yandex2011.round2;
import java.util.*;

public class C {
	private static Scanner in;

	class State {
		String s;
		int c;

		public State(String s, int c) {
			this.s = s;
			this.c = c;
		}

		@Override
		public int hashCode() {
			return 13 * s.hashCode() + c;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			State that = (State) o;
			return c == that.c && Objects.equals(s, that.s);
		}

		boolean isTerminal() {
			return s.length() == c;
		}

		State step(char n) {
			int c1 = c;
			String ss = s + n;
			for (;;) {
				int cbest = c1;
				for (String d : dna) {
					for (int i = 0; i <= c1; i++) {
						if (ss.substring(i).startsWith(d)) {
							cbest = Math.max(cbest, i + d.length());
						}
					}
				}
				if (cbest == c1) {
					break;
				}
				c1 = cbest;
			}
			loop:
			for (;;) {
				for (String d : dna) {
					if (d.startsWith(ss)) {
						break loop;
					}
				}
				ss = ss.substring(1);
				c1--;
			}
			if (c1 < 0) {
				return null;
			}
			return new State(ss, c1);
		}
	}

	String[] dna;
	HashMap<State, Integer> map = new HashMap<>();
	int[][] nxt = new int[2000][4];
	boolean[] trm = new boolean[2000];
	char[] ns = new char[]{'A', 'C', 'G', 'T'};

	public void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		dna = new String[m];
		for (int i = 0; i < m; i++) {
			dna[i] = in.next();
		}
		State s0 = new State("", 0);
		dfs(s0);
		int k = map.size();
		int[][] a = new int[n + 1][k];
		a[0][0] = 1;
		for (int len = 0; len < n; len++) {
			for (int s = 0; s < k; s++) {
				int aa = a[len][s];
				if (aa == 0) {
					continue;
				}
				for (int i = 0; i < 4; i++) {
					int t = nxt[s][i];
					if (t < 0) {
						continue;
					}
					a[len + 1][t] = add(a[len + 1][t], aa);
				}
			}
		}
		int ans = 0;
		for (int s = 0; s < k; s++) {
			if (trm[s]) {
				ans = add(ans, a[n][s]);
			}
		}
		System.out.println(ans);
	}

	final int MOD = 1000000009;

	private int add(int x, int value) {
		x += value;
		if (x >= MOD) {
			x -= MOD;
		}
		return x;
	}

	private void dfs(State st) {
		if (map.containsKey(st)) {
			return;
		}
		int v = map.size();
		map.put(st, v);
		trm[v] = st.isTerminal();
		for (int i = 0; i < 4; i++) {
			State u = st.step(ns[i]);
			if (u == null) {
				nxt[v][i] = -1;
				continue;
			}
			dfs(u);
			nxt[v][i] = map.get(u);
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		in = new Scanner(System.in);
		new C().run();
		in.close();
	}
}

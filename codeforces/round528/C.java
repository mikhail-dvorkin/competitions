package codeforces.round528;
import java.io.*;
import java.util.*;

public class C {
	int k;
	int[] s, a, b;

	void run() {
		k = in.nextInt();
		s = read();
		a = read();
		b = read();
		solve();
	}

	boolean solve() {
		int[] p = new int[k];
		Arrays.fill(p, -1);
		int[] q = new int[k];
		Arrays.fill(q, -1);
		p = solve(p, q, 0, false, false);
		if (p == null) {
			out.println("NO");
			return false;
		}
		out.println("YES");
		for (int i = 0; i < k; i++) {
			out.print((char) ('a' + p[i]));
		}
		out.println();
		/*
		int[] t = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			t[i] = p[s[i]];
		}
		boolean la = false;
		boolean lb = false;
		for (int i = 0; i < s.length; i++) {
			if (!la) {
				if (t[i] < a[i]) {
					throw new RuntimeException();
				}
				if (t[i] > a[i]) {
					la = true;
				}
			}
			if (!lb) {
				if (t[i] > b[i]) {
					throw new RuntimeException();
				}
				if (t[i] < b[i]) {
					lb = true;
				}
			}
		}
		*/
		return true;
	}

	int[] solve(int[] p, int[] q, int x, boolean happyA, boolean happyB) {
		main:
		for (;; x++) {
			if (x == s.length || happyA && happyB) {
				for (int i = 0, t = 0; i < k; i++) {
					if (p[i] >= 0) {
						continue;
					}
					while (q[t] >= 0) {
						t++;
					}
					p[i] = t;
					q[t] = i;
				}
				return p;
			}
			if (p[s[x]] != -1) {
				if (!happyA) {
					if (p[s[x]] < a[x]) {
						return null;
					}
					if (p[s[x]] > a[x]) {
						happyA = true;
					}
				}
				if (!happyB) {
					if (p[s[x]] > b[x]) {
						return null;
					}
					if (p[s[x]] < b[x]) {
						happyB = true;
					}
				}
				continue;
			}
			if (happyA) {
				for (int i = 0; i < b[x]; i++) {
					if (q[i] >= 0) {
						continue;
					}
					p[s[x]] = i;
					q[i] = s[x];
					happyB = true;
					continue main;
				}
				if (q[b[x]] >= 0) {
					return null;
				}
				p[s[x]] = b[x];
				q[b[x]] = s[x];
				continue;
			}
			if (happyB) {
				for (int i = a[x] + 1; i < k; i++) {
					if (q[i] >= 0) {
						continue;
					}
					p[s[x]] = i;
					q[i] = s[x];
					happyA = true;
					continue main;
				}
				if (q[a[x]] >= 0) {
					return null;
				}
				p[s[x]] = a[x];
				q[a[x]] = s[x];
				continue;
			}
			if (a[x] > b[x]) {
				return null;
			}
			for (int i = a[x] + 1; i < b[x]; i++) {
				if (q[i] >= 0) {
					continue;
				}
				p[s[x]] = i;
				q[i] = s[x];
				happyA = happyB = true;
				continue main;
			}
			if (a[x] == b[x]) {
				if (q[a[x]] >= 0) {
					return null;
				}
				p[s[x]] = a[x];
				q[a[x]] = s[x];
				continue;
			}
			if (q[a[x]] == -1) {
				int[] pp = p.clone();
				int[] qq = q.clone();
				pp[s[x]] = a[x];
				qq[a[x]] = s[x];
				int[] res = solve(pp, qq, x + 1, false, true);
				if (res != null) {
					return res;
				}
			}
			if (q[b[x]] == -1) {
				int[] pp = p.clone();
				int[] qq = q.clone();
				pp[s[x]] = b[x];
				qq[b[x]] = s[x];
				return solve(pp, qq, x + 1, true, false);
			}
			return null;
		}
	}

	int[] read() {
		String line = in.next();
		int[] array = new int[line.length()];
		for (int i = 0; i < array.length; i++) {
			array[i] = line.charAt(i) - 'a';
		}
		return array;
	}

	boolean solveSlow() {
		int[] p = new int[k];
		Arrays.fill(p, -1);
		int[] q = new int[k];
		Arrays.fill(q, -1);
		return search(p, q, 0);
	}

	boolean search(int[] p, int[] q, int x) {
		if (x == k) {
			int[] t = new int[s.length];
			for (int i = 0; i < s.length; i++) {
				t[i] = p[s[i]];
			}
			boolean la = false;
			boolean lb = false;
			boolean wrong = false;
			for (int i = 0; i < s.length; i++) {
				if (!la) {
					if (t[i] < a[i]) {
						wrong = true;
					}
					if (t[i] > a[i]) {
						la = true;
					}
				}
				if (!lb) {
					if (t[i] > b[i]) {
						wrong = true;
					}
					if (t[i] < b[i]) {
						lb = true;
					}
				}
			}
			return !wrong;
		}
		for (int i = 0; i < k; i++) {
			if (q[i] >= 0) {
				continue;
			}
			p[x] = i;
			q[i] = x;
			if (search(p, q, x + 1)) {
				return true;
			}
			p[x] = -1;
			q[i] = -1;
		}
		return false;
	}

	void stress() {
		Random r = new Random(566);
		//noinspection InfiniteLoopStatement
		for (;;) {
			k = 4;
			int n = 12;
			s = new int[n];
			a = new int[n];
			b = new int[n];
			for (int i = 0; i < n; i++) {
				s[i] = r.nextInt(k);
				a[i] = r.nextInt(k);
				b[i] = r.nextInt(k);
			}
			boolean x = solve();
			boolean y = solveSlow();
			if (x ^ y) {
				System.out.println(Arrays.toString(s));
				System.out.println(Arrays.toString(a));
				System.out.println(Arrays.toString(b));
				throw new RuntimeException(x + " " + y);
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";

		Locale.setDefault(Locale.US);
		BufferedReader br;
		//noinspection ConstantConditions
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
//		stress();
		in = new MyScanner(br);
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			//out.print("Case #" + (test + 1) + ": ");
			new C().run();
		}
		br.close();
		out.close();
	}

	static class MyScanner {
		final BufferedReader br;
		StringTokenizer st;

		MyScanner(BufferedReader br) {
			this.br = br;
		}

		void findToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		String next() {
			findToken();
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}

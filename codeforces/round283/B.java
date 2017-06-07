package codeforces.round283;
import java.io.*;
import java.util.*;

public class B {
	int n;
	int[] a;
	int[] c;
	
	int playFrom(int i, int t) {
		int low = i;
		int high = n + 1;
		while (low + 1 < high) {
			int m = (low + high) / 2;
			int win1 = c[m] - c[i];
			int win0 = m - i - win1;
			if (Math.max(win0, win1) < t) {
				low = m;
			} else {
				high = m;
			}
		}
		return high;
	}
	
	void run() {
		n = in.nextInt();
		a = new int[n];
		c = new int[n + 1];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			c[i + 1] = c[i] + a[i];
		}
		List<Pair> ans = new ArrayList<>();
		for (int t = 1; t <= n; t++) {
			int[] score = new int[2];
			for (int i = 0; i < n;) {
				int j = playFrom(i, t);
				if (j > n) {
					break;
				}
				int winner = a[j - 1];
				score[winner]++;
				if (j == n) {
					if (score[winner] > score[1 - winner]) {
						ans.add(new Pair(score[winner], t));
					}
					break;
				}
				i = j;
			}
		}
		Collections.sort(ans);
		out.println(ans.size());
		for (Pair p : ans) {
			out.println(p);
		}
	}
	
	class Pair implements Comparable<Pair> {
		int s, t;

		public Pair(int s, int t) {
			this.s = s;
			this.t = t;
		}

		@Override
		public int compareTo(Pair o) {
			if (s != o.s) {
				return Integer.compare(s, o.s);
			}
			return Integer.compare(t, o.t);
		}
		
		@Override
		public String toString() {
			return s + " " + t;
		}
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = new MyScanner(br);
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new B().run();
		}
		br.close();
		out.close();
	}
	
	static class MyScanner {
		BufferedReader br;
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
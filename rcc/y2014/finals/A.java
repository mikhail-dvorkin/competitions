package rcc.y2014.finals;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		n = in.nextInt();
		int m = in.nextInt();
		int hints = in.nextInt();
		BitSet[] a = new BitSet[n];
		boolean[] known = new boolean[hints];
		for (int i = 0; i < n; i++) {
			a[i] = new BitSet();
			for (int k = in.nextInt(); k > 0; k--) {
				int h = in.nextInt() - 1;
				a[i].set(h);
				known[h] = true;
			}
		}
		BitSet[] b = new BitSet[m];
		for (int i = 0; i < m; i++) {
			b[i] = new BitSet();
			for (int k = in.nextInt(); k > 0; k--) {
				int h = in.nextInt() - 1;
				b[i].set(h);
			}
		}
		unknown = 0;
		int[] renumber = new int[hints];
		for (int i = 0; i < hints; i++) {
			if (!known[i]) {
				renumber[i] = unknown;
				unknown++;
			}
		}
		if (unknown > n) {
			System.out.println(2);
			return;
		}
		int[][] how = new int[n][unknown];
		e = new boolean[n][unknown];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				BitSet bitSet = (BitSet) b[j].clone();
				bitSet.andNot(a[i]);
				if (bitSet.cardinality() == 1) {
					int h = bitSet.nextSetBit(0);
					if (!known[h]) {
						how[i][renumber[h]] = j;
						e[i][renumber[h]] = true;
					}
				}
			}
		}
		int match = solve();
		if (match < unknown) {
			out.println(2);
			return;
		}
		out.println(1);
		int[] ask = new int[n];
		for (int h = 0; h < hints; h++) {
			if (known[h]) {
				continue;
			}
			int j = renumber[h];
			int i = left[j];
			ask[i] = how[i][j];
		}
		for (int i = 0; i < n; i++) {
			out.print(ask[i] + 1 + " ");
		}
		out.println();
	}
	
	public int n, unknown;
	public boolean[][] e;
	
	private boolean[] mark;
	private int[] left;
	
	public int solve() {
		mark = new boolean[n];
		left = new int[unknown];
		Arrays.fill(left, -1);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			Arrays.fill(mark, false);
			if (dfs(i)) {
				ans++;
			}
		}
		return ans;
	}

	private boolean dfs(int i) {
		if (mark[i]) {
			return false;
		}
		mark[i] = true;
		for (int j = 0; j < unknown; j++) {
			if (!e[i][j]) {
				continue;
			}
			if (left[j] == -1 || dfs(left[j])) {
				left[j] = i;
				return true;
			}
		}
		return false;
	}

	static boolean stdStreams = true;
	static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new A().run();
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

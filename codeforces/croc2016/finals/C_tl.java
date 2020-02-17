package codeforces.croc2016.finals;
import java.io.*;
import java.util.*;

public class C_tl {
	static final long TIME_END = System.currentTimeMillis() + 5800;

	void run() {
		Random r = new Random(566);
		int n = in.nextInt(); // <= 20
		int m = in.nextInt();
		byte[][] a = new byte[n][m];
		for (int i = 0; i < n; i++) {
			String s = in.next();
			for (int j = 0; j < m; j++) {
				a[i][j] = (byte) (s.charAt(j) - '0');
			}
		}
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			int v = 0;
			for (int j = 0; j < n; j++) {
				v <<= 1;
				v += a[j][i];
			}
			list.add(v);
		}
		Collections.shuffle(list, r);
		int[] array = new int[m];
		for (int i = 0; i < m; i++) {
			array[i] = list.get(i);
		}
		int ans = n * m;
		int max = array.length + (1 << n);
		boolean[] seen = new boolean[1 << n];
		for (int i = 0; i < max; i++) {
			if (System.currentTimeMillis() > TIME_END) {
				break;
			}
			int c;
			if (i < array.length) {
				c = array[i];
			} else {
				c = array[0] ^ (i - array.length);
			}
			if (seen[c]) {
				continue;
			}
			seen[c] = true;
			int res = 0;
			for (int d : list) {
				int ones = Integer.bitCount(c ^ d);
				if ((ones << 1) <= n) {
					res += ones;
				} else {
					res += n - ones;
				}
			}
			if (res < ans) {
				ans = res;
			}
		}
		System.out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C_tl.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";

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
			new C_tl().run();
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

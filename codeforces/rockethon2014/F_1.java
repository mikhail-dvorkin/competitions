package codeforces.rockethon2014;
import java.io.*;
import java.util.*;

public class F_1 {
	void run() {
		int n = in.nextInt();
		int k = in.nextInt();
		long[] p = new long[n];
		for (int i = 0; i < n; i++) {
			p[i] = in.nextLong();
		}
		List<Long> seg = new ArrayList<>();
		{
			int i = 0;
			while (i + 1 < n && p[i] >= p[i + 1]) {
				i++;
			}
			int start = i;
			while (i + 1 < n && p[i] <= p[i + 1]) {
				i++;
			}
			if (p[i] > p[start]) {
				seg.add(p[i] - p[start]);
				while (i + 2 < n) {
					start = i;
					while (i + 1 < n && p[i] >= p[i + 1]) {
						i++;
					}
					int mid = i;
					while (i + 1 < n && p[i] <= p[i + 1]) {
						i++;
					}
					int fin = i;
					if (p[start] > p[mid] && p[mid] < p[fin]) {
						seg.add(p[mid] - p[start]);
						seg.add(p[fin] - p[mid]);
					}
				}
			}
		}
		if (seg.isEmpty()) {
			System.out.println(0);
			return;
		}
		int pos = (seg.size() + 1) / 2;
		k = Math.min(k, pos);
		long[][] a = new long[pos + 1][k + 1];
		long[][] b = new long[pos + 1][k + 1];
		for (int i = 0; i < a.length; i++) {
			Arrays.fill(a[i], -1);
			Arrays.fill(b[i], -1);
		}
//		System.out.println(seg);
		a[0][0] = 0;
		for (int i = 0; i < pos; i++) {
			for (int j = 0; j <= k; j++) {
				if (b[i][j] != -1) {
					a[i][j] = Math.max(a[i][j], b[i][j]);
					b[i + 1][j] = Math.max(b[i + 1][j], b[i][j] + seg.get(2 * i - 1) + seg.get(2 * i));
				}
				if (a[i][j] != -1) {
					a[i + 1][j] = Math.max(a[i + 1][j], a[i][j]);
					if (j < k) {
						b[i + 1][j + 1] = Math.max(b[i + 1][j + 1], a[i][j] + seg.get(2 * i));
					}
				}
			}
		}
		long ans = 0;
		for (int j = 0; j <= k; j++) {
			ans = Math.max(ans, a[pos][j]);
			ans = Math.max(ans, b[pos][j]);
		}
		System.out.println(ans);
	}

	static final boolean stdStreams = true;
	static final String fileName = F_1.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static final String inputFileName = fileName + ".in";
	static final String outputFileName = fileName + ".out";
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
		new F_1().run();
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

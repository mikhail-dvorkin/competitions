package gcj.y2018.round1b;

import java.io.*;
import java.util.*;

public class C_medium {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			b[i] = in.nextInt() - 1;
		}
		long total = 0;
		long[] g = new long[n];
		for (int i = 0; i < n; i++) {
			g[i] = in.nextInt();
			total += g[i];
		}
		long[] f = new long[n];
		f[0] = 1;
		long ans = 0;
//		TreeSet<Integer> danger = new TreeSet<>(); 
		for (;;) {
			int bad = -1;
			long lack = 0;
			long count = Long.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (f[i] == 0) {
					continue;
				}
				long curLack = f[i] - g[i];
				if (curLack > lack) {
					lack = curLack;
					bad = i;
				}
				count = Math.min(count, g[i] / f[i]);
			}
			if (count > 0) {
				for (int i = 0; i < n; i++) {
					g[i] -= f[i] * count;
				}
				ans += count;
//				danger.clear();
				continue;
			}
//			if (danger.contains(bad)) {
//				break;
//			}
//			danger.add(bad);
			f[bad] -= lack;
			f[a[bad]] += lack;
			if (f[a[bad]] > total) {
				break;
			}
			f[b[bad]] += lack;
			if (f[b[bad]] > total) {
				break;
			}
		}
		out.println(ans);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C_medium.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
			new C_medium().run();
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

package codeforces.round385;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int colored = 0;
		int[] cost0 = new int[n];
		int[] cost1 = new int[n];
		int total0 = 0, total1 = 0;
		for (int i = 0; i < n; i++) {
			if (in.next().equals("B")) {
				colored |= 1 << i;
			}
			cost0[i] = in.nextInt();
			cost1[i] = in.nextInt();
			total0 += cost0[i];
			total1 += cost1[i];
		}
		int maxGain = n * (n - 1) / 2;
		int[][] a = new int[1 << n][maxGain + 1];
		for (int[] row : a) {
			Arrays.fill(row, -1);
		}
		a[0][0] = 0;
		int ans = Integer.MAX_VALUE;
		for (int mask = 0; mask < a.length; mask++) {
			int count1 = Integer.bitCount(mask & colored);
			int count0 = Integer.bitCount(mask) - count1;
			for (int gain0 = 0; gain0 <= maxGain; gain0++) {
				int gain1 = a[mask][gain0];
				if (gain1 == -1) {
					continue;
				}
				for (int k = 0; k < n; k++) {
					int next = mask | (1 << k);
					if (next == mask) {
						continue;
					}
					int xx = gain0 + Math.min(count0, cost0[k]);
					int yy = gain1 + Math.min(count1, cost1[k]);
					a[next][xx] = Math.max(a[next][xx], yy);
				}
				if (mask == a.length - 1) {
					ans = Math.min(ans, Math.max(total0 - gain0, total1 - gain1));
				}
			}
		}
		out.println(ans + n);
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
		in = new MyScanner(br);
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
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

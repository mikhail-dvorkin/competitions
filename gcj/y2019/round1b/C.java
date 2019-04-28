package gcj.y2019.round1b;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int diff = in.nextInt();
		SegmentsTree[] st = new SegmentsTree[2];
		for (int j = 0; j < 2; j++) {
			st[j] = new SegmentsTree(n);
			for (int i = 0; i < n; i++) {
				st[j].set(i, in.nextInt());
			}
		}
		out.println(n * (n + 1L) / 2 - wins(st[0], st[1], diff) - wins(st[1], st[0], diff));
	}

	static long wins(SegmentsTree a, SegmentsTree b, int diff) {
		long ans = 0;
		for (int i = 0; i < a.n; i++) {
			int ai = a.get(i);
			if (b.get(i) >= ai - diff) {
				continue;
			}
			int x, y;
			{
				int low = -1;
				int high = i;
				while (low + 1 < high) {
					int mid = (low + high) / 2;
					int aMax = a.getMax(mid, i);
					int bMax = b.getMax(mid, i);
					if (aMax < ai && bMax < ai - diff) {
						high = mid;
					} else {
						low = mid;
					}
				}
				x = high;
			}
			{
				int low = i + 1;
				int high = a.n + 1;
				while (low + 1 < high) {
					int mid = (low + high) / 2;
					int aMax = a.getMax(i + 1, mid);
					int bMax = b.getMax(i + 1, mid);
					if (aMax <= ai && bMax < ai - diff) {
						low = mid;
					} else {
						high = mid;
					}
				}
				y = low - 1;
			}
			ans += (i - x + 1L) * (y - i + 1L);
		}
		return ans;
	}

	static class SegmentsTree {
		int n;
		int[] max;
		int size;

		public SegmentsTree(int n) {
			this.n = n;
			size = 1;
			while (size <= n) {
				size *= 2;
			}
			max = new int[2 * size];
		}

		int get(int index) {
			return max[size + index];
		}

		void set(int index, int value) {
			int i = size + index;
			max[i] = value;
			while (i > 1) {
				i /= 2;
				max[i] = Math.max(max[2 * i], max[2 * i + 1]);
			}
		}

		int getMax(int from, int to) {
			from += size;
			to += size;
			int res = Integer.MIN_VALUE;
			while (from < to) {
				if (from % 2 == 1) {
					res = Math.max(res, max[from]);
					from++;
				}
				if (to % 2 == 1) {
					to--;
					res = Math.max(res, max[to]);
				}
				from /= 2;
				to /= 2;
			}
			return res;
		}
	}

	static long slow(int[] a, int[] b, int maxDiff) {
		int n = a.length;
		long ans = 0;
		for (int i = 0; i < n; i++) {
			int maxA = -1;
			int maxB = -1;
			for (int j = i; j < n; j++) {
				if (a[j] > maxA) maxA = a[j];
				if (b[j] > maxB) maxB = b[j];
				if (Math.abs(maxA - maxB) <= maxDiff) {
					ans++;
				}
			}
		}
		return ans;
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
			new C().run();
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

package roi.y2019;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int queries = in.nextInt();
		SegmentsTree a = new SegmentsTree(hei);
		SegmentsTree b = new SegmentsTree(wid);
		for (int time = 1; time <= queries; time++) {
			int type = in.nextInt();
			if (type == 1) {
				int x = in.nextInt() - 1;
				a.set(x, -time);
				continue;
			}
			if (type == 2) {
				int y = in.nextInt() - 1;
				b.set(y, -time);
				continue;
			}
			int x1 = in.nextInt() - 1;
			int y1 = in.nextInt() - 1;
			int x2 = in.nextInt() - 1;
			int y2 = in.nextInt() - 1;
			int k = in.nextInt();
			int good = k - time;
			int ax1 = a.get(x1);
			int ax2 = a.get(x2);
			int by1 = b.get(y1);
			int by2 = b.get(y2);
			if (Math.min(ax1, by1) > good || Math.min(ax2, by2) > good) {
				out.println(-1);
				continue;
			}
			if (Math.max(ax1, by2) <= good || Math.max(ax2, by1) <= good) {
				out.println(Math.abs(x1 - x2) + Math.abs(y1 - y2));
				continue;
			}
			if (ax1 <= good) {
				solve(a, b, x1, x2, y1, y2, good);
			} else {
				solve(b, a, y1, y2, x1, x2, good);
			}
		}
	}
	
	void solve(SegmentsTree a, SegmentsTree b, int x1, int x2, int y1, int y2, int good) {
		if (a.getMax(Math.min(x1, x2), Math.max(x1, x2) + 1) <= good) {
			out.println(Math.abs(x1 - x2) + Math.abs(y1 - y2));
			return;
		}
		int yBest = b.closestAtMost(y1 + y2, good);
		if (yBest == -1) {
			out.println(-1);
			return;
		}
		out.println(Math.abs(x1 - x2) + Math.abs(y1 - yBest) + Math.abs(y2 - yBest));
	}
	
	static class SegmentsTree {
		int n;
		int[] min;
		int[] max;
		int size = 1 << 20;
		
		public SegmentsTree(int n) {
			this.n = n;
			min = new int[2 * size];
			max = new int[2 * size];
		}
		
		void set(int index, int value) {
			int i = size + index;
			min[i] = max[i] = value;
			while (i > 1) {
				i /= 2;
				min[i] = Math.min(min[2 * i], min[2 * i + 1]);
				max[i] = Math.max(max[2 * i], max[2 * i + 1]);
			}
		}
		
		int get(int index) {
			return min[size + index];
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
		
		int getMin(int from, int to) {
			from += size;
			to += size;
			int res = Integer.MAX_VALUE;
			while (from < to) {
				if (from % 2 == 1) {
					res = Math.min(res, min[from]);
					from++;
				}
				if (to % 2 == 1) {
					to--;
					res = Math.min(res, min[to]);
				}
				from /= 2;
				to /= 2;
			}
			return res;
		}
		
		public int closestAtMost(int xDoubled, int threshold) {
			if (getMin(0, n) > threshold) {
				return -1;
			}
			int low = -1;
			int high = n + 1;
			int bestFrom = -1;
			int bestTo = -1;
			while (low + 1 < high) {
				int mid = (low + high) / 2;
				int from = Math.max(xDoubled / 2 - mid, 0);
				int to = Math.min((xDoubled + 3) / 2 + mid, n);
				if (getMin(from, to) <= threshold) {
					high = mid;
					bestFrom = from;
					bestTo = to;
				} else {
					low = mid;
				}
			}
			if (min[size + bestFrom] <= threshold) {
				return bestFrom;
			}
			return bestTo - 1;
		}
	}
	
	static class SegmentsTreeDumb {
		int n;
		int[] a;
		
		public SegmentsTreeDumb(int n) {
			this.n = n;
			a = new int[n];
		}

		void set(int index, int value) {
			a[index] = value;
		}
		
		int get(int index) {
			return a[index];
		}
		
		int getMax(int from, int to) {
			int max = Integer.MIN_VALUE;
			for (int i = from; i < to; i++) {
				max = Math.max(a[i], max);
			}
			return max;
		}

		public int closestAtMost(int xDoubled, int threshold) {
			for (int i = 0; i < n; i++) {
				int j = xDoubled / 2 - i;
				if (j >= 0 && a[j] <= threshold) {
					return j;
				}
				j = (xDoubled + 1) / 2 + i;
				if (j < n && a[j] <= threshold) {
					return j;
				}
			}
			return -1;
		}
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = !true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			//out.print("Case #" + (test + 1) + ": ");
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

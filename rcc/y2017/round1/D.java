package rcc.y2017.round1;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		Point[] p = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			p[i] = new Point(x, y);
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			Point c = p[i];
			Point[] q = Arrays.copyOf(p, n - 1);
			if (i < n - 1) {
				q[i] = p[n - 1];
			}
			Arrays.sort(q, new Comparator<Point>() {
				@Override
				public int compare(Point a, Point b) {
					double ta = Math.atan2(a.y - c.y, a.x - c.x);
					double tb = Math.atan2(b.y - c.y, b.x - c.x);
					if (Math.abs(ta - tb) > 1e-8) {
						return Double.compare(ta, tb);
					}
					int ori = -orientation(c, a, b);
					return ori;
				}
			});
			long acutes = 0;
			for (int j = 0, k = 0; j < n - 1; j++) {
				if (k == j) {
					k++;
				}
				Point a = q[j];
				for (;;) {
					if (k == j + n - 1) {
						break;
					}
					int kk = k % (n - 1);
					Point b = q[kk];
					int or = orientation(c, a, b);
					if (or != 1) {
						if (or == 0) {
							if ((a.x >= c.x) == (b.x >= c.x)) {
								if ((a.y >= c.y) == (b.y >= c.y)) {
									if (k < n - 1) {
										k++;
										continue;
									}
								}
							}
						}
						break;
					}
					long cos = len2(c, a) + len2(c, b);
					if (cos < 0) {
						cos = Long.MAX_VALUE;
					}
					cos -= len2(a, b);
					if (cos <= 0) {
						break;
					}
					k++;
				}
				acutes += k - j - 1;
			}
			ans += (n - 1) * (n - 2) / 2 - acutes;
		}
		out.println(n * (n - 1L) * (n - 2) / 6 - ans);
	}
	
	class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
	
	static int orientation(Point a, Point b, Point c) {
		return Long.signum(
				1L * a.x * b.y - 1L * b.x * a.y +
				1L * b.x * c.y - 1L * c.x * b.y +
				1L * c.x * a.y - 1L * a.x * c.y
				);
	}

	static long len2(Point a, Point b) {
		long dx = a.x - b.x;
		long dy = a.y - b.y;
		return dx * dx + dy * dy;
	}
	
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new D().run();
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

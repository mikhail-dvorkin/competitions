package rcc.y2014.round1;
import java.io.*;
import java.util.*;

public class C_better {
	String yes = "YES";
	String no = "NO";
	final static int M = 100001;
	
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int t1 = in.nextInt();
		int t2 = in.nextInt();
		int k = in.nextInt();
		boolean[] as = new boolean[M];
		boolean[] bs = new boolean[M];
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			as[a] = true;
		}
		for (int i = 0; i < m; i++) {
			int b = in.nextInt();
			bs[b] = true;
		}
		BitSet[] aBitSets = new BitSet[M];
		BitSet[] bBitSets = new BitSet[M];
		Map<Long, Boolean> memo = new TreeMap<Long, Boolean>();
		for (int query = 0; query < k; query++) {
			int p = in.nextInt();
			int q = in.nextInt();
			long xx = q * 1L * t2 - p;
			long yy = p - q * 1L * t1;
			if (yy <= 0 || xx <= 0) {
				out.println(no);
				continue;
			}
			long g = gcd(xx, yy);
			xx /= g;
			yy /= g;
			if (xx >= M || yy >= M) {
				out.println(no);
				continue;
			}
			int x = (int) xx;
			int y = (int) yy;
			if (aBitSets[x] == null) {
				aBitSets[x] = bitSet(as, x);
			}
			if (bBitSets[y] == null) {
				bBitSets[y] = bitSet(bs, y);
			}
			long code = (x * 1L << 32) + y;
			if (!memo.containsKey(code)) {
				boolean res = aBitSets[x].intersects(bBitSets[y]);
				memo.put(code, res);
			}
			out.println(memo.get(code) ? yes : no);
		}
	}
	
	BitSet bitSet(boolean[] as, int x) {
		BitSet b = new BitSet();
		for (int i = 1; i * x < M; i++) {
			if (as[i * x]) {
				b.set(i);
			}
		}
		return b;
	}

	public static long gcd(long a, long b) {
		while (a > 0) {
			long t = b % a;
			b = a;
			a = t;
		}
		return b;
	}

	static boolean stdStreams = true;
	static String fileName = C_better.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new C_better().run();
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

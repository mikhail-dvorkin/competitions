package rcc.y2014.round1;
import java.io.*;
import java.util.*;

public class C {
	String yes = "YES";
	String no = "NO";
	final static int M = 100001;
	final static int s = 128;
	
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
		BitSet[] aBitSets = bitSets(as);
		BitSet[] bBitSets = bitSets(bs);
		Map<Integer, Boolean> memo = new TreeMap<Integer, Boolean>();
		loop:
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
			if (xx >= s || yy >= s) {
				for (int i = 1; i * x < M && i * y < M; i++) {
					if (as[i * x] && bs[i * y]) {
						out.println(yes);
						continue loop;
					}
				}
				out.println(no);
				continue;
			}
			int code = x * s + y;
			if (!memo.containsKey(code)) {
				boolean res = aBitSets[x].intersects(bBitSets[y]);
				memo.put(code, res);
			}
			out.println(memo.get(code) ? yes : no);
		}
	}
	
	private BitSet[] bitSets(boolean[] as) {
		BitSet[] r = new BitSet[s];
		for (int x = 1; x < s; x++) {
			BitSet b = new BitSet();
			for (int i = 1; i * x < M; i++) {
				if (as[i * x]) {
					b.set(i);
				}
			}
			r[x] = b;
		}
		return r;
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
	static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new C().run();
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

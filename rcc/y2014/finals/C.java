package rcc.y2014.finals;
import java.io.*;
import java.util.*;

public class C {
	static final int MAXN = 200;
	
	static int run(long m) {
		int k1 = 37;
		int k2 = 25;
		int mid = 45;
		int max = Math.max(k1, k2);
		int s = k1 + 2 * k2;
		int w = 500;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < k1; i++) {
			list.add(1);
		}
		for (int i = 0; i < k2; i++) {
			list.add(2);
		}
		long[][] cnk = new long[max + 1][max + 1];
		for (int i = 0; i <= max; i++) {
			cnk[i][0] = cnk[i][i] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
			}
		}
		long[] p = new long[s + 1];
		for (int i = 0; i <= s; i++) {
			for (int j = 0; 2 * j <= i; j++) {
				if (j <= k2 && (i - 2 * j) <= k1) {
					p[i] += cnk[k1][i - 2 * j] * cnk[k2][j];
				}
			}
		}
//		System.out.println(Arrays.toString(p));
//		for (int i = 0; i <= s; i++) {
//			System.out.println(i + "\t" + p[i]);
//		}
		for (int i = mid; i >= 0; i--) {
			while (m >= p[i]) {
				m -= p[i];
				list.add(w - i);
			}
		}
		System.out.println(list.size() + " " + w);
		for (int i : list) {
			System.out.print(i + " ");
		}
		System.out.println();
		if (list.size() > MAXN) {
			throw new RuntimeException();
		}
		return list.size();
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			run(in.nextLong());
		}
//		Random r = new Random(1);
//		for (;;) {
//			long m = r.nextLong();
//			if (m > 1000000000000000000L || m < 0) {
//				continue;
//			}
//			System.out.println(m);
//			run(m);
//		}
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

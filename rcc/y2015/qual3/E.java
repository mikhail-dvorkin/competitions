package rcc.y2015.qual3;
import java.io.*;
import java.util.*;

public class E {
	void run() {
		int n = in.nextInt();
		long[] x = new long[n];
		long[] y = new long[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
		}
		if (n <= 3) {
			System.out.println(n);
			return;
		}
		int[] detriangle = new int[n * n];
		for (int i = 1; i <= n; i++) {
			detriangle[i * (i - 1) / 2] = i;
		}
		Map<Long, Map<Long, Integer>> count = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				long a = y[j] - y[i];
				long b = x[i] - x[j];
				long c = x[j] * y[i] - x[i] * y[j];
				if (a < 0 || a == 0 && b < 0) {
					a *= -1;
					b *= -1;
					c *= -1;
				}
				long g = gcd(Math.abs(a), Math.abs(b));
				a /= g;
				b /= g;
				{
					// c / g
					long t = gcd(c, g);
					c /= t;
					g /= t;
					c += g * Integer.MAX_VALUE;
				}
				long d = a * Integer.MAX_VALUE + b;
				if (!count.containsKey(d)) {
					count.put(d, new TreeMap<>());
				}
				Map<Long, Integer> map = count.get(d);
				if (!map.containsKey(c)) {
					map.put(c, 1);
				} else {
					map.put(c, 1 + map.get(c));
				}
			}
		}
		int ans = 0;
		for (Map<Long, Integer> map : count.values()) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int v : map.values()) {
				list.add(detriangle[v]);
			}
			Collections.sort(list);
			int cur = list.get(list.size() - 1);
			if (list.size() >= 2) {
				cur += list.get(list.size() - 2);
			} else {
				if (cur < n) {
					cur++;
				}
			}
			ans = Math.max(ans, cur);
		}
		System.out.println(ans);
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
	static String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			new E().run();
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

package yandex.y2014.warmup;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class C {
	void run() {
		int n = in.nextInt();
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();
			long p = a + b + c;
			long q = (p - 2 * a) * (p - 2 * b) * (p - 2 * c);
			long g = gcd(p, q);
			p /= g;
			q /= g;
			long h = q * 4096 + p;
			if (!map.containsKey(h)) {
				map.put(h, 1);
			} else {
				map.put(h, map.get(h) + 1);
			}
		}
		int ans = 0;
		for (Entry<Long, Integer> entry : map.entrySet()) {
			ans = Math.max(ans, entry.getValue());
		}
		out.println(ans);
	}
	
	static long gcd(long a, long b) {
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

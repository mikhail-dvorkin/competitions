package gcj.y2018.practice;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		long n = in.nextLong();
		long k = in.nextLong();
		TreeMap<Long, Long> intervals = new TreeMap<>();
		intervals.put(n, 1L);
		for (;;) {
			long len = intervals.lastKey();
			long amount = intervals.get(len);
			intervals.remove(len);
			long len1 = (len - 1) / 2;
			long len2 = (len - 1) - len1;
			intervals.put(len1, amount + intervals.getOrDefault(len1, 0L));
			intervals.put(len2, amount + intervals.getOrDefault(len2, 0L));
			k -= amount;
			if (k <= 0) {
				out.println(len2 + " " + len1);
				return;
			}
		}
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
			out.print("Case #" + (test + 1) + ": ");
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

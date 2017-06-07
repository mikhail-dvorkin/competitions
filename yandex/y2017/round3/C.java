package yandex.y2017.round3;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		Hashing hashing = new Hashing(a);
		int low = 0;
		int high = n;
		while (low + 1 < high) {
			int len = (low + high) / 2;
			Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
			boolean found = false;
			for (int i = len; i < n; i++) {
				int hash = hashing.hash(i - len, i);
				Integer prev = map.put(hash, a[i]);
				if (prev != null && prev != a[i]) {
					found = true;
					break;
				}
			}
			if (found) {
				low = len;
			} else {
				high = len;
			}
		}
		if (high == 1 && (n == 1 || a[0] == a[1])) {
			high--;
		}
		out.println(high);
	}
	
	static class Hashing {
		final int X = 566239;
		final int M = 1000000007;
		
		final int[] h;
		final int[] t;

		public Hashing(int[] a) {
			int n = a.length;
			h = new int[n + 1];
			t = new int[n + 1];
			t[0] = 1;
			for (int i = 0; i < n; i++) {
				t[i + 1] = (int) ((t[i] * (long) X) % M);
				h[i + 1] = (int) ((h[i] * (long) X + a[i]) % M);
			}
		}
		
		public int hash(int from, int to) {
			int res = (int) ((h[to] - h[from] * (long) t[to - from]) % M);
			if (res < 0) {
				res += M;
			}
			return res;
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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

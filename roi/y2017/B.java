package roi.y2017;
import java.io.*;
import java.util.*;

public class B {
	int n;
	int[] a;
	int[][] must = new int[Integer.SIZE][2];
	int[] or = new int[2];
	int b, bad;
	
	void run() {
		n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		count(0, n - 1, 1);
		int queries = in.nextInt();
		for (int query = 0;; query++) {
			out.println((or[0] & or[1]) != 0 ? -1 : or[1]);
			if (query == queries) {
				break;
			}
			int x = in.nextInt() - 1;
			int value = in.nextInt();
			count(x - 1, x + 1, -1);
			a[x] = value;
			count(x - 1, x + 1, 1);
		}
	}

	void count(int from, int to, int weight) {
		for (int i = from; i < to; i++) {
			if (i < 0 || i >= n - 1) {
				continue;
			}
			int x = a[i];
			int y = a[i + 1];
			if (x == y) {
				continue;
			}
			int pos = 31 - Integer.numberOfLeadingZeros(x ^ y);
			int value = x < y ? 0 : 1;
			if (must[pos][value] == 0) {
				or[value] ^= 1 << pos;
			}
			must[pos][value] += weight;
			if (must[pos][value] == 0) {
				or[value] ^= 1 << pos;
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
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

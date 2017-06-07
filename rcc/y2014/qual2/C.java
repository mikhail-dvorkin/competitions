package rcc.y2014.qual2;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		int x = in.nextInt() - 1;
		int y = in.nextInt() - 1;
		List<Long> list = new ArrayList<>();
		toOrigin(list, x, y);
		toOrigin(list, n - 1 - x, y);
		toOrigin(list, x, m - 1 - y);
		toOrigin(list, n - 1 - x, m - 1 - y);
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == 0) {
				list.remove(i);
			}
		}
		Collections.sort(list);
		out.print(list.size());
		for (long v : list) {
			out.print(" " + v);
		}
		out.println();
	}

	static void toOrigin(List<Long> list, int x, int y) {
		list.add(region(x, y));
		list.add(region(y, x));
	}

	static long region(int x, int y) {
		if (y < 2) {
			return 0;
		}
		return y * (y - 1L) / 2 - region(y - x, y - x);
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
		int tests = in.nextInt();
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

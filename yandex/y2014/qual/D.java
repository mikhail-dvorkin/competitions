package yandex.y2014.qual;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		int inf = 1000000001;
		String bad = "Invisible paper detected";
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(0, inf);
		map.put(inf, 0);
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int xLowerEqual = map.floorKey(x);
			if (y >= map.get(xLowerEqual)) {
				out.println(bad);
				return;
			}
			for (;;) {
				int xHigher = map.higherKey(x);
				if (map.get(xHigher) >= y) {
					map.remove(xHigher);
					continue;
				}
				break;
			}
			map.put(x, y);
		}
		out.println("Well done");
	}

	static boolean stdStreams = true;
	static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new D().run();
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

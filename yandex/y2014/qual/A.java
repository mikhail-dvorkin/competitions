package yandex.y2014.qual;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int[][] a = new int[hei + 2][wid + 2];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i + 1][j + 1] = in.nextInt();
			}
		}
		int best = -1;
		int bestI = -1;
		int bestJ = -1;
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				int cur = 0;
				for (int di = -1; di <= 1; di++) {
					for (int dj = -1; dj <= 1; dj++) {
						int coef = (di == 0 && dj == 0) ? 8 : 1;
						cur += coef * a[i + di + 1][j + dj + 1];
					}
				}
				if (cur > best) {
					best = cur;
					bestI = i;
					bestJ = j;
				}
			}
		}
		out.println((bestI + 1) + " " + (bestJ + 1));
	}

	static boolean stdStreams = true;
	static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
		new A().run();
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

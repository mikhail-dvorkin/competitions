package rcc.y2014.qual2;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		long n = in.nextInt();
		long m = in.nextInt();
		long k = in.nextInt();
		loop:
		for (int mask = 0; mask < 512; mask++) {
			int count = 0;
			int p = 0;
			boolean[] iUsed = new boolean[3];
			boolean[] jUsed = new boolean[3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (((mask >> p) & 1) == 1) {
						count++;
						iUsed[i] = true;
						jUsed[j] = true;
						if (i >= n || j >= m) {
							continue loop;
						}
					}
					p++;
				}
			}
			if (count != 3) {
				continue loop;
			}
			int iCount = 0;
			for (boolean b : iUsed) {
				if (b) {
					iCount++;
				}
			}
			int jCount = 0;
			for (boolean b : jUsed) {
				if (b) {
					jCount++;
				}
			}
			long beat = m * iCount + n * jCount - iCount * jCount - 3;
			if (beat == k) {
				p = 0;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (((mask >> p) & 1) == 1) {
							out.print((i + 1) + " ");
							out.print((j + 1) + " ");
						}
						p++;
					}
				}
				out.println("");
				return;
			}
		}
		out.println("IMPOSSIBLE");
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

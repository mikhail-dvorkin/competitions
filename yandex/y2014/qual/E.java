package yandex.y2014.qual;
import java.io.*;
import java.util.*;

public class E {
	void run() {
		int n = in.nextInt();
		long ans = 0;
		long curV = 0;
		long minV = 0;
		long maxV = 0;
		for (int i = 0; i < n; i++) {
			if (i % 2 == 0) {
				curV += in.nextInt();
			} else {
				curV -= in.nextInt();
			}
			ans = Math.max(ans, Math.abs(curV - minV));
			ans = Math.max(ans, Math.abs(curV - maxV));
			minV = Math.min(minV, curV);
			maxV = Math.max(maxV, curV);
		}
		out.println(ans);
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
		new E().run();
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

package rcc.y2015.qual3;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int a = (int) (in.nextLong() % 9);
		int b = (int) ((in.nextLong() + 1) % 9);
		if (a >= b) {
			b += 9;
		}
		TreeSet<Integer> ans = new TreeSet<>();
		for (int i = a; i < b; i++) {
			ans.add((i + 8) % 9 + 1);
		}
		System.out.print(ans.size());
		for (int i : ans) {
			System.out.print(" " + i);
		}
		System.out.println();
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

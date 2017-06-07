package rcc.y2016.qual1;
import java.io.*;
import java.util.*;

public class A {
	static final String NO = "impossible";
	
	String run() {
		int n00 = in.nextInt();
		int n01 = in.nextInt();
		int n10 = in.nextInt();
		int n11 = in.nextInt();
		if (Math.abs(n01 - n10) > 1) {
			return NO;
		}
		String s = (n10 > n01 || n10 + n01 == 0 && n11 > 0) ? "1" : "0";
		while (n10 + n01 > 0) {
			if (s.charAt(s.length() - 1) == '0') {
				s += "1";
				n01--;
			} else {
				s += "0";
				n10--;
			}
		}
		if (n00 > 0 && !s.contains("0") || n11 > 0 && !s.contains("1")) {
			return NO;
		}
		while (n00-- > 0) {
			s = s.replaceFirst("0", "00");
		}
		while (n11-- > 0) {
			s = s.replaceFirst("1", "11");
		}
		return s;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			out.println(new A().run());
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

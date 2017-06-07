package yandex.y2014.round2;

import java.io.*;
import java.util.*;

public class E {
	void run() {
		int n = in.nextInt();
		int ax = in.nextInt();
		int ay = in.nextInt();
		int bx = in.nextInt();
		int by = in.nextInt();
		int cx = in.nextInt();
		int cy = in.nextInt();
		if (ay * bx > by * ax) {
			int t = ax; ax = bx; bx = t;
			t = ay; ay = by; by = t;
		}
		if (by * cx > cy * bx) {
			int t = cx; cx = bx; bx = t;
			t = cy; cy = by; by = t;
		}
		if (ay * bx > by * ax) {
			int t = ax; ax = bx; bx = t;
			t = ay; ay = by; by = t;
		}
		int p1 = cy * bx - cx * by;
		int p2 = cy * ax - cx * ay;
		int pgcd = gcd(p1, p2);
		p1 /= pgcd;
		p2 /= pgcd;
		int q1 = - (ay * bx - ax * by);
		int q2 = - (ay * cx - ax * cy);
		int qgcd = gcd(q1, q2);
		q1 /= qgcd;
		q2 /= qgcd;
		long k = lcm(p2, q2);
		long ans = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				long xx = x + k * p1 / p2;
				long yy = y + k * q1 / q2;
				if (xx < n && yy < n) {
					ans += Math.min(k, n);
				} else {
					ans += n;
				}
			}
		}
		out.println(ans);
	}
	
	public static int gcd(int a, int b) {
		while (a > 0) {
			int t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
	
	public static long gcd(long a, long b) {
		while (a > 0) {
			long t = b % a;
			b = a;
			a = t;
		}
		return b;
	}
	
	public static long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}

	static boolean stdStreams = false;
	static String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = "input.txt";
	static String outputFileName = "output.txt";
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

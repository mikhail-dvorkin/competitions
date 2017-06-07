package codeforces.round383;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] inDegree = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			inDegree[a[i]]++;
		}
		for (int i = 0; i < n; i++) {
			if (inDegree[i] != 1) {
				out.println(-1);
				return;
			}
		}
		long lcm = 1;
		for (int i = 0; i < n; i++) {
			int length = 1;
			int j = a[i];
			while (j != i) {
				j = a[j];
				length++;
			}
			lcm = lcm(lcm, length);
		}
		if (lcm % 2 == 0) {
			lcm /= 2;
		}
		out.println(lcm);
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new A().run();
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

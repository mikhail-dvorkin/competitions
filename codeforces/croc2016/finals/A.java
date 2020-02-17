package codeforces.croc2016.finals;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		long[] a = new long[n + 1];
		long b = 0;
		for (int i = 0; i < n; i++) {
			long p = in.nextLong();
			long q = in.nextLong();
			b ^= p;
			a[i] = p ^ q;
		}
		a[n] = b;
		long[] base = new long[Long.SIZE];
		long[] bit = new long[base.length];
		int baseSize = 0;
		for (int i = 0; i <= n; i++) {
			long v = a[i];
			for (int j = 0; j < baseSize; j++) {
				if ((v & bit[j]) != 0) {
					v ^= base[j];
				}
			}
			if (i == n) {
				if (v != 0) {
					System.out.println("1/1");
					return;
				}
				long den = 1L << baseSize;
				System.out.println((den - 1) + "/" + den);
				return;
			}
			if (v == 0) {
				continue;
			}
			base[baseSize] = v;
			bit[baseSize] = Long.lowestOneBit(v);
			baseSize++;
		}
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
		//noinspection ConstantConditions
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
		final BufferedReader br;
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

package hackerrank.hourrank8;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		long[] a = new long[n + 4];
		for (int i = 0; i < n; i++) {
			a[i + 2] = in.nextLong();
		}
		int z = n - 1 + n % 2;
		long ones = 0;
		long zeros = 0;
		for (int i = z + 1; i < a.length; i++) {
			if (i % 2 == 0) {
				ones += a[i];
			} else {
				zeros += a[i];
			}
		}
		a[z]--;
		a[z + 1] = 1;
		a[z + 2] = zeros + 1;
		a[z + 3] = ones - 1;
		main:
		for (;;) {
			if (a[0] == 0) {
				a = Arrays.copyOfRange(a, 2, a.length);
				continue;
			}
			if (a[a.length - 1] == 0) {
				a = Arrays.copyOf(a, a.length - 1);
				continue;
			}
			for (int i = 0; i < a.length; i++) {
				if (a[i] == 0) {
					a[i - 1] += a[i + 1];
					for (int j = i + 2; j < a.length; j++) {
						a[j - 2] = a[j];
					}
					a[a.length - 2] = 0;
					a[a.length - 1] = 0;
					continue main;
				}
			}
			break;
		}
		System.out.println(a.length);
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]);
			if (i < a.length - 1) {
				System.out.print(" ");
			} else {
				System.out.println();
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

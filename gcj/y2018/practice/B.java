package gcj.y2018.practice;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		main:
		for (;;) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					a[i]--;
					a[j]--;
					if (a[i] >= 0 && a[j] >= 0 && good(a)) {
						out.print(" " + (char) ('A' + i) + (char) ('A' + j));
						continue main;
					}
					a[i]++;
					a[j]++;
				}
			}
			for (int i = 0; i < n; i++) {
				a[i]--;
				if (a[i] >= 0 && good(a)) {
					out.print(" " + (char) ('A' + i));
					continue main;
				}
				a[i]++;
			}
			out.println();
			break;
		}
	}
	
	boolean good(int[] a) {
		int sum = 0;
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			max = Math.max(max, a[i]);
			sum += a[i];
		}
		return 2 * max <= sum;
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
			out.print("Case #" + (test + 1) + ":");
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

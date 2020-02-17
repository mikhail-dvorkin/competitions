package codeforces.round528;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int[] x = new int[3];
		int[] y = new int[3];
		for (int i = 0; i < 3; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
		}
		int[] xSorted = x.clone();
		int[] ySorted = y.clone();
		Arrays.sort(xSorted);
		Arrays.sort(ySorted);
		out.println(ySorted[2] - ySorted[0] + xSorted[2] - xSorted[0] + 1);
		for (int i = xSorted[0]; i <= xSorted[2]; i++) {
			out.println(i + " " + ySorted[1]);
		}
		for (int i = 0; i < 3; i++) {
			int yLow = Math.min(y[i], ySorted[1]);
			int yHigh = Math.max(y[i], ySorted[1]);
			for (int j = yLow; j <= yHigh; j++) {
				if (j == ySorted[1]) {
					continue;
				}
				out.println(x[i] + " " + j);
			}
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
			//out.print("Case #" + (test + 1) + ": ");
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

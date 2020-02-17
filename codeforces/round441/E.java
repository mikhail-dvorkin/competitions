package codeforces.round441;
import java.io.*;
import java.util.*;

public class E {
	void run() {
		int n = in.nextInt();
		int s0 = in.nextInt();
		int s1 = in.nextInt();
		int[] x = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
		}
		int low = Math.abs(s0 - s1) - 1;
		int high = (int) 1e9;
		while (low + 1 < high) {
			int dist = (low + high) / 2;
			TreeSet<Integer> points = new TreeSet<>();
			points.add(s0);
			points.add(s1);
			boolean ok = true;
			for (int a : x) {
				while (!points.isEmpty()) {
					int b = points.first();
					if (Math.abs(b - a) <= dist) {
						break;
					}
					points.remove(b);
				}
				while (!points.isEmpty()) {
					int b = points.last();
					if (Math.abs(b - a) <= dist) {
						break;
					}
					points.remove(b);
				}
				if (points.isEmpty()) {
					ok = false;
					break;
				}
				points.add(a);
			}
			if (ok) {
				high = dist;
			} else {
				low = dist;
			}
		}
		out.println(high);
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new E().run();
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

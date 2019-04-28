package gcj.y2019.round1b;
import java.io.*;
import java.util.*;

public class A {
	public static final String DIR_ROSE = "ENWS";

	void run() {
		int people = in.nextInt();
		int m = in.nextInt();
		int[][] a = new int[2][m + 2];
		for (int i = 0; i < people; i++) {
			int[] xy = new int[] {in.nextInt(), in.nextInt()};
			int dir = DIR_ROSE.indexOf(in.next());
			int[] array = a[dir % 2];
			int coord = xy[dir % 2];
			if (dir >= 2) {
				array[0] += 1;
				array[coord] -= 1;
			} else {
				array[coord + 1] += 1;
				array[m + 1] -= 1;
			}
		}
		for (int[] array : a) {
			int best = -1;
			int bestX = -1;
			int cur = 0;
			for (int x = 0; x <= m; x++) {
				cur += array[x];
				if (cur > best) {
					best = cur;
					bestX = x;
				}
			}
			out.print(" " + bestX);
		}
		out.println();
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
			out.print("Case #" + (test + 1) + ":");
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

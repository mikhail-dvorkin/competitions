package codeforces.rockethon2015;
import java.io.*;
import java.util.*;

public class B_wa {
	void run() {
		int n = in.nextInt();
		long m = in.nextLong() - 1;
		int[] a = new int[n];
		int x, y, z;
		if (n % 2 == 1) {
			a[n / 2] = n;
			x = n / 2 - 1;
			y = n / 2 + 1;
			z = n - 1;
		} else {
			x = n / 2 - 1;
			y = n / 2;
			z = n;
		}
		while (z > 0) {
			a[y] = z;
			y++;
			z--;
			a[x] = z;
			x--;
			z--;
		}
		for (int i = 0; i < n / 2; i++) {
			int j = n / 2 - 1 - i;
			int k = n - 1 - j;
			if (((m >> i) & 1) != 0) {
				int t = a[j];
				a[j] = a[k];
				a[k] = t;
			}
		}
		System.out.println(Arrays.toString(a).replaceAll("[^\\s\\d]", ""));
	}

	static final boolean stdStreams = true;
	static final String fileName = B_wa.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static final String inputFileName = fileName + ".in";
	static final String outputFileName = fileName + ".out";
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new B_wa().run();
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

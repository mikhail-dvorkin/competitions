package codeforces.round383;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		int[] p = new int[2 * n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			b[i] = in.nextInt() - 1;
			p[a[i]] = b[i];
			p[b[i]] = a[i];
		}
		int[] c = new int[2 * n];
		Arrays.fill(c, 2);
		for (int s = 0; s < 2 * n; s++) {
			if (c[s] != 2) {
				continue;
			}
			int i = s;
			for (;;) {
				c[i] = 0;
				i = p[i];
				c[i] = 1;
				i ^= 1;
				if (c[i] == 0) {
					break;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			out.println((c[a[i]] + 1) + " " + (c[b[i]] + 1));
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new C().run();
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

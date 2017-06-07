package rcc.y2017.qual2;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		Arrays.fill(b, -1);
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			if (a[i] >= 0) {
				b[a[i]] = i;
			}
		}
		int prev = -1;
		int first = -1;
		for (int i = 0; i < n; i++) {
			if (a[i] >= 0) {
				continue;
			}
			int j = i;
			while (b[j] >= 0) {
				j = b[j];
			}
			if (prev == -1) {
				first = j;
			} else {
				a[prev] = j;
			}
			prev = i;
		}
		if (prev != -1) {
			a[prev] = first;
		}
		boolean[] seen = new boolean[n];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (seen[i]) {
				continue;
			}
			ans++;
			int j = i;
			while (true) {
				seen[j] = true;
				j = a[j];
				if (j == i) {
					break;
				}
			}
		}
		out.println(n - ans);
		for (int i = 0; i < n; i++) {
			out.print(a[i] + 1 + " ");
		}
		out.println();
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
		int tests = in.nextInt();
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

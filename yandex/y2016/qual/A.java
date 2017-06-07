package yandex.y2016.qual;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		String[] s = new String[n];
		for (int i = 0; i < n; i++) {
			s[i] = in.next();
		}
		int m = s[0].length();
		for (int i = 0; i < n; i++) {
			boolean good = true;
			for (int j = 0; j < n; j++) {
				int diff = 0;
				for (int k = 0; k < m; k++) {
					if (s[i].charAt(k) != s[j].charAt(k)) {
						diff++;
					}
				}
				if (diff > 1) {
					good = false;
				}
			}
			if (good) {
				out.println(s[i]);
				return;
			}
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = false;
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";
		
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

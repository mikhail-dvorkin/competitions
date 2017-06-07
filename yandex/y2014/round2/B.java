package yandex.y2014.round2;

import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		Arrays.sort(a);
		int ans = 1;
		int min = a[0];
		if (n > 1 && a[1] == min) {
			ans = 0;
		}
		int max = a[n - 1];
		boolean[] p = new boolean[max + 1];
		for (int i = n - 1; i >= 1; i--) {
			int v = a[i];
			for (int j = v; j <= max; j++) {
				if (p[j]) {
					p[j % v] = true;
				}
			}
			p[v] = true;
		}
		for (int j = 0; j <= max; j++) {
			if (p[j]) {
				p[j % min] = true;
			}
		}
		for (int i = 0; i < min; i++) {
			if (p[i]) {
				ans++;
			}
		}
		out.println(ans);
	}

	static boolean stdStreams = false;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = "input.txt";
	static String outputFileName = "output.txt";
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
		new B().run();
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

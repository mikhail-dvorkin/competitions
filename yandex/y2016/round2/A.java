package yandex.y2016.round2;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		in.nextInt();
		int m = in.nextInt();
		String s = in.next();
		int[] letterCost = new int['z' - 'a' + 1];
		Arrays.fill(letterCost, Integer.MAX_VALUE);
		for (int i = 0; i < m; i++) {
			in.next();
			int cost = in.nextInt();
			String t = in.next();
			for (int j = 0; j < t.length(); j++) {
				int k = t.charAt(j) - 'a';
				letterCost[k] = Math.min(letterCost[k], cost);
			}
		}
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			int k = s.charAt(i) - 'a';
			if (letterCost[k] == Integer.MAX_VALUE) {
				out.println(-1);
				return;
			}
			ans += letterCost[k];
		}
		out.println(ans);
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

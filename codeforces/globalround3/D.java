package codeforces.globalround3;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		ArrayList<Long> inc = new ArrayList<>();
		ArrayList<Long> dec = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			if (a < b) {
				inc.add(n * (long) (2 * n - b) + i);
			} else {
				dec.add(n * (long) (a) + i);
			}
		}
		ArrayList<Long> ans = (inc.size() > dec.size()) ? inc : dec;
		Collections.sort(ans);
		out.println(ans.size());
		for (long x : ans) {
			out.print(x % n + 1);
			out.print(" ");
		}
		out.println();
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			//out.print("Case #" + (test + 1) + ": ");
			new D().run();
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

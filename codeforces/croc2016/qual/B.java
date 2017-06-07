package codeforces.croc2016.qual;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int max = in.nextInt();
		int[] length = new int[n];
		int[] time = new int[n];
		int[] queue = new int[n];
		long[] ans = new long[n];
		Arrays.fill(ans, -1);
		for (int i = 0; i < n; i++) {
			time[i] = in.nextInt();
			length[i] = in.nextInt();
		}
		int low = 0;
		int high = 0;
		long t = 0;
		long tEnd = -1;
		int next = 0;
		for (;;) {
			while (next < n && time[next] < tEnd) {
				if (high - low < max) {
					queue[high] = next;
					high++;
				}
				next++;
			}
			if (low < high) {
				t = tEnd;
				tEnd = t + length[queue[low]];
				ans[queue[low]] = tEnd;
				low++;
			} else if (next < n) {
				t = time[next];
				tEnd = t + length[next];
				ans[next] = tEnd;
				next++;
			} else {
				break;
			}
		}
		for (int i = 0; i < n; i++) {
			System.out.print(ans[i] + " ");
		}
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			new B().run();
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

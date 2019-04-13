package roi.y2019;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int m = in.nextInt();
		int n = in.nextInt();
		int[] a = new int[m];
		int[] left = new int[n];
		int[] right = new int[n];
		Arrays.fill(left, m);
		Arrays.fill(right, -1);
		for (int i = 0; i < m; i++) {
			a[i] = in.nextInt() - 1;
			left[a[i]] = Math.min(left[a[i]], i);
			right[a[i]] = i;
		}
		int[] stack = new int[m];
		int stackSize = 0;
		ArrayList<String> answer = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			if (i == left[a[i]]) {
				if (stackSize > 0) {
					if (right[a[i]] > right[stack[stackSize - 1]]) {
						out.println(-1);
						return;
					}
				}
				stack[stackSize] = a[i];
				stackSize++;
				answer.add((a[i] + 1) + " " + (i + 1) + " " + (right[a[i]] + 1));
			}
			if (a[i] != stack[stackSize - 1]) {
				out.println(-1);
				return;
			}
			if (i == right[a[i]]) {
				stackSize--;
			}
		}
		out.println(answer.size());
		for (String s : answer) {
			out.println(s);
		}
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			//out.print("Case #" + (test + 1) + ": ");
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

package gcj.y2018.round2;
import java.io.*;
import java.util.*;

public class A {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int sum = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			sum += a[i];
		}
		if (sum != n || a[0] == 0 || a[n - 1] == 0) {
			out.println("IMPOSSIBLE");
			return;
		}
		int[] fin = new int[n];
		int[] index = new int[n];
		for (int i = 0, k = 0; i < n; i++) {
			for (int j = 0; j < a[i]; j++) {
				fin[k++] = i;
			}
			index[i] = i;
		}
		ArrayList<String> ans = new ArrayList<>();
		for (;;) {
			boolean done = true;
			char[] c = new char[n];
			Arrays.fill(c, '.');
			for (int i = 0; i < n; i++) {
				if (index[i] == fin[i]) {
					continue;
				}
				done = false;
				if (index[i] > fin[i]) {
					c[index[i]] = '/';
					index[i]--;
				} else {
					c[index[i]] = '\\';
					index[i]++;
				}
			}
			ans.add(new String(c));
			if (done) {
				break;
			}
		}
		out.println(ans.size());
		for (String s : ans) {
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
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

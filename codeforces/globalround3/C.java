package codeforces.globalround3;
import java.io.*;
import java.util.*;

public class C {
	int[] a, pos;
	final ArrayList<String> ans = new ArrayList<>();

	void run() {
		int n = in.nextInt();
		int n2 = n / 2;
		a = new int[n];
		pos = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt() - 1;
			pos[a[i]] = i;
		}
		for (int i = 1; i < n; i++) {
			int near = (i < n2) ? 0 : n - 1;
			int far = n - 1 - near;
			if ((pos[i] < n2) ^ (i < n2)) {
				swap(pos[i], near);
			}
			swap(pos[i], far);
			swap(pos[i], i);
		}
		out.println(ans.size());
		for (String s : ans) {
			out.println(s);
		}
	}

	void swap(int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
		pos[a[i]] = i;
		pos[a[j]] = j;
		ans.add((i + 1) + " " + (j + 1));
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
		//noinspection ConstantConditions
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
			new C().run();
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

package atcoder.keyence2019;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
		long need = 0;
		int ans = 0;
		ArrayList<Integer> extras = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			b[i] = in.nextInt();
			if (a[i] < b[i]) {
				need += b[i] - a[i];
				ans++;
			} else if (a[i] > b[i]) {
				extras.add(a[i] - b[i]);
			}
		}
		Collections.sort(extras);
		while (need > 0) {
			if (extras.isEmpty()) {
				out.println(-1);
				return;
			}
			ans++;
			need -= extras.get(extras.size() - 1);
			extras.remove(extras.size() - 1);
		}
		out.println(ans);
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
			//out.print("Case #" + (test + 1) + ": ");
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

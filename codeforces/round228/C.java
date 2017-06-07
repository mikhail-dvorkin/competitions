package codeforces.round228;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int m = in.nextInt();
		int[][] a = new int[m][];
		for (int i = 0; i < m; i++) {
			a[i] = new int[in.nextInt()];
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = in.nextInt();
			}
		}
		int[] ans = new int[2];
		List<Integer> middles = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (2 * j == a[i].length - 1) {
					middles.add(a[i][j]);
				} else if (2 * j < a[i].length - 1) {
					ans[0] += a[i][j];
				} else {
					ans[1] += a[i][j];
				}
			}
		}
		Collections.sort(middles);
		Collections.reverse(middles);
		int i = 0;
		for (int v : middles) {
			ans[i] += v;
			i ^= 1;
		}
		out.println(ans[0] + " " + ans[1]);
	}

	static boolean stdStreams = true;
	static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
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
		new C().run();
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

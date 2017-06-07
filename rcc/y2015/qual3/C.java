package rcc.y2015.qual3;
import java.io.*;
import java.util.*;

public class C {
	void run() {
		int up1 = in.nextInt();
		int down1 = in.nextInt();
		int up2 = in.nextInt();
		int down2 = in.nextInt();
		int time = in.nextInt();
		int h1 = 0;
		int h2 = 0;
		double ans = 0;
		for (int t = 0; t < time; t++) {
			boolean up = (t % 24) < 12;
			int v1, v2;
			if (up) {
				v1 = up1;
				v2 = up2;
			} else {
				v1 = -down1;
				v2 = -down2;
			}
			double tChange;
			if (v1 != v2) {
				// h1 + v1 * t = h2 + v2 * t
				tChange = (h2 - h1) * 1.0 / (v1 - v2);
				if (tChange < 0) {
					tChange = 1;
				} else if (h1 == h2) {
					tChange = (v1 > v2) ? 0 : 1;
				}
				tChange = Math.min(1, tChange);
			} else {
				tChange = 1;
			}
			if (h1 > h2) {
				ans += tChange;
			} else {
				ans += 1 - tChange;
			}
			h1 += v1;
			h2 += v2;
		}
		System.out.println(ans);
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
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
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

package rcc.y2014.qual1;
import java.io.*;
import java.util.*;

public class B {
	void solve() {
		int value = in.nextInt();
		int div = in.nextInt();
		int cheats = in.nextInt();
		int time = in.nextInt();
		long ans = Math.max(solveClick(value, div, cheats, time), solveDontClick(value, div, cheats, time));
		out.println(ans);
	}
	
	long solveClick(long value, int div, int cheats, int time) {
		if (cheats == 0) {
			return 0;
		}
		cheats--;
		value = (value + div - 1) / div * div;
		return solveDiv(value, div, cheats, time);
	}

	long solveDontClick(long value, int div, int cheats, int time) {
		long valueDiv = (value + div - 1) / div * div;
		if (time < valueDiv - value) {
			return value + time;
		}
		return solveDiv(valueDiv, div, cheats, time - (int) (valueDiv - value));
	}
	
	long solveDiv(long value, int div, int cheats, int time) {
		int c = Math.min(cheats, time);
		value += c * (long) div;
		cheats -= c;
		time -= c;
		return value + time;
	}

	void run() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			solve();
		}
	}

	static boolean stdStreams = true;
	static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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

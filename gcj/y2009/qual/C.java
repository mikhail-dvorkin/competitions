package gcj.y2009.qual;

import java.io.*;
import java.util.*;

public class C implements Runnable {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	private final String t = "welcome to code jam";
	private final int m = t.length();
	private final int d = 10000;

	private void solve() {
		String s = in.nextLine();
		System.out.println(s);
		int n = s.length();
		int[][] a = new int[n + 1][m + 1];
		a[0][0] = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i + 1][j] += a[i][j];
				a[i + 1][j] %= d;
				if (s.charAt(i) == t.charAt(j)) {
					a[i + 1][j + 1] += a[i][j];
					a[i + 1][j + 1] %= d;
				}
			}
			a[i + 1][m] += a[i][m];
			a[i + 1][m] %= d;
		}
		s = "" + a[n][m];
		while (s.length() < (d - 1 + "").length())
			s = "0" + s;
		out.println(s);
	}

	@Override
	public void run() {
		int tests = in.nextInt();
		in.nextLine();
		for (int test = 1; test <= tests; test++) {
			out.print("Case #" + test + ": ");
			solve();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		Thread thread = new Thread(new C());
		thread.start();
		thread.join();
		in.close();
		out.close();
	}
}

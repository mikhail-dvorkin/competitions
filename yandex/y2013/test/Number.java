package yandex.y2013.test;
import java.io.*;
import java.util.*;

public class Number {
	private static String fileName = Number.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	public void run() {
		String s = in.next();
		int n = s.length();
		int m = 4;
		int ans = Integer.MAX_VALUE;
		for (int mask = 0; mask < (1 << n); mask++) {
			if (Integer.bitCount(mask) != m) {
				continue;
			}
			String t = "";
			for (int i = 0; i < n; i++) {
				if (((mask >> i) & 1) == 0) {
					continue;
				}
				t += s.charAt(i);
			}
			int v = Integer.parseInt(t);
			ans = Math.min(ans, v);
		}
		out.println(ans);
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		new Number().run();
		in.close();
		out.close();
	}
}

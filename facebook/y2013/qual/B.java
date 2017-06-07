package facebook.y2013.qual;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		String s = in.nextLine();
		int n = s.length();
		boolean[][] a = new boolean[n + 1][n + 1];
		for (int len = 0; len <= n; len++) {
			for (int i = 0; i + len <= n; i++) {
				int j = i + len;
				if (len == 0) {
					a[i][j] = true;
					continue;
				}
				if (len == 1 && ((s.charAt(i) == ' ') || (s.charAt(i) == ':') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z'))) {
					a[i][j] = true;
					continue;
				}
				if (len == 2 && s.charAt(i) == ':' && ((s.charAt(i + 1) == ')') || (s.charAt(i + 1) == '('))) {
					a[i][j] = true;
					continue;
				}
				if (s.charAt(i) == '(' && s.charAt(j - 1) == ')' && a[i + 1][j - 1]) {
					a[i][j] = true;
					continue;
				}
				for (int k = i + 1; k < j; k++) {
					if (a[i][k] && a[k][j]) {
						a[i][j] = true;
					}
				}
			}
		}
		out.println(a[0][n] ? "YES" : "NO");
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);	
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new B().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}

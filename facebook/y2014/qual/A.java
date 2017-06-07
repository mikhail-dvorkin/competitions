package facebook.y2014.qual;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		boolean[][] f = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			String s = in.next();
			for (int j = 0; j < n; j++) {
				f[i][j] = s.charAt(j) == '#';
			}
		}
		int minI = n, minJ = n, maxI = -1, maxJ = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (f[i][j]) {
					maxI = i;
					maxJ = j;
					if (minI == n) {
						minI = i;
						minJ = j;
					}
				}
			}
		}
		boolean square = (maxI - minI == maxJ - minJ);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if ((i >= minI && i <= maxI && j >= minJ && j <= maxJ) != f[i][j]) {
					square = false;
				}
			}
		}
		out.println(square ? "YES" : "NO");
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
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}
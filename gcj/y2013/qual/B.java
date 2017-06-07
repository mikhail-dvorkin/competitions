package gcj.y2013.qual;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		int[][] a = new int[hei][wid];
		int[] rowMax = new int[hei];
		int[] colMax = new int[wid];
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				a[i][j] = in.nextInt();
				rowMax[i] = Math.max(rowMax[i], a[i][j]);
				colMax[j] = Math.max(colMax[j], a[i][j]);
			}
		}
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (a[i][j] != Math.min(rowMax[i], colMax[j])) {
					out.println("NO");
					return;
				}
			}
		}
		out.println("YES");
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
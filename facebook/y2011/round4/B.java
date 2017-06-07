package facebook.y2011.round4;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int h = in.nextInt();
		int w = in.nextInt();
		int t = in.nextInt();
		int m = in.nextInt();
		int[][] f = new int[h][2 * w - 1];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < 2 * w - 1; j++) {
				if (i % 2 == 1 && (j == 0 || j == 2 * w - 2)) {
					continue;
				}
				if (i % 2 == j % 2) {
					f[i][j] = 1;
				} else {
					f[i][j] = 2;
				}
			}
		}
		for (int i = 0; i < m; i++) {
			int r = in.nextInt();
			int c = in.nextInt();
			f[r][2 * c + (r % 2)] = 2;
		}
		double[][] a = new double[h][2 * w - 1];
		for (int i = h - 1; i >= 0; i--) {
			for (int j = 0; j < 2 * w - 1; j++) {
				if (f[i][j] != 2) {
					continue;
				}
				if (i == h - 1) {
					if (j == 2 * t + 1) {
						a[i][j] = 1;
					}
					continue;
				}
				a[i][j] = a[i + 1][j];
			}
			for (int j = 0; j < 2 * w - 1; j++) {
				if (f[i][j] != 1) {
					continue;
				}
				double p1 = 0;
				double p2 = 0;
				double v1 = 0;
				double v2 = 0;
				if (j > 0 && f[i][j - 1] == 2) {
					p1 = 1;
					v1 = a[i][j - 1];
				}
				if (j < 2 * w - 2 && f[i][j + 1] == 2) {
					p2 = 1;
					v2 = a[i][j + 1];
				}
				a[i][j] = (p1 * v1 + p2 * v2) / (p1 + p2);
			}
		}
		int s = -1;
		for (int j = 1; j < 2 * w - 1; j++) {
			if (s < 0 || a[0][j] > a[0][s]) {
				s = j;
			}
		}
		out.println((s / 2) + " " + String.format("%.6f", a[0][s]));
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
			new B().solve();
		}
		in.close();
		out.close();
	}
}

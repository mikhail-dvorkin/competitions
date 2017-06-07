package facebook.y2014.round1;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		boolean[][] f = new boolean[hei][wid];
		boolean[][] fTransp = new boolean[wid][hei];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				f[i][j] = fTransp[j][i] = s.charAt(j) == '#';
			}
		}
		out.println(Math.max(solve(f), solve(fTransp)));
	}

	private int solve(boolean[][] f) {
		int hei = f.length;
		int wid = f[0].length;
		boolean[][] fromStart = new boolean[hei][wid];
		int[][] a = new int[hei][wid];
		fromStart[0][0] = true;
		for (int i = 0; i < hei; i++) {
			for (int j = 0; j < wid; j++) {
				if (f[i][j]) {
					continue;
				}
				if (i > 0 && fromStart[i - 1][j] || j > 0 && fromStart[i][j - 1]) {
					fromStart[i][j] = true;
				}
			}
		}
		int ans = 1;
		for (int i = hei - 1; i >= 0; i--) {
			int bestj = -1;
			for (int j = wid - 1; j >= 0; j--) {
				if (f[i][j]) {
					bestj = -1;
					continue;
				}
				int cur = 1;
				int down = 0;
				if (i + 1 < hei) {
					cur = Math.max(cur, 1 + a[i + 1][j]);
					down = a[i + 1][j];
				}
				if (j + 1 < wid) {
					cur = Math.max(cur, 1 + a[i][j + 1]);
				}
				a[i][j] = cur;
				if (bestj == -1 && i > 0 && fromStart[i - 1][j]) {
					bestj = j;
				}
				if (bestj >= 0) { // <- after the contest :(
					ans = Math.max(ans, 1 + i + bestj + bestj - j + down);
				}
			}
		}
		ans = Math.max(ans, a[0][0]);
		return ans;
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
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}

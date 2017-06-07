package facebook.y2011.round1;
import java.io.*;
import java.util.*;

public class C {
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "");
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	int[] dx = {1, 0, -1, 0, 0};
	int[] dy = {0, 1, 0, -1, 0};

	private void solve() {
		int hei = in.nextInt();
		int wid = in.nextInt();
		boolean[][] f = new boolean[hei][wid];
		for (int i = 0; i < hei; i++) {
			String s = in.next();
			for (int j = 0; j < wid; j++) {
				f[i][j] = (s.charAt(j) == 'X');
			}
		}
		int max = 1 << hei;
		int ans = Integer.MAX_VALUE;
		boolean[][] a = new boolean[hei][wid];
		for (int mask = 0; mask < max; mask++) {
			if (mask % 10000 == 0) {
				System.out.println(mask);
			}
			for (int i = 0; i < hei; i++) {
				for (int j = 0; j < wid; j++) {
					a[i][j] = f[i][j];
				}
			}
			int cur = 0;
			for (int j = 0; j < wid; j++) {
				for (int i = 0; i < hei; i++) {
					if (((j > 0) && !a[i][j - 1]) || ((j == 0) && ((mask & (1 << i)) != 0))) {
						cur++;
						for (int k = 0; k < dx.length; k++) {
							int ii = i + dx[k];
							int jj = j + dy[k];
							if (ii >= 0 && jj >= 0 && ii < hei && jj < wid) {
								a[ii][jj] ^= true;
							}
						}
					}
				}
			}
			boolean win = true;
			for (int j = 1; j < wid; j++) {
				for (int i = 0; i < hei; i++) {
					if (!a[i][j]) {
						win = false;
					}
				}
			}
			if (win) {
				ans = Math.min(ans, cur);
			}
		}
		out.println((ans == Integer.MAX_VALUE) ? -1 : ans);
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
			new C().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}

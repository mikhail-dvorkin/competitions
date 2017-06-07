package facebook.y2014.round2;
import java.io.*;
import java.util.*;

public class B {
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;

	private void solve() {
		int n = in.nextInt();
		long all = (n - 2) * 1L * (n - 3) * (n - 4) * (n - 5) * (n - 6) * (n - 7) / 48;
		boolean[][] bet = new boolean[n][n];
		for (int aa = 0; aa < n; aa++) {
			for (int a = 0; a < aa; a++) {
				int s = a + aa;
				long good = 0;
				for (int bb = 0; bb < n; bb++) {
					if (bb == aa || bb == a) {
						continue;
					}
					int bMax = s - bb + 1;
					if (bb > aa) {
						bMax--;
					}
					bMax = Math.min(bMax, bb);
					if (bMax <= 0) {
						continue;
					}
					for (int cc = bb + 1; cc < n; cc++) {
						if (cc == aa || cc == a) {
							continue;
						}
						int cMax = s - cc + 1;
						if (cc > aa) {
							cMax--;
						}
						cMax = Math.min(cMax, cc);
						if (cMax <= 0) {
							continue;
						}
						for (int dd = cc + 1; dd < n; dd++) {
							if (dd == aa || dd == a) {
								continue;
							}
							int dMax = s - dd + 1;
							if (dd > aa) {
								dMax--;
							}
							dMax = Math.min(dMax, dd);
							if (dMax <= 0) {
								continue;
							}
							int t1 = Math.min(bMax, Math.min(cMax, dMax));
							int t3 = Math.max(bMax, Math.max(cMax, dMax));
							int t2 = bMax + cMax + dMax - t1 - t3;
							int v1 = t1;
							if (a < t1) v1--;
							if (aa < t1) v1--;
							if (bb < t1) v1--;
							if (cc < t1) v1--;
							if (dd < t1) v1--;
							if (v1 <= 0) continue;
							int v2 = t2 - 1;
							if (a < t2) v2--;
							if (aa < t2) v2--;
							if (bb < t2) v2--;
							if (cc < t2) v2--;
							if (dd < t2) v2--;
							if (v2 <= 0) continue;
							int v3 = t3 - 2;
							if (a < t3) v3--;
							if (aa < t3) v3--;
							if (bb < t3) v3--;
							if (cc < t3) v3--;
							if (dd < t3) v3--;
							if (v3 <= 0) continue;
							good += v1 * v2 * v3;
						}
					}
				}
				bet[a][aa] = bet[aa][a] = (good * 4 > all);
			}
		}
		int hands = in.nextInt();
		for (int h = 0; h < hands; h++) {
			int c1 = in.nextInt() - 1;
			int c2 = in.nextInt() - 1;
			out.print(bet[c1][c2] ? "B" : "F");
		}
		out.println();
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

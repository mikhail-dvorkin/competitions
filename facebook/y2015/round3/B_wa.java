package facebook.y2015.round3;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B_wa {
	int m, n, lunch;
	int[] from, to;
	String imp = "Lunchtime";
	int MAX = 80000000;
	
	String solve() {
		int[][] a = new int[m + 1][n + 1];
		int[] best1 = new int[m + n + 1];
		int[] best2 = new int[m + n + 1];
		for (int i = 0; i <= m + n; i++) {
			int r = (i == m + n) ? 0 : to[i];
			best1[i] = -1;
			best2[i] = -1;
			for (int j = 0; j < m + n; j++) {
				if (from[j] >= r + lunch) {
					continue;
				}
				if (j < m) {
					if ((best1[i] == -1 || to[j] > to[best1[i]]) && to[j] > r) {
						best1[i] = j;
					}
				} else {
					if ((best2[i] == -1 || to[j] > to[best2[i]]) && to[j] > r) {
						best2[i] = j;
					}
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			Arrays.fill(a[i], -1);
		}
		a[0][0] = n + m;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (a[i][j] == -1) {
					continue;
				}
				int k = a[i][j];
				int r = (k == m + n) ? 0 : to[k];
				if (r + lunch > MAX) {
					ans = Math.min(ans, Math.max(i, j));
				}
				if (best1[k] >= 0/* && i < m*/) {
					a[i + 1][j] = Math.max(a[i + 1][j], best1[k]);
				}
				if (best2[k] >= 0/* && j < n*/) {
					a[i][j + 1] = Math.max(a[i][j + 1], best2[k]);
				}
			}
		}
		if (ans == Integer.MAX_VALUE) {
			return imp;
		}
		return "" + ans;
	}

	public B_wa(Scanner in) {
		m = in.nextInt();
		n = in.nextInt();
		lunch = in.nextInt();
		from = new int[m + n];
		to = new int[m + n];
		for (int i = 0; i < m + n; i++) {
			from[i] = in.nextInt();
			to[i] = in.nextInt();
		}
	}
	
	private static String fileName = B_wa.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Future<String>[] outputs = new Future[tests];
		for (int t = 0; t < tests; t++) {
			final B_wa testCase = new B_wa(in);
			outputs[t] = executor.submit(new Callable<String>() {
				@Override
				public String call() {
					return testCase.solve();
				}
			});
		}
		for (int t = 0; t < tests; t++) {
			out.println("Case #" + (t + 1) + ": " + outputs[t].get());
		}
		in.close();
		out.close();
		executor.shutdown();
	}
}

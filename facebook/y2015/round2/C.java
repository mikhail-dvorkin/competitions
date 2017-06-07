package facebook.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	int n, k;
	String[] s;
	
	String solve() {
		if (k == 1) {
			return "1";
		}
		Arrays.sort(s);
		int inf = Integer.MAX_VALUE / 3;
		int[][][] a = new int[n][k + 1][];
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= k; j++) {
				a[i][j] = new int[s[i].length()];
				Arrays.fill(a[i][j], inf);
			}
		}
		for (int i = 0; i < n; i++) {
			a[i][1][0] = 0;
		}
		int[][] common = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				common[i][j] = common[j][i] = common(s[i], s[j]);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int t = 1; t < k; t++) {
				for (int j = 0; j < s[i].length(); j++) {
					int aCur = a[i][t][j];
					if (aCur == inf) {
						continue;
					}
					for (int m = i + 1; m < n; m++) {
						int jj = common[i][m];
						int c = Math.max(j, jj);
						if (c < s[i].length()) {
							c++;
						}
						a[m][t + 1][jj] = Math.min(a[m][t + 1][jj], aCur + c);
					}
				}
			}
		}
		int ans = inf;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < s[i].length(); j++) {
				int aCur = a[i][k][j];
				if (aCur == inf) {
					continue;
				}
				int c = j;
				if (c < s[i].length()) {
					c++;
				}
				ans = Math.min(ans, aCur + c);
			}
		}
		return "" + ans;
	}
	
	int common(String a, String b) {
		if (a == null || b == null) {
			return 0;
		}
		int res = 0;
		while (res < a.length() && res < b.length() && a.charAt(res) == b.charAt(res)) {
			res++;
		}
		return res;
	}
	

	
	public C(Scanner in) {
		n = in.nextInt();
		k = in.nextInt();
		s = new String[n];
		for (int i = 0; i < n; i++) {
			s[i] = in.next();
		}
	}
	
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final C testCase = new C(in);
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

package facebook.y2015.round3;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int x0, y0, n, d;
	int[] x;
	int[] y;
	
	String solve() {
		double eps = 1e-9;
		for (int i = 0; i < n; i++) {
			x[i] -= x0;
			y[i] -= y0;
		}
		int ans = 0;
		double[] angles = new double[n];
		for (int f = 0; f < n; f++) {
			double rf = Math.hypot(x[f], y[f]);
			double x1 = d * 1.0 * x[f] / rf;
			double y1 = d * 1.0 * y[f] / rf;
			int here = 0;
			int to = 0;
			double a0 = Math.atan2(0 - y1, 0 - x1);
			for (int i = 0; i < n; i++) {
				if (Math.hypot(y[i] - y1, x[i] - x1) < eps) {
					here++;
					angles[i] = -9;
				} else {
					angles[i] = Math.atan2(y[i] - y1, x[i] - x1);
					if (Math.abs(angles[i] - a0) < eps) {
						if (Math.hypot(x[i], y[i]) < eps) {
							to++;
						} else if (Math.abs(Math.atan2(y1, x1) - Math.atan2(y[i], x[i])) < eps) {
							to++;
						}
					}
				}
			}
			Arrays.sort(angles);
			int cur = 0;
			int best = 0;
			for (int i = 0; i < n; i++) {
				if (angles[i] == -9) {
					continue;
				}
				if (i == 0 || angles[i] > angles[i - 1] + eps) {
					cur = 1;
				} else {
					cur++;
				}
				best = Math.max(best, cur);
			}
			ans = Math.max(ans, (to + here) * (best + here));
		}
		return "" + ans;
	}
	
	public A(Scanner in) {
		x0 = in.nextInt();
		y0 = in.nextInt();
		d = in.nextInt();
		n = in.nextInt();
		x = new int[n];
		y = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
		}
	}
	
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final A testCase = new A(in);
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

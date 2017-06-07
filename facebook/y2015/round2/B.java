package facebook.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	double p;
	int n = 20;
	
	String solve() {
		double[] a = new double[n + 1];
		long[][] cnk = new long[n + 1][n + 1];
		for (int i = 0; i <= n; i++) {
			cnk[i][i] = cnk[i][0] = 1;
			for (int j = 1; j < i; j++) {
				cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j];
			}
		}
		double[] ps = new double[n + 1];
		double[] qs = new double[n + 1];
		ps[0] = qs[0] = 1;
		for (int i = 1; i <= n; i++) {
			ps[i] = ps[i - 1] * p;
			qs[i] = qs[i - 1] * (1 - p);
			// a[i] = 1 + (1 - p)^i * a[i] + c_i^1 * (1 - p)^i-1 * p * a[i - 1] +
			double sum = 0;
			for (int j = 1; j <= i; j++) {
				sum += cnk[i][j] * ps[j] * qs[i - j] * a[i - j];
			}
			a[i] = (1 + sum) / (1 - qs[i]);
		}
		double ans = a[n];
		return "" + String.format("%.5f", ans);
	}
	
	public B(Scanner in) {
		p = in.nextDouble();
	}
	
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final B testCase = new B(in);
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

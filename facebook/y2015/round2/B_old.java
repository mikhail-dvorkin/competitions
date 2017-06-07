package facebook.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B_old {
	double p;
	int n = 20;
	
	String solve() {
		double[] a = new double[n + 1];
		for (int i = 1; i <= n; i++) {
			// a[i] = 1 + (1 - p) * a[i] + p * (1 - p) * a[i - 1] + p^2 * (1 - p) * a[i-2] + ...
			// a[1] = 1 + (1 - p) * a[1] + p * a[0];
			double sum = 0;
			double prod = 1 - p;
			for (int j = i - 1; j >= 0; j--) {
				prod *= p;
				sum += prod * a[j];
			}
			a[i] = (1 + sum) / p;
		}
		System.out.println(Arrays.toString(a));
		double ans = a[n];
		return "" + String.format("%.5f", ans);
	}
	
	public B_old(Scanner in) {
		p = in.nextDouble();
	}
	
	private static String fileName = B_old.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final B_old testCase = new B_old(in);
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

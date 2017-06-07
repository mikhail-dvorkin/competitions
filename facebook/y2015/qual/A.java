package facebook.y2015.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int n;
	
	String solve() {
		char[] c = ("" + n).toCharArray();
		int min = n, max = n;
		for (int i = 0; i < c.length; i++) {
			for (int j = i + 1; j < c.length; j++) {
				if (i == 0 && c[j] == '0') {
					continue;
				}
				char t = c[i]; c[i] = c[j]; c[j] = t;
				int cur = Integer.parseInt(new String(c));
				t = c[i]; c[i] = c[j]; c[j] = t;
				min = Math.min(min, cur);
				max = Math.max(max, cur);
			}
		}
		return min + " " + max;
	}
	
	public A(Scanner in) {
		n = in.nextInt();
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

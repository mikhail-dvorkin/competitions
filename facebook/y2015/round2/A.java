package facebook.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int n;
	int[] a;
	
	String solve() {
		boolean ans = solve(1, n - 1, a[0], a[0]) || solve(0, n - 2	, a[n - 1], a[n - 1]);
		return ans ? "yes" : "no";
	}
	
	boolean solve(int left, int right, int min, int max) {
		while (left <= right) {
			if (a[left] == min - 1) {
				left++;
				min--;
				continue;
			}
			if (a[left] == max + 1) {
				left++;
				max++;
				continue;
			}
			if (a[right] == min - 1) {
				right--;
				min--;
				continue;
			}
			if (a[right] == max + 1) {
				right--;
				max++;
				continue;
			}
			return false;
		}
		return true;
	}
	
	public A(Scanner in) {
		n = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
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

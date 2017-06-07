package facebook.y2016.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int m = 4;
	int n;
	int[] v;
	
	String solve() {
		int[] a = new int[n + 1];
		int inf = 3 * n;
		Arrays.fill(a, inf);
		a[0] = 0;
		for (int i = 0; i < n; i++) {
			int inter = 0;
			for (int j = i; j < n; j++) {
				if (j > i) {
					if (v[j - 1] >= v[j]) {
						break;
					}
					inter += (v[j] - v[j - 1] - 1) / 10;
				}
				int total = j + 1 - i + inter;
				if (total > m) {
					break;
				}
				a[j + 1] = Math.min(a[j + 1], a[i] + inter + m - total);
			}
		}
		return "" + a[n];
	}
	
	public A(Scanner in) {
		n = in.nextInt();
		v = new int[n];
		for (int i = 0; i < n; i++) {
			v[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt(); in.nextLine();//1;
		nThreads = Math.min(nThreads, tests);
		@SuppressWarnings("unchecked")
		Callable<String>[] callables = new Callable[tests];
		for (int t = 0; t < tests; t++) {
			final A testCase = new A(in);
			final int testCaseNumber = t + 1;
			callables[t] = new Callable<String>() {
				@Override
				public String call() {
					String answer = testCase.solve();
					System.out.println(String.format(formatSystemOut, answer, testCaseNumber));
					return String.format(formatOut, answer, testCaseNumber);
				}
			};
		}
		try {
			if (nThreads > 1) {
				ExecutorService executor = Executors.newFixedThreadPool(4);
				@SuppressWarnings("unchecked")
				Future<String>[] outputs = new Future[tests];
				for (int t = 0; t < tests; t++) {
					outputs[t] = executor.submit(callables[t]);
				}
				for (int t = 0; t < tests; t++) {
					out.println(outputs[t].get());
				}
				executor.shutdown();
			} else {
				for (int t = 0; t < tests; t++) {
					out.println(callables[t].call());
				}
			}
		} catch (Exception e) {
			System.out.flush();
			System.err.flush();
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("COMPLETE");
		in.close();
		out.close();
	}
}

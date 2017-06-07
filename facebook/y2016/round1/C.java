package facebook.y2016.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	int n, from, to;
	int[] c;
	
	String solve() {
		long sum = 0;
		double ans = 0;
		for (int i = 0; i < n; i++) {
			sum += c[i];
		}
		{
			int full = (int) (from / sum);
			from -= full * sum;
			to -= full * sum;
		}
		if (to <= sum) {
			ans = solve(from, to);
		} else {
			ans = solve(from, sum) * (sum - from);
			int m = (int) (to / sum);
			ans += solve(0, to - sum * m) * (to - sum * m);
			ans += (m - 1) * sum * solve(0, sum);
			ans /= to - from;
		}
		return "" + ans;
	}
	
	private double solve(long a, long b) {
		if (a == b) {
			return 0;
		}
		double ans = 0;
		long sum = 0;
		for (int i = 0; i < n; i++) {
			long min = Math.max(sum, a);
			long max = Math.min(sum + c[i], b);
			if (min < max) {
				ans += ((max + min) * 0.5 - sum) * (max - min);
			}
			sum += c[i];
		}
		ans /= b - a;
		return ans;
	}

	public C(Scanner in) {
		n = in.nextInt();
		from = in.nextInt();
		to = in.nextInt();
		c = new int[n];
		for (int i = 0; i < n; i++) {
			c[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final C testCase = new C(in);
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

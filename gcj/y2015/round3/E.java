package gcj.y2015.round3;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class E {
	int n, d;
	int[] a;
	String wrong = "CHEATERS!";
	
	String solve() {
		for (int i = 2 * d; i < n; i++) {
			if (a[i] != a[i - 2 * d]) {
				return wrong;
			}
		}
		a = Arrays.copyOf(a, 4 * d);
		for (int i = 0; i < 2 * d; i++) {
			a[i + 2 * d] = a[i];
		}
		int ans = 0;
		for (int t = d; t >= 1;) {
			assert 4 * t == a.length;
			int[] c = new int[a.length + 1];
			for (int i = 0; i < a.length; i++) {
				c[i + 1] = c[i] + a[i];
			}
			int maxS = Integer.MIN_VALUE;
			int minS = Integer.MAX_VALUE;
			int pos = -1;
			for (int i = 0; i < 2 * t; i++) {
				int a0 = c[i + t] - c[i];
				int a1 = c[i + t + 1] - c[i + 1];
				int a2 = c[i + t + 2] - c[i + 2];
				if (a2 + a0 < 2 * a1) {
					pos = i + 1;
				}
				maxS = Math.max(maxS, a0);
				minS = Math.min(minS, a0);
			}
			if (maxS == minS) {
				t /= 2;
				a = Arrays.copyOf(a, a.length / 2);
				continue;
			}
			assert pos >= 0;
			for (int i = 0; i < t; i++) {
				for (int j = (pos + i) % (2 * t); j < a.length; j += 2 * t) {
					a[j]--;
					if (a[j] < 0) {
						return wrong;
					}
				}
			}
			ans++;
		}
		return "" + ans;
	}
	
	public E(Scanner in) {
		n = in.nextInt();
		d = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = E.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Callable<String>[] callables = new Callable[tests];
		for (int t = 0; t < tests; t++) {
			final E testCase = new E(in);
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

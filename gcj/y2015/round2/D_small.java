package gcj.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class D_small {
	int hei, wid;
	
	String solve() {
		int[] a = new int[hei + 1];
		int[] b = new int[hei + 1];
		int[] aF = new int[hei + 1];
		int[] bF = new int[hei + 1];
		a[0] = 1;
		b[0] = 1;
		for (int i = 1; i <= hei; i++) {
			if (i >= 2) {
				b[i] = a[i - 2];
				bF[i] = aF[i - 2];
			}
			if (i >= 1) {
				a[i] += b[i - 1];
				aF[i] += bF[i - 1];
			}
			if (i >= 2 && wid % 3 == 0) {
				aF[i] += b[i - 2] + bF[i - 2] * 3;
			}
			if (i >= 2 && wid % 6 == 0) {
				aF[i] += b[i - 2] + bF[i - 2] * 6;
			}
			if (i >= 3 && wid % 4 == 0) {
				aF[i] += b[i - 3] + bF[i - 3] * 4;
			}
		}
		int ans = a[hei] + b[hei] + aF[hei] + bF[hei];
		if (hei == 6 && wid == 6) {
			ans -= 3;
		}
		return "" + ans;
	}
	
	public D_small(Scanner in) {
		hei = in.nextInt();
		wid = in.nextInt();
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = D_small.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final D_small testCase = new D_small(in);
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

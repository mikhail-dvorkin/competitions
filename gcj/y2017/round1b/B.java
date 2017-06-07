package gcj.y2017.round1b;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	static final String COLORS = "ROYGBV";
	static final String NO = "IMPOSSIBLE";
	
	String solve() {
		for (int i = 1; i <= 5; i += 2) {
			if (a[i] > 0 && a[(i + 3) % 6] == a[i]) {
				int c = 0;
				for (int j = 0; j < 6; j++) {
					if (a[j] > 0) {
						c++;
					}
				}
				if (c == 2) {
					String s = "";
					for (int j = 0; j < a[i]; j++) {
						s += COLORS.charAt(i);
						s += COLORS.charAt((i + 3) % 6);
					}
					return s;
				}
			}
			if (a[i] > 0 && a[(i + 3) % 6] <= a[i]) {
				return NO;
			}
			a[(i + 3) % 6] -= a[i];
		}
		for (int i = 0; i <= 4; i += 2) {
			if (a[i] > a[(i + 2) % 6] + a[(i + 4) % 6]) {
				return NO;
			}
		}
		String s = "";
		int p = -1;
		int f = -1;
		for (;;) {
			int q = -1;
			for (int i = 0; i <= 4; i += 2) {
				if (i == p) {
					continue;
				}
				if (q == -1 || a[i] > a[q] || a[i] == a[q] && i == f) {
					q = i;
				}
			}
			if (a[q] == 0) {
				break;
			}
			a[q]--;
			s += COLORS.charAt(q);
			if (p == -1) {
				f = q;
			}
			p = q;
		}
		for (int i = 1; i <= 5; i += 2) {
			char ci = COLORS.charAt(i);
			char co = COLORS.charAt((i + 3) % 6);
			String from = "" + co;
			String to = "" + co + ci + co;
			for (int j = 0; j < a[i]; j++) {
				s = s.replaceFirst(from, to);
			}
		}
		return s;
	}
	
	int[] a;
	
	public B(Scanner in) {
		a = new int[6];
		in.nextInt();
		for (int i = 0; i < 6; i++) {
			a[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		/* "Google Code Jam, Facebook Hacker Cup" */
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
//		/* "IPSC" */
//		String fileNameSuffix = "0";
//		String formatOut = "%s";
//		String formatSystemOut = "Case #%2$d solved.";//"Case #%2$d: %1$s";
		
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final B testCase = new B(in);
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

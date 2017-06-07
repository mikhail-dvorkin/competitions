package gcj.y2016.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	int len, amount;
	
	String solve() {
		StringBuilder ans = new StringBuilder();
		for (int mask = 0; mask < amount; mask++) {
			long m = (1 << (len / 2 - 1)) | (mask << 1) | 1;
			long n = (m << (len / 2)) | m;
			ans.append(Long.toBinaryString(n));
			for (int b = 2; b <= 10; b++) {
				ans.append(" ").append(Long.parseLong(Long.toBinaryString(m), b));
			}
			ans.append("\n");
		}
		return "\n" + ans.toString().trim();
	}
	
	static boolean isGood(int n) {
		String binary = Integer.toBinaryString(n);
		for (int b = 2; b <= 10; b++) {
			int m = Integer.parseInt(binary, b);
			boolean prime = true;
			for (int i = 2; i * i <= m; i++) {
				if (m % i == 0) {
					prime = false;
					break;
				}
			}
			if (prime) {
				return false;
			}
		}
		return true;
	}
	
	public C(Scanner in) {
		len = in.nextInt();
		amount = in.nextInt();
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

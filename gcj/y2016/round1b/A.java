package gcj.y2016.round1b;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	static final String[] names = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
	static final int T = names.length;
	static char[] letter;
	static int[] digit;
	
	String s;
	
	String solve() {
		int[] ans = new int[T];
		int[] count = new int['Z' + 1];
		for (int i = 0; i < s.length(); i++) {
			count[s.charAt(i)]++;
		}
		for (int i = 0; i < T; i++) {
			int k = count[letter[i]];
			ans[digit[i]] = k;
			String name = names[digit[i]];
			for (int j = 0; j < name.length(); j++) {
				count[name.charAt(j)] -= k;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < T; i++) {
			for (int j = 0; j < ans[i]; j++) {
				sb.append(i);
			}
		}
		return sb.toString();
	}
	
	public A(Scanner in) {
		s = in.next();
	}
	
	static void precalc() {
		letter = new char[T];
		digit = new int[T];
		boolean[] processed = new boolean[T];
		for (int k = 0; k < T; k++) {
			for (char c = 'A'; c <= 'Z'; c++) {
				int count = 0;
				int which = -1;
				for (int i = 0; i < T; i++) {
					if (processed[i]) {
						continue;
					}
					if (names[i].contains("" + c)) {
						count++;
						which = i;
					}
				}
				if (count == 1 && names[which].indexOf(c) == names[which].lastIndexOf(c)) {
					System.out.println(c + " " + names[which]);
					digit[k] = which;
					letter[k] = c;
					processed[which] = true;
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		precalc();
		int nThreads = 4;
		/* "Google Code Jam, Facebook Hacker Cup" */
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
//		/* "IPSC" */
//		String fileNameSuffix = "0";
//		String formatOut = "%s";
//		String formatSystemOut = "Case #%2$d solved.";//"Case #%2$d: %1$s";
		
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

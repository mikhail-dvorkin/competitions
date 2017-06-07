package facebook.y2017.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	static final int INF = Integer.MAX_VALUE / 2;
	int n, m;
	int[][] c;
	
	String solve() {
		int[] a = new int[n + 1];
		int[] b = new int[n + 1];
		Arrays.fill(a, INF);
		a[0] = 0;
		for (int day = 0; day < n; day++) {
			Arrays.sort(c[day]);
			System.arraycopy(a, 0, b, 0, n + 1);
			int pay = 0;
			for (int i = 1; i <= m; i++) {
				pay += c[day][i - 1] + 2 * i - 1;
				for (int j = 0; i + j <= n; j++) {
					b[i + j] = Math.min(b[i + j], a[j] + pay);
				}
			}
			System.arraycopy(b, 1, a, 0, n);
		}
		return "" + a[0];
	}
	
	public A(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		c = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				c[i][j] = in.nextInt();
			}
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

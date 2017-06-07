package facebook.y2016.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int n, m = 2;
	boolean[][] wall;
	
	String solve() {
		int[][] a = new int[3][3];
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3; k++) {
				a[j][k] = 2 * n;
			}
		}
		a[0][0] = 0;
		for (int i = 0; i < n; i++) {
			int[][] b = new int[3][3];
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					b[j][k] = 2 * n;
				}
			}
			boolean wx = wall[0][i];
			boolean wy = wall[1][i];
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					for (int x = 0; x < 2; x++) {
						for (int y = 0; y < 2; y++) {
							if (x == 1 && wx) {
								continue;
							}
							if (y == 1 && wy) {
								continue;
							}
							int jj = j;
							int kk = k;
							if (j == 2 && wx) {
								continue;
							}
							if (k == 2 && wy) {
								continue;
							}
							if (!wx) {
								if (x == 1) {
									jj = 1;
								} else {
									if (j == 0 && x == 0 && y == 0) {
										jj = 2;
									}
								}
							} else {
								jj = 0;
							}
							if (!wy) {
								if (y == 1) {
									kk = 1;
								} else {
									if (k == 0 && x == 0 && y == 0) {
										kk = 2;
									}
								}
							} else {
								kk = 0;
							}
							b[jj][kk] = Math.min(b[jj][kk], a[j][k] + x + y);
						}
					}
				}
			}
			a = b;
		}
		int ans = 2 * n;
		for (int j = 0; j < 2; j++) {
			for (int k = 0; k < 2; k++) {
				ans = Math.min(ans, a[j][k]);
			}
		}
		return "" + ans;
	}
	
	public B(Scanner in) {
		n = in.nextInt();
		wall = new boolean[m][n];
		for (int j = 0; j < m; j++) {
			String s = in.next();
			for (int i = 0; i < n; i++) {
				wall[j][i] = s.charAt(i) == 'X';
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
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

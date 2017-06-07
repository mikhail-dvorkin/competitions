package facebook.y2017.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	static final int INF = Integer.MAX_VALUE / 2;
	static final String NO = "-1";
	int n, moves;
	int[][] e;
	int[] from;
	int[] to;
	
	String solve() {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					e[i][j] = Math.min(e[i][j], e[i][k] + e[k][j]);
				}
			}
		}
		for (int i = 0, pos = 0; i < moves; i++) {
			if (e[pos][from[i]] == INF || e[from[i]][to[i]] == INF) {
				return NO;
			}
			pos = to[i];
		}
		int without = 0;
		int with = INF;
		int pos = 0;
		for (int i = 0; i < moves; i++) {
			int withNext = INF;
			int withoutNext = INF;
			int fi = from[i];
			int ti = to[i];
			withoutNext = Math.min(withoutNext, without + e[pos][fi] + e[fi][ti]);
			withoutNext = Math.min(withoutNext, with + e[pos][ti]);
			if (i + 1 < moves) {
				int fj = from[i + 1];
				withNext = Math.min(withNext, without + e[pos][fi] + e[fi][fj] + e[fj][ti]);
				withNext = Math.min(withNext, with + e[pos][fj] + e[fj][ti]);
			}
			without = withoutNext;
			with = withNext;
			pos = ti;
		}
		return "" + without;
	}
	
	public C(Scanner in) {
		n = in.nextInt();
		int m = in.nextInt();
		moves = in.nextInt();
		e = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(e[i], INF);
			e[i][i] = 0;
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int g = in.nextInt();
			e[a][b] = e[b][a] = Math.min(e[a][b], g);
		}
		from = new int[moves];
		to = new int[moves];
		for (int i = 0; i < moves; i++) {
			from[i] = in.nextInt() - 1;
			to[i] = in.nextInt() - 1;
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

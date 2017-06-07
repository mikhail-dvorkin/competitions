package facebook.y2015.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	final static int M = 3;
	int[] need;
	int n;
	int[][] food;
	
	String solve() {
		for (int mask = 0; mask < (1 << n); mask++) {
			int[] get = need.clone();
			for (int i = 0; i < n; i++) {
				if ((mask & (1 << i)) == 0) {
					continue;
				}
				for (int j = 0; j < M; j++) {
					get[j] -= food[i][j];
				}
			}
			Arrays.sort(get);
			if (get[0] == 0 && get[M - 1] == 0) {
				return "yes";
			}
		}
		return "no";
	}
	
	public B(Scanner in) {
		need = new int[M];
		for (int i = 0; i < M; i++) {
			need[i] = in.nextInt();
		}
		n = in.nextInt();
		food = new int[n][M];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < M; j++) {
				food[i][j] = in.nextInt();
			}
		}
	}
	
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Future<String>[] outputs = new Future[tests];
		for (int t = 0; t < tests; t++) {
			final B testCase = new B(in);
			outputs[t] = executor.submit(new Callable<String>() {
				@Override
				public String call() {
					return testCase.solve();
				}
			});
		}
		for (int t = 0; t < tests; t++) {
			out.println("Case #" + (t + 1) + ": " + outputs[t].get());
		}
		in.close();
		out.close();
		executor.shutdown();
	}
}

package facebook.y2015.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	static final int M = 1000000007;
	String input;
	
	String solve() {
		String[] s = input.split("-");
		int a = Integer.parseInt(s[0]);
		int b = Integer.parseInt(s[1]);
		int[][] hard = new int[a + b + 1][a + b + 1];
		int[][] easy = new int[a + b + 1][a + b + 1];
		hard[0][0] = easy[0][0] = 1;
		for (int i = 0; i < a + b; i++) {
			for (int x = 0; x <= i; x++) {
				int y = i - x;
				for (int k = 0; k < 2; k++) {
					int xx = x;
					int yy = y;
					if (k == 0) {
						xx++;
					} else {
						yy++;
					}
					if (xx > yy) {
						easy[i + 1][xx] += easy[i][x];
						easy[i + 1][xx] %= M;
					}
					if (xx <= yy || yy == b) {
						hard[i + 1][xx] += hard[i][x];
						hard[i + 1][xx] %= M;
					}
				}
			}
		}
		return easy[a + b][a] + " " + hard[a + b][a];
	}
	
	public C(Scanner in) {
		input = in.next();
	}
	
	private static String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final C testCase = new C(in);
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

package gcj.y2014.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	static final int S = 4;
	
	private String solve(Input input) {
		Set<Integer> a = set(input.table[0][input.row[0] - 1]);
		Set<Integer> b = set(input.table[1][input.row[1] - 1]);
		a.retainAll(b);
		if (a.size() == 0) {
			return "Volunteer cheated!";
		}
		if (a.size() > 1) {
			return "Bad magician!";
		}
		return "" + a.iterator().next();
	}
	
	Set<Integer> set(int[] array) {
		Set<Integer> set = new HashSet<Integer>();
		for (int x : array) {
			set.add(x);
		}
		return set;
	}

	static class Input {
		int[] row = new int[2];
		int[][][] table = new int[2][S][S];
		
		public Input(Scanner in) {
			for (int k = 0; k < 2; k++) {
				row[k] = in.nextInt();
				for (int i = 0; i < S; i++) {
					for (int j = 0; j < S; j++) {
						table[k][i][j] = in.nextInt();
					}
				}
			}
		}
	}
	
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Future<String>[] outputs = new Future[tests];
		for (int t = 0; t < tests; t++) {
			final Input input = new Input(in);
			outputs[t] = executor.submit(new Callable<String>() {
				@Override
				public String call() {
					return new A().solve(input);
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

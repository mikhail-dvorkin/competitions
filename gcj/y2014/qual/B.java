package gcj.y2014.qual;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	static class Input {
		String solve() {
			double speed = 2;
			double t = 0;
			double answer = Double.MAX_VALUE;
			for (;;) {
				answer = Math.min(answer, t + x / speed);
				t += c / speed;
				speed += f;
				if (t > answer) {
					break;
				}
			}
			return "" + answer;
		}
		
		double c, f, x;
		
		Input(Scanner in) {
			c = in.nextDouble();
			f = in.nextDouble();
			x = in.nextDouble();
		}
	}
	
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
					return input.solve();
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

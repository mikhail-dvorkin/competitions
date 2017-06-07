package gcj.y2015.round1a;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int b, n;
	int[] a;
	int which;
	
	boolean inTime(long time, boolean last) {
		List<Integer> free = new ArrayList<>();
		long prevFree = 0;
		for (int i = 0; i < b; i++) {
			prevFree += (time + a[i] - 1) / a[i];
			if (time % a[i] == 0) {
				free.add(i);
			}
		}
		if (last) {
			which = free.get((int) (n - prevFree));
		}
		return n < prevFree + free.size();
	}
	
	String solve() {
		n--;
		long low = -1;
		long high = a[0] * 1L * n;
		while (low + 1 < high) {
			long mid = (low + high) / 2;
			if (inTime(mid, false)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		inTime(high, true);
		return "" + (which + 1);
	}
	
	public B(Scanner in) {
		b = in.nextInt();
		n = in.nextInt();
		a = new int[b];
		for (int i = 0; i < b; i++) {
			a[i] = in.nextInt();
		}
	}
	
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static int nThreads = 4;
	
	public static void main(String[] args) throws Exception {
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(new File(inputFileName));
		PrintWriter out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		@SuppressWarnings("unchecked")
		Callable<String>[] callables = new Callable[tests];
		for (int t = 0; t < tests; t++) {
			final B testCase = new B(in);
			final int testCaseNumber = t;
			callables[t] = new Callable<String>() {
				@Override
				public String call() {
					String answer = testCase.solve();
					String printed = String.format("Case #%2$d: %1$s", answer, testCaseNumber + 1);
					System.out.println(printed);
					return printed;
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

package facebook.y2016.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int loads, washers, dryers;
	int[] w;
	int d;
	
	String solve() {
		Arrays.sort(w);
		long low = 0;
		long high = (w[0] + (long) d) * loads;
		while (low + 1 < high) {
			long time = (low + high) / 2;
			TreeMap<Long, Integer> dryerTimes = new TreeMap<>();
			{
				int m = loads;
				long t = time - d;
				while (m > 0) {
					int dry = Math.min(m, dryers);
					dryerTimes.put(t, dry);
					m -= dry;
					t -= d;
				}
			}
			for (int i = 0; i < washers; i++) {
				for (int j = 1;; j++) {
					long t = w[i] * 1L * j;
					Long tDry = dryerTimes.ceilingKey(t);
					if (tDry == null) {
						break;
					}
					int count = dryerTimes.get(tDry) - 1;
					if (count == 0) {
						dryerTimes.remove(tDry);
					} else {
						dryerTimes.put(tDry, count);
					}
				}
			}
			if (dryerTimes.isEmpty()) {
				high = time;
			} else {
				low = time;
			}
		}
		return "" + high;
	}
	
	public B(Scanner in) {
		loads = in.nextInt();
		washers = in.nextInt();
		dryers = in.nextInt();
		d = in.nextInt();
		w = new int[washers];
		for (int i = 0; i < washers; i++) {
			w[i] = in.nextInt();
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

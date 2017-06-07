package gcj.y2015.round2;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int n;
	long tempNeed, vNeed;
	Source[] sources;
	long tick = 10000000;
	
	String solve() {
		vNeed *= tick;
		Arrays.sort(sources);
		if (tempNeed < sources[0].temp || tempNeed > sources[n - 1].temp) {
			return "IMPOSSIBLE";
		}
		Source[] sourcesReversed = new Source[n];
		long sumR = 0;
		for (int i = 0; i < n; i++) {
			sumR += sources[i].rate;
			sourcesReversed[n - 1 - i] = sources[i];
		}
		BigInteger vtNeed = BigInteger.valueOf(tempNeed).multiply(BigInteger.valueOf(vNeed));
		long low = (vNeed + sumR - 1) / sumR;
		long high = Long.MAX_VALUE / 2;
		while (low + 1 < high) {
			long time = (low + high) / 2;
			byte[] res = new byte[2];
			for (int q = 0; q < 2; q++) {
				Source[] array = (q == 0) ? sources : sourcesReversed;
				long v = vNeed;
				BigInteger vt = BigInteger.ZERO;
				for (int i = 0; i < n; i++) {
					long vTake = v;
					if (array[i].rate * 1.0 * time <= vTake) {
						vTake = Math.min(array[i].rate * time, vTake);
					}
					v -= vTake;
//					vt += vTake * 1.0 * array[i].temp;
					vt = vt.add(BigInteger.valueOf(vTake).multiply(BigInteger.valueOf(array[i].temp)));
				}
				assert v == 0;
//				vt -= tempNeed * 1.0 * vNeed;
				res[q] = (byte) vt.compareTo(vtNeed);
			}
			if (res[0] <= 0 && 0 <= res[1]) {
				high = time;
			} else {
				low = time;
			}
		}
		return String.format("%.6f", high * 1.0 / tick);
	}
	
	class Source implements Comparable<Source> {
		long temp, rate;

		public Source(long r, long t) {
			this.temp = t;
			this.rate = r;
		}

		@Override
		public int compareTo(Source that) {
			return Long.compare(temp, that.temp);
		}
	}
	
	public B(Scanner in) {
		n = in.nextInt();
		vNeed = Math.round(in.nextDouble() * 10000);
		tempNeed = Math.round(in.nextDouble() * 10000);
		sources = new Source[n];
		for (int i = 0; i < n; i++) {
			sources[i] = new Source(Math.round(in.nextDouble() * 10000), Math.round(in.nextDouble() * 10000));
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
		int tests = in.nextInt();
		in.nextLine();
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

package facebook.y2017.round2;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

public class B {
	String solve() {
		int[] x = new int[n];
		int[] h = new int[n];
		x[0] = xs;
		h[0] = hs;
		for (int i = 1; i < n; i++) {
			x[i] = (int) (((long) ax * x[i - 1] + bx) % cx + 1);
			h[i] = (int) (((long) ah * h[i - 1] + bh) % ch + 1);
		}
		TreeMap<Integer, Integer> map = new TreeMap<>();
		cx = Math.max(cx, xs);
		ch = Math.max(ch, hs);
		map.put(-ch - 1, 0);
		map.put(cx + ch + 1, 0);
		long ans = 0;
		long area = 0;
		for (int i = 0; i < n; i++) {
			int x1 = x[i];
			int h1 = h[i];
			Entry<Integer, Integer> entry = map.floorEntry(x1);
			int x0 = entry.getKey();
			int h0 = entry.getValue();
			entry = map.ceilingEntry(x1);
			int x2 = entry.getKey();
			int h2 = entry.getValue();
			if (h1 <= h0 - x1 + x0 || h1 <= h2 - x2 + x1) {
				ans += area;
				continue;
			}
			area -= area(x0, h0, x2, h2);
			while (h0 <= h1 - x1 + x0) {
				entry = map.floorEntry(x0 - 1);
				int x00 = entry.getKey();
				int h00 = entry.getValue();
				area -= area(x00, h00, x0, h0);
				map.remove(x0);
				x0 = x00;
				h0 = h00;
			}
			while (h2 <= h1 - x2 + x1) {
				entry = map.ceilingEntry(x2 + 1);
				int x22 = entry.getKey();
				int h22 = entry.getValue();
				area -= area(x2, h2, x22, h22);
				map.remove(x2);
				x2 = x22;
				h2 = h22;
			}
			area += area(x0, h0, x1, h1);
			area += area(x1, h1, x2, h2);
			ans += area;
			map.put(x1, h1);
		}
		return "" + ans / 8.0;
	}
	
	static long area(int x0, int h0, int x2, int h2) {
		x0 *= 2;
		x2 *= 2;
		h0 *= 2;
		h2 *= 2;
		int x1 = (h0 - h2 + x0 + x2) / 2;
		if (x1 < x0 || x1 > x2) {
			throw new AssertionError(x0 + " " + h0 + " " + x2 + " " + h2);
		}
		int h1 = h0 - x1 + x0;
		if (h1 <= 0) {
			return (long) h0 * h0 + (long) h2 * h2;
		}
		return ((long) x1 - x0) * (h0 + h1) + ((long) x2 - x1) * (h1 + h2);
	}

	int n, xs, ax, bx, cx, hs, ah, bh, ch;
	
	public B(Scanner in) {
		n = in.nextInt();
		xs = in.nextInt();
		ax = in.nextInt();
		bx = in.nextInt();
		cx = in.nextInt();
		hs = in.nextInt();
		ah = in.nextInt();
		bh = in.nextInt();
		ch = in.nextInt();
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

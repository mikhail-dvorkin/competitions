package gcj.y2015.round3;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class E_wa {
	int n, d;
	int[] a;
	String wrong = "CHEATERS!";
	
	int solve1() {
		for (int i = 2 * d; i < n; i++) {
			if (a[i] != a[i - 2 * d]) {
				return Integer.MAX_VALUE;
			}
		}
		a = Arrays.copyOf(a, 2 * d);
		a = Arrays.copyOf(a, 4 * d);
		for (int i = 0; i < 2 * d; i++) {
			a[i + 2 * d] = a[i];
		}
		int ans = 0;
		for (int t = d; t >= 1;) {
			assert 4 * t == a.length;
			int[] c = new int[a.length + 1];
			for (int z = 0; z < a.length; z++) {
				c[z + 1] = c[z] + a[z];
			}
			int maxS = Integer.MIN_VALUE;
			int minS = Integer.MAX_VALUE;
			int pos = -1;
			int pos2 = -1;
			for (int i = 0; i < 2 * t; i++) {
				int s = c[i + t] - c[i];
				if (maxS < s) {
					maxS = s;
					pos = i;
				}
				if (minS > s) {
					minS = s;
					pos2 = i;
				}
			}
			@SuppressWarnings("unused")
			int pp = (true) ? pos : (pos2 + t);
//			System.out.println(t + " " + minS + " " + maxS);
//			System.out.println(Arrays.toString(a));
			if (maxS != minS) {
				for (int i = 0; i < t; i++) {
					for (int j = (pp + i) % (2 * t); j < a.length; j += 2 * t) {
						a[j]--;
						if (a[j] < 0) {
							return Integer.MAX_VALUE;
						}
					}
				}
				ans++;
				continue;
			}
			t /= 2;
			a = Arrays.copyOf(a, a.length / 2);
//			System.out.println(Arrays.toString(a));
		}
		return ans;
	}
	
	String solve() {
		int c = solve1();
		if (c == Integer.MAX_VALUE) {
			return "CHEATERS!";
		}
		return "" + c;
	}
	
	public E_wa(Scanner in) {
		n = in.nextInt();
		d = in.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = E_wa.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final E_wa testCase = new E_wa(in);
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

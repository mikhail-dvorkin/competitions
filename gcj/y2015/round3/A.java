package gcj.y2015.round3;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int n;
	int diff;
	int s0, as, cs, rs, m0, am, cm, rm;
	
	String solve() {
		int[] salary = new int[n];
		int[] parent = new int[n];
		salary[0] = s0;
		parent[0] = m0;
		for (int i = 1; i < n; i++) {
			salary[i] = (int) ((salary[i - 1] * 1L * as + cs) % rs);
			parent[i] = (int) ((parent[i - 1] * 1L * am + cm) % rm);
		}
		parent[0] = 0;
		for (int i = 1; i < n; i++) {
			parent[i] %= i;
		}
		int maxS = 0;
		for (int i = 0; i < n; i++) {
			maxS = Math.max(maxS, salary[i]);
		}
		int[] c = new int[maxS + diff + 2];
		int[] min = new int[n];
		int[] max = new int[n];
		min[0] = maxS;
		for (int i = 0; i < n; i++) {
			min[i] = Math.min(salary[i], min[parent[i]]);
			max[i] = Math.max(salary[i], max[parent[i]]);
			int sMin = max[i];
			int sMax = min[i] + diff;
			if (sMin <= sMax) {
				c[sMin]++;
				c[sMax + 1]--;
			}
		}
		int count = 0;
		int ans = 0;
		for (int i = 0; i < c.length; i++) {
			count += c[i];
			if (salary[0] <= i && i <= salary[0] + diff) {
				ans = Math.max(ans, count);
			}
		}
		return "" + ans;
	}
	
	public A(Scanner in) {
		n = in.nextInt();
		diff = in.nextInt();
		s0 = in.nextInt();
		as = in.nextInt();
		cs = in.nextInt();
		rs = in.nextInt();
		m0 = in.nextInt();
		am = in.nextInt();
		cm = in.nextInt();
		rm = in.nextInt();
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final A testCase = new A(in);
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

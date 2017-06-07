package facebook.y2017.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class D {
	public static final int P = 1_000_000_007;
	int n, m;
	int[] r;
	
	String solve() {
		if (n == 1) {
			return "" + m;
		}
		m--;
		Arrays.sort(r);
		for (int i = 0; i < n; i++) {
			m -= 2 * r[i];
		}
		int[] memo = new int[r[n - 2] + r[n - 1] + 1];
		Arrays.fill(memo, -1);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int rSum = r[i] + r[j];
				int s = m + rSum;
				if (s < 0) {
					continue;
				}
				if (memo[rSum] == -1) {
					int c = modInverse(n * (n - 1));
					for (int k = s + 1; k <= s + n; k++) {
						c = (int) (1L * c * k % P);
					}
					memo[rSum] = c;
				}
				ans = (ans + memo[rSum]) % P;
			}
		}
		return "" + ans * 2 % P;
	}
	
	public static int gcdExtended(int a, int b, int[] xy) {
		if (a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		int d = gcdExtended(b % a, a, xy);
		int t = xy[0];
		xy[0] = xy[1] - (b / a) * xy[0];
		xy[1] = t;
		return d;
	}
	
	public static int modInverse(int x) {
		int[] xy = new int[2];
		int gcd = gcdExtended(x, P, xy);
		if (gcd != 1) {
			throw new IllegalArgumentException(x + ", " + P);
		}
		int result = xy[0] % P;
		if (result < 0) {
			result += P;
		}
		return result;
	}
	
	public D(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		r = new int[n];
		for (int i = 0; i < n; i++) {
			r[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final D testCase = new D(in);
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

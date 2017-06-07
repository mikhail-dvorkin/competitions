package facebook.y2015.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class A {
	int a, b, k;
	
	String solve() {
		boolean[] isPrime = isPrimeArray(b);
		int[] primacity = new int[b + 1];
		for (int p = 2; p <= b; p++) {
			if (!isPrime[p]) {
				continue;
			}
			for (int i = p; i <= b; i += p) {
				primacity[i]++;
			}
		}
		int ans = 0;
		for (int i = a; i <= b; i++) {
			if (primacity[i] == k) {
				ans++;
			}
		}
		return "" + ans;
	}
	
	public static boolean[] isPrimeArray(int n) {
		boolean[] isPrime = new boolean[n + 1];
		for (int i = 2; i <= n; i++)
			isPrime[i] = true;
		for (int i = 2, j; (j = i * i) <= n; i++) {
			if (!isPrime[i])
				continue;
			do {
				isPrime[j] = false;
				j += i;
			} while (j <= n);
		}
		return isPrime;
	}
	
	public A(Scanner in) {
		a = in.nextInt();
		b = in.nextInt();
		k = in.nextInt();
	}
	
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final A testCase = new A(in);
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

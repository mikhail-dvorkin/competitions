package facebook.y2015.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int n;
	String[] s;
	
	String solve() {
		TreeSet<String> prev = new TreeSet<String>();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			String a = prev.floor(s[i]);
			String b = prev.ceiling(s[i]);
			int common = Math.max(common(s[i], a), common(s[i], b));
			if (common == s[i].length()) {
				ans += common;
			} else {
				ans += common + 1;
			}
			prev.add(s[i]);
		}
		return "" + ans;
	}
	
	int common(String a, String b) {
		if (b == null) {
			return 0;
		}
		int res = 0;
		while (res < a.length() && res < b.length() && a.charAt(res) == b.charAt(res)) {
			res++;
		}
		return res;
	}
	
	public B(Scanner in) {
		n = in.nextInt();
		s = new String[n];
		for (int i = 0; i < n; i++) {
			s[i] = in.next();
		}
	}
	
	private static String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final B testCase = new B(in);
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

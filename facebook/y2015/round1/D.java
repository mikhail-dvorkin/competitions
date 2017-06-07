package facebook.y2015.round1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class D {
	int n;
	int[] p;
	ArrayList<Integer>[] kids;
	int[] best;
	int[] diff;
	int[] sum;
	
	@SuppressWarnings("unchecked")
	String solve() {
		kids = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			kids[i] = new ArrayList<Integer>();
		}
		for (int i = 1; i < n; i++) {
			kids[p[i]].add(i);
		}
		best = new int[n];
		sum = new int[n];
		diff = new int[n];
		dfs(0);
		return "" + sum[0];
	}
	
	void dfs(int v) {
		if (kids[v].isEmpty()) {
			best[v] = 1;
			sum[v] = 1;
			diff[v] = 1;
			return;
		}
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		int total = 0;
		for (int u : kids[v]) {
			dfs(u);
			total += sum[u];
			int b = best[u];
			map.put(b, map.getOrDefault(b, 0) + diff[u]);
		}
		int free = map.lastKey() + 1;
		best[v] = free;
		sum[v] = total + free;
		diff[v] = 1;
		for (int i = map.lastKey(); i >= 1; i--) {
			int cur = total + i + map.getOrDefault(i, 0);
			if (cur < sum[v]) {
				diff[v] = sum[v] - cur;
				sum[v] = cur;
				best[v] = i;
			} else {
				diff[v] = Math.min(diff[v], cur - sum[v]);
			}
		}
	}
	
	public D(Scanner in) {
		n = in.nextInt();
		p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = in.nextInt() - 1;
		}
	}
	
	private static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
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
			final D testCase = new D(in);
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

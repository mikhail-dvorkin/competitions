package facebook.y2018.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class B {
	int n, m, a, b;
	int[] p;
	int[] buyers;
	ArrayList<Integer>[] kids;
	long ans;
	
	@SuppressWarnings("unchecked")
	String solve() {
		buyers = new int[n];
		for (int i = 0; i < m; i++) {
			int c = (int) ((a * (long) i + b) % n);
			buyers[c]++;
		}
		kids = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			kids[i] = new ArrayList<>();
		}
		for (int i = 1; i < n; i++) {
			kids[p[i]].add(i);
		}
		dfs(0);
		return "" + ans;
	}
	
	TreeSet<Integer> dfs(int v) {
		TreeSet<Integer> leftOvers = new TreeSet<>();
		for (int u : kids[v]) {
			TreeSet<Integer> fromSubTree = dfs(u);
			if (leftOvers.size() >= fromSubTree.size()) {
				leftOvers.addAll(fromSubTree);
			} else {
				fromSubTree.addAll(leftOvers);
				leftOvers = fromSubTree;
			}
		}
		leftOvers.add(v);
		while (!leftOvers.isEmpty() && buyers[v] > 0) {
			buyers[v]--;
			ans += leftOvers.pollLast();
		}
		return leftOvers;
	}

	public B(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		p = new int[n];
		p[0] = -1;
		for (int i = 1; i < n; i++) {
			p[i] = in.nextInt();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		/* "Google Code Jam, Facebook Hacker Cup" */
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
//		/* "IPSC" */
//		String fileNameSuffix = "0";
//		String formatOut = "%s";
//		String formatSystemOut = "Case #%2$d solved.";//"Case #%2$d: %1$s";
		
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

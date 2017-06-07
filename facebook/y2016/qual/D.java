package facebook.y2016.qual;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class D {
	int n, k;
	String[] words;
	int inf = Integer.MAX_VALUE / 2;
	
	String solve() {
		Node root = new Node();
		for (String s : words) {
			root.add(s, 0);
		}
		int[] ans = root.solve();
		return "" + ans[k];
	}
	
	class Node {
		Node[] nodes = new Node[26];
		boolean word = false;

		void add(String s, int index) {
			if (index == s.length()) {
				word = true;
				return;
			}
			int x = s.charAt(index) - 'a';
			if (nodes[x] == null) {
				nodes[x] = new Node();
			}
			nodes[x].add(s, index + 1);
		}
		
		int[] solve() {
			int[] a = new int[k + 1];
			Arrays.fill(a, inf);
			a[0] = 0;
			int max = 0;
			if (word) {
				a[1] = 1;
				max = 1;
			}
			for (Node node : nodes) {
				if (node == null) {
					continue;
				}
				int[] c = node.solve();
				int max2 = 0;
				while (max2 + 1 <= k && c[max2 + 1] != inf) {
					max2++;
				}
				for (int i = Math.min(k, max + max2); i > 0; i--) {
					for (int j = Math.max(i - max, 1); j <= max2 && j <= i; j++) {
						a[i] = Math.min(a[i], a[i - j] + c[j] + 2);
					}
				}
				max += max2;
				if (max > k) {
					max = k;
				}
			}
			return a;
		}
	}
	
	public D(Scanner in) {
		n = in.nextInt();
		k = in.nextInt();
		words = new String[n];
		for (int i = 0; i < n; i++) {
			words[i] = in.next();
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

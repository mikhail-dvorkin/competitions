package gcj.y2015.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C_small {
	int m;
	String[] s;
	
	String solve() {
		TreeMap<String, Integer> words = new TreeMap<>();
		int[][] ids = new int[m][];
		for (int i = 0; i < m; i++) {
			TreeSet<Integer> set = new TreeSet<>();
			for (String w : s[i].split("\\s+")) {
				if (!words.containsKey(w)) {
					words.put(w, words.size());
				}
				set.add(words.get(w));
			}
			ids[i] = new int[set.size()];
			int k = 0;
			for (int x : set) {
				ids[i][k++] = x;
			}
		}
		int wordsCount = words.size();
		int[] which = new int[wordsCount];
		int ans = Integer.MAX_VALUE;
		for (int mask = 0; mask < (1 << m); mask++) {
			if ((mask & 3) != 1) {
				continue;
			}
			Arrays.fill(which, 0);
			for (int i = 0; i < m; i++) {
				int lang = ((mask & (1 << i)) != 0) ? 1 : 2;
				for (int id : ids[i]) {
					which[id] |= lang;
				}
			}
			int common = 0;
			for (int i = 0; i < wordsCount; i++) {
				if (which[i] == 3) {
					common++;
				}
			}
			ans = Math.min(ans, common);
		}
		return "" + ans;
	}
	
	public C_small(Scanner in) {
		m = in.nextInt();
		in.nextLine();
		s = new String[m];
		for (int i = 0; i < m; i++) {
			s[i] = in.nextLine();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = C_small.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final C_small testCase = new C_small(in);
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

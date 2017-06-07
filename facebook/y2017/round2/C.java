package facebook.y2017.round2;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class C {
	static final int P = 1_000_000_007;
	
	String solve() {
		int[] wArray = new int[m];
		int[] dArray = new int[m];
		int[] sArray = new int[m];
		wArray[0] = sw;
		dArray[0] = sd;
		sArray[0] = ss;
		for (int i = 1; i < m; i++) {
			wArray[i] = (int) (((long) aw * wArray[i - 1] + bw) % n + 1);
			dArray[i] = (ad * dArray[i - 1] + bd) % 3;
			sArray[i] = (int) (((long) as * sArray[i - 1] + bs) % 1_000_000_000 + 1);
		}
		Node root = new Node(0, n);
		int ans = 0;
		for (int k = 0; k < m; k++) {
			int z = Math.max(1, Math.min(n, wArray[k] + dArray[k] - 1)) - 1;
			int w = wArray[k] - 1;
			int s = sArray[k];
			root.update(w, z, s);
			ans += root.ans;
			ans %= P;
		}
		return "" + ans;
	}
	
	static class Node {
		int low, high, mid;
		int ans, ansLeft, ansRight, ansBoth;
		int sLeft, sRight;
		Node left, right;

		Node(int low, int high) {
			this.low = low;
			this.high = high;
			ans = ansLeft = ansRight = 1;
			if (low + 1 < high) {
				mid = (low + high) / 2;
				left = new Node(low, mid);
				right = new Node(mid, high);
				ansBoth = 1;
			}
		}

		void update(int w, int z, int s) {
			if (z < low) {
				sLeft += s;
				sLeft %= P;
			} else if (z >= high) {
				sRight += s;
				sRight %= P;
			}
			if (left == null) {
				if (z == w) {
					ans += s;
					ans %= P;
				}
				return;
			}
			if (w < mid) {
				left.update(w, z, s);
			} else {
				right.update(w, z, s);
			}
			long cross = (long) left.sRight * right.sLeft % P;
			ans = (int) (((long) left.ans * right.ans + cross * left.ansRight % P * right.ansLeft) % P);
			ansLeft = (int) (((long) left.ansLeft * right.ans + cross * left.ansBoth % P * right.ansLeft) % P);
			ansRight = (int) (((long) left.ans * right.ansRight + cross * left.ansRight % P * right.ansBoth) % P);
			ansBoth = (int) (((long) left.ansLeft * right.ansRight + cross * left.ansBoth % P * right.ansBoth) % P);
		}
	}
	
	int n, m, sw, aw, bw, sd, ad, bd, ss, as, bs;
	
	public C(Scanner in) {
		n = in.nextInt();
		m = in.nextInt();
		sw = in.nextInt();
		aw = in.nextInt();
		bw = in.nextInt();
		sd = in.nextInt();
		ad = in.nextInt();
		bd = in.nextInt();
		ss = in.nextInt();
		as = in.nextInt();
		bs = in.nextInt();
	}
	
	public static void main(String[] args) throws Exception {
		int nThreads = 4;
		String fileNameSuffix = "";
		String formatOut = "Case #%2$d: %1$s";
		String formatSystemOut = formatOut;
		
		String fileName = C.class.getSimpleName().replaceFirst("_.*", "").toLowerCase() + fileNameSuffix;
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
			final C testCase = new C(in);
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

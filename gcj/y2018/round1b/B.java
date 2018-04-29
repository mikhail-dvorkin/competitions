package gcj.y2018.round1b;
import java.io.*;
import java.util.*;

public class B {
	void run() {
		int n = in.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		for (int i = 0; i < n; i++) {
			int d = in.nextInt();
			a[i] = d + in.nextInt();
			b[i] = d - in.nextInt();
		}
		int low = 0;
		int high = 0;
		TreeMap<Integer, Integer> countA = new TreeMap<>();
		TreeMap<Integer, Integer> countB = new TreeMap<>();
		TreeMap<Long, Integer> countPair = new TreeMap<>();
		int ans = 0;
		int ansCount = 0;
		int aPrev = a[0] + 1;
		int aPrevAbsent = -1;
		int bPrev = b[0] + 1;
		int bPrevAbsent = -1;
		while (high < n) {
			int aHigh = a[high];
			int bHigh = b[high];
			high++;
			countA.put(aHigh, countA.getOrDefault(aHigh, 0) + 1);
			countB.put(bHigh, countB.getOrDefault(bHigh, 0) + 1);
			long pairHigh = pair(aHigh, bHigh);
			countPair.put(pairHigh, countPair.getOrDefault(pairHigh, 0) + 1);
			int aAbsent = high - 2;
			int bAbsent = high - 2;
			if (aPrev == aHigh) aAbsent = aPrevAbsent;
			if (bPrev == bHigh) bAbsent = bPrevAbsent;
			while (low < high) {
				int aLow = a[low];
				int bLow = b[low];
				int len = high - low;
				
				boolean good = false;
				if (aAbsent == -1 || bAbsent == -1) {
					good = true;
				} else {
					int[] v1 = new int[] {aHigh, b[aAbsent]};
					int[] v2 = new int[] {a[bAbsent], bHigh};
					for (int[] array : new int[][] {v1, v2}) {
						int c = array[0];
						int d = array[1];
						int covered = countA.get(c) + countB.get(d) - countPair.getOrDefault(pair(c, d), 0);
						if (covered == len) {
							good = true;
						}
					}
				}
				
				if (good) {
					if (len > ans) {
						ans = len;
						ansCount = 1;
					} else if (len == ans) {
						ansCount++;
					}
					break;
				}
				countA.put(aLow, countA.get(aLow) - 1);
				countB.put(bLow, countB.get(bLow) - 1);
				long pairLow = pair(aLow, bLow);
				countPair.put(pairLow, countPair.get(pairLow) - 1);
				low++;
			}
			aPrev = aHigh;
			bPrev = bHigh;
			aPrevAbsent = aAbsent;
			bPrevAbsent = bAbsent;
		}
		out.println(ans + " " + ansCount);
	}
	
	static long pair(int a, int b) {
		return a * (1L << 32) + b;
	}

	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		boolean stdStreams = true;
		String fileName = B.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
		String inputFileName = fileName + ".in";
		String outputFileName = fileName + ".out";
		
		Locale.setDefault(Locale.US);
		BufferedReader br;
		if (stdStreams) {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			br = new BufferedReader(new FileReader(inputFileName));
			out = new PrintWriter(outputFileName);
		}
		in = new MyScanner(br);
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			out.print("Case #" + (test + 1) + ": ");
			new B().run();
		}
		br.close();
		out.close();
	}
	
	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		MyScanner(BufferedReader br) {
			this.br = br;
		}
		
		void findToken() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		String next() {
			findToken();
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}

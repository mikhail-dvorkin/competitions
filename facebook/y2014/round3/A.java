package facebook.y2014.round3;
import java.io.*;
import java.util.*;

public class A {
	private static String fileName = A.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	private static String inputFileName = fileName + ".in";
	private static String outputFileName = fileName + ".out";
	private static Scanner in;
	private static PrintWriter out;
	
	final static int M = 1000000007;
	final static int S = 4;
	
	final static Map<State, Integer> theMap = new HashMap<State, Integer>();

	private void solve() {
		int k = in.nextInt();
		int[] a = new int[k];
		int[] count = new int[S + 1];
		for (int i = 0; i < k; i++) {
			a[i] = in.nextInt();
			count[a[i]]++;
		}
		State init = new State(count);
		out.println(init.calc());
	}
	
	class State {
		int[] count;
		boolean open;
		int firstSize;
		boolean inFirst;
		int proh;
		int hash;
		
		@Override
		public int hashCode() {
			return hash;
		}
		
		@Override
		public boolean equals(Object obj) {
			State that = (State) obj;
			return Arrays.equals(count, that.count) &&
					open == that.open &&
					firstSize == that.firstSize &&
					inFirst == that.inFirst &&
					proh == that.proh;
		}
		
		public State(int[] count) {
			this.count = count;
			this.open = false;
			hash = -Arrays.hashCode(count);
		}

		public State(int[] count, boolean open, int firstSize, boolean inFirst, int proh) {
			this.count = count;
			this.open = open;
			this.firstSize = firstSize;
			this.inFirst = inFirst;
			this.proh = proh;
			if (inFirst == (proh >= 0)) {
				throw new RuntimeException();
			}
			hash = -Arrays.hashCode(count);
			hash = 5 * hash + firstSize;
			hash = 5 * hash + proh;
			hash = 5 * hash + (open ? 2 : 0) + (inFirst ? 1 : 0);
		}

		public int calc() {
			if (theMap.containsKey(this)) {
				return theMap.get(this);
			}
			int res = 0;
			if (!open) {
				int largest = S;
				while (largest > 0 && count[largest] == 0) {
					largest--;
				}
				if (largest == 0) {
					res = 1;
				} else {
					int[] c = count.clone();
					c[largest]--;
					State next = new State(c, true, largest - 1, true, -1);
					res = next.calc();
				}
			} else {
				if (!inFirst) {
					int[] c = count.clone();
					if (firstSize > 0) {
						c[firstSize]++;
					}
					State next = new State(c);
					res = next.calc();
				}
				if (firstSize > 0 && !inFirst) {
					State next = new State(count, true, firstSize - 1, true, -1);
					res = (int) ((res + next.calc() * (long) (firstSize)) % M);
				}
				for (int s = 1; s <= S; s++) {
					int poss = count[s];
					if (proh == s) {
						poss--;
					}
					if (poss == 0) {
						continue;
					}
					int[] c = count.clone();
					c[s]--;
					if (s > 1) {
						c[s - 1]++;
					}
					State next = new State(c, true, firstSize, false, s - 1);
					res = (int) ((res + next.calc() * (long) (poss * s)) % M);
				}
			}
			theMap.put(this, res);
			return res;
		}
	}

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		if (args.length >= 2) {
			inputFileName = args[0];
			outputFileName = args[1];
		}
		in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		int tests = in.nextInt();
		in.nextLine();
		for (int t = 1; t <= tests; t++) {
			out.print("Case #" + t + ": ");
			new A().solve();
			System.out.println("Case #" + t + ": solved");
		}
		in.close();
		out.close();
	}
}

package rcc.y2015.qual3;
import java.io.*;
import java.util.*;

public class D {
	void run() {
		int n = in.nextInt();
		int m = in.nextInt();
		BitSet all = new BitSet(n);
		all.set(0, n);
		BitSet desired = readBitSet();
		boolean[] turnsOn = new boolean[m];
		BitSet[] changes = new BitSet[m];
		for (int i = 0; i < m; i++) {
			turnsOn[i] = in.nextInt() == 1;
			changes[i] = readBitSet();
		}
		BitSet needOn = (BitSet) desired.clone();
		BitSet needOff = (BitSet) all.clone();
		needOff.andNot(desired);
		ArrayList<Integer> ans = new ArrayList<Integer>();
		int prevScore = n;
		for (;;) {
			for (int i = 0; i < m; i++) {
				if (turnsOn[i]) {
					if (changes[i].intersects(needOff)) {
						continue;
					}
					if (!changes[i].intersects(needOn)) {
						continue;
					}
					needOn.andNot(changes[i]);
					ans.add(i);
				} else {
					if (changes[i].intersects(needOn)) {
						continue;
					}
					if (!changes[i].intersects(needOff)) {
						continue;
					}
					needOff.andNot(changes[i]);
					ans.add(i);
				}
			}
			int score = needOn.cardinality() + needOff.cardinality();
			if (score == prevScore) {
				System.out.println("NO");
				return;
			}
			if (score == 0) {
				break;
			}
			prevScore = score;
		}
		Collections.reverse(ans);
		System.out.println("YES");
		System.out.println(ans.size());
		for (int i : ans) {
			System.out.print((i + 1) + " ");
		}
	}
	
	BitSet readBitSet() {
		String s = in.next();
		BitSet b = new BitSet(s.length());
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) == '1') {
				b.set(i);
			}
		}
		return b;
	}

	static boolean stdStreams = true;
	static String fileName = D.class.getSimpleName().replaceFirst("_.*", "").toLowerCase();
	static String inputFileName = fileName + ".in";
	static String outputFileName = fileName + ".out";
	static MyScanner in;
	static PrintWriter out;

	public static void main(String[] args) throws IOException {
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
		int tests = 1;//in.nextInt();
		for (int test = 0; test < tests; test++) {
			new D().run();
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

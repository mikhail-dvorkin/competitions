package dgcj.y2016.finals;

import dgcj.message;
import java.util.*;

public class A {
	static final Object PROBLEM = new encoded_sum(); // PROBLEM NAME goes here
	static final int M = 1000000007;
	static final int T = 10;

	public String run() {
		int n = (int) encoded_sum.GetLength();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		int[] first = new int[T * T];
		int[] val = new int[T];
		Arrays.fill(first, n);
		long t = power(10, n - to);
		for (int i = to - 1; i >= from; i--) {
			int a = encoded_sum.GetScrollOne(i) - 'A';
			int b = encoded_sum.GetScrollTwo(i) - 'A';
			int c = a * T + b;
			first[c] = Math.min(first[c], i);
			val[a] = (int) ((val[a] + t) % M); 
			val[b] = (int) ((val[b] + t) % M);
			t = (t * 10) % M;
		}
		
		if (ID > 0) {
			for (int i = 0; i < T; i++) {
				message.PutInt(0, val[i]);
			}
			for (int i = 0; i < first.length; i++) {
				message.PutInt(0, first[i]);
			}
			message.Send(0);
			return null;
		}
		
		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			for (int i = 0; i < T; i++) {
				int v = message.GetInt(id);
				val[i] = (val[i] + v) % M;
			}
			for (int i = 0; i < first.length; i++) {
				first[i] = Math.min(first[i], message.GetInt(id));
			}
		}
		
		final int[][] larger = new int[T][T];
		for (int a = 0; a < T; a++) {
			for (int b = a + 1; b < T; b++) {
				int aFirst = n;
				int bFirst = n;
				int aOnly = n;
				int bOnly = n;
				for (int c = 0; c < T; c++) {
					int ac = Math.min(first[T * a + c], first[T * c + a]);
					int bc = Math.min(first[T * b + c], first[T * c + b]);
					aFirst = Math.min(aFirst, ac);
					bFirst = Math.min(bFirst, bc);
					if (c != b) {
						aOnly = Math.min(aOnly, ac);
					}
					if (c != a) {
						bOnly = Math.min(bOnly, bc);
					}
				}
				larger[a][b] = Integer.compare(aFirst, bFirst);
				if (larger[a][b] == 0) {
					larger[a][b] = Integer.compare(aOnly, bOnly);
				}
				larger[b][a] = -larger[a][b];
			}
		}
		
		Integer[] array = new Integer[T];
		for (int i = 0; i < T; i++) {
			array[i] = i;
		}
		Arrays.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return larger[a][b];
			}
		});
		
		long ans = 0;
		for (int i = 0; i < T; i++) {
			ans = (ans + (T - 1L - i) * val[array[i]]) % M;
		}
		return ans + "";
	}
	
	private static long power(long base, long p) {
		if (p == 0)
			return 1;
		if (p == 1)
			return base % M;
		long v = power(base, p / 2);
		v = (v * v) % M;
		return (p & 1) == 0 ? v : (v * base) % M;
	}
	
	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

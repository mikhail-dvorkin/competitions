package dgcj.y2017.round1;

import dgcj.message;

public class B {
	static final Object PROBLEM = new weird_editor(); // PROBLEM NAME goes here
	static final int M = 1000000007;

	public String run() {
		int n = (int) weird_editor.GetNumberLength();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int m = to - from;
		int[] digit = new int[m];
		for (int i = 0; i < m; i++) {
			digit[i] = (int) weird_editor.GetDigit(from + i);
		}
		int max = 0;
		for (int d : digit) {
			max = Math.max(max, d);
		}
		
		int maxRight = 0;
		if (ID < NODES - 1) {
			message.Receive(ID + 1);
			maxRight = message.GetInt(ID + 1);
		}
		if (ID > 0) {
			message.PutInt(ID - 1, Math.max(max, maxRight));
			message.Send(ID - 1);
		}
		
		boolean[] take = new boolean[m];
		for (int i = m - 1; i >= 0; i--) {
			int d = digit[i];
			if (d < maxRight) {
				continue;
			}
			maxRight = Math.max(maxRight, d);
			take[i] = true;
		}
		
		int val = 0;
		int len = 0;
		for (int i = 0; i < m; i++) {
			if (take[i]) {
				val = (int) ((val * 10L + digit[i]) % M);
				len++;
			}
		}
		long ten = power(10, len);
		
		int leftVal = 0;
		int leftLen = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			leftVal = message.GetInt(ID - 1);
			leftLen = message.GetInt(ID - 1);
		}
		
		val = (int) ((leftVal * 1L * ten + val) % M);
		len += leftLen;
		
		if (ID < NODES - 1) {
			message.PutInt(ID + 1, val);
			message.PutInt(ID + 1, len);
			message.Send(ID + 1);
			return null;
		}
		
		val = (int) ((val * power(10, n - len)) % M);
		return val + "";
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

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

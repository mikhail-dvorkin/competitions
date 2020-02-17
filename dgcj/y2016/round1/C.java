package dgcj.y2016.round1;

import dgcj.message;

public class C {
	static final Object PROBLEM = new crates(); // PROBLEM NAME goes here
	static final int M = 1000000007;

	public String run() {
		int n = (int) crates.GetNumStacks();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int m = to - from;
		int[] a = new int[m];
		long sum = 0;
		for (int i = 0; i < m; i++) {
			a[i] = (int) crates.GetStackHeight(from + i + 1);
			sum += a[i];
		}
		
		long prevSum = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			prevSum = message.GetLL(ID - 1);
		}
		long nextSum = prevSum + sum;
		if (ID < NODES - 1) {
			message.PutLL(ID + 1, nextSum);
			message.Send(ID + 1);
		}
		
		long totalSum;
		if (ID == NODES - 1) {
			totalSum = nextSum;
			for (int i = 0; i < ID; i++) {
				message.PutLL(i, totalSum);
				message.Send(i);
			}
		} else {
			message.Receive(NODES - 1);
			totalSum = message.GetLL(NODES - 1);
		}
		
		long h = totalSum / n;
		int first = (int) (totalSum % n);
		long prevMustHave = from * h + Math.min(from, first);
		long result = 0;
		for (int i = 0; i < m; i++) {
			result += Math.abs(prevSum - prevMustHave);
			result %= M;
			prevMustHave += h;
			if (from + i < first) {
				prevMustHave++;
			}
			prevSum += a[i];
		}
		
		if (ID > 0) {
			message.PutLL(0, result);
			message.Send(0);
			return null;
		}
		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			result += message.GetLL(i);
			result %= M;
		}
		return "" + result;
	}
	
	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

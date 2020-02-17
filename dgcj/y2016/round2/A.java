package dgcj.y2016.round2;

import dgcj.message;

public class A {
	static final Object PROBLEM = new again(); // PROBLEM NAME goes here
	
	static final long M = 1000000007;

	public String run() {
		long n = again.GetN();
		
		long sumA = 0;
		long sumB = 0;
		for (int i = ID; i < n; i += NODES) {
			sumA += again.GetA(i);
			sumB += again.GetB(i);
		}
		message.PutInt(0, (int) (sumA % M));
		message.PutInt(0, (int) (sumB % M));
		message.Send(0);
		if (ID > 0) {
			return null;
		}
		
		int[] a = new int[NODES];
		int[] b = new int[NODES];
		for (int J = 0; J < NODES; J++) {
			message.Receive(J);
			a[J] = message.GetInt(J);
			b[J] = message.GetInt(J);
		}
		
		long ans = 0;
		for (int i = 0; i < NODES; i++) {
			for (int j = 0; j < NODES; j++) {
				if ((i + j) % NODES > 0) {
					ans += 1L * a[i] * b[j];
					ans %= M;
				}
			}
		}

		return "" + ans;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

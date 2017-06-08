package dgcj.y2015.round1;

import java.util.Arrays;

import dgcj.message;

public class A {
	static final Object PROBLEM = new almost_sorted(); // PROBLEM NAME goes here
	static final int MOD = 1 << 20;

	public String run() {
		int n = (int) almost_sorted.NumberOfFiles();
		int shift = (int) almost_sorted.MaxDistance();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int fromWide = Math.max(from - 2 * shift, 0);
		int toWide = Math.min(to + 2 * shift, n);
		long[] data = new long[toWide - fromWide];
		for (int i = fromWide; i < toWide; i++) {
			data[i - fromWide] = almost_sorted.Identifier(i);
		}
		Arrays.sort(data);
		int ans = 0;
		for (int i = from; i < to; i++) {
			ans = (int) ((ans + (data[i - fromWide] % MOD) * i) % MOD);
		}
		if (ID > 0) {
			message.PutInt(0, ans);
			message.Send(0);
			return null;
		}
		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			ans = (ans + message.GetInt(id)) % MOD;
		}
		return "" + ans;
	}
	
	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

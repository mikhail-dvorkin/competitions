package dgcj.y2016.round1;

import dgcj.message;

public class A {
	static final Object PROBLEM = new oops(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) oops.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for (int i = from; i < to; i++) {
			min = Math.min(min, oops.GetNumber(i));
			max = Math.max(max, oops.GetNumber(i));
		}
		
		if (ID > 0) {
			message.PutLL(0, min);
			message.PutLL(0, max);
			message.Send(0);
			return null;
		}
		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			min = Math.min(min, message.GetLL(i));
			max = Math.max(max, message.GetLL(i));
		}
		return max - min + "";
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

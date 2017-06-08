package dgcj.y2017.round1;

import dgcj.message;

public class A {
	static final Object PROBLEM = new pancakes(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) pancakes.GetStackSize();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		int x = 0;
		if (from > 0) {
			x = (int) pancakes.GetStackItem(from - 1);
		}
		int jumps = 0;
		for (int i = from; i < to; i++) {
			int y = (int) pancakes.GetStackItem(i);
			if (y < x) {
				jumps++;
			}
			x = y;
		}
		
		if (ID > 0) {
			message.PutInt(0, jumps);
			message.Send(0);
			return null;
		}
		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			jumps += message.GetInt(i);
		}
		return jumps + 1 + "";
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

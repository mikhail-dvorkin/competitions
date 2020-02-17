package dgcj.y2016.round2;

import dgcj.message;

public class B {
	static final Object PROBLEM = new lisp_plus_plus(); // PROBLEM NAME goes here
	
	public String run() {
		int n = (int) lisp_plus_plus.GetLength();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int len = to - from;
		
		byte[] open = new byte[len];
		int balance = 0;
		for (int i = 0; i < len; i++) {
			open[i] = (byte) ((lisp_plus_plus.GetCharacter(from + i) == '(') ? 1 : -1);
			balance += open[i];
		}
		
		int prevBalace = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			prevBalace = message.GetInt(ID - 1);
		}
		int nextBalance = prevBalace + balance;
		if (ID < LAST) {
			message.PutInt(ID + 1, nextBalance);
			message.Send(ID + 1);
		}
		
		int minError = Integer.MAX_VALUE;
		balance = prevBalace;
		for (int i = 0; i < len; i++) {
			balance += open[i];
			if (balance < 0) {
				minError = from + i;
				break;
			}
		}
		
		if (ID < LAST) {
			message.PutInt(LAST, minError);
			message.Send(LAST);
			return null;
		}
		
		for (int J = 0; J < LAST; J++) {
			message.Receive(J);
			minError = Math.min(minError, message.GetInt(J));
		}
		if (minError == Integer.MAX_VALUE) {
			if (balance == 0) {
				return "-1";
			}
			return "" + n;
		}
		return "" + minError;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();
	final int LAST = NODES - 1;

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

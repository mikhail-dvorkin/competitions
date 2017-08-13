package dgcj.y2017.round2;

import dgcj.message;

public class A_worse {
	static final Object PROBLEM = new flagpoles(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) flagpoles.GetNumFlagpoles();
		if (n == 1) {
			return ID == 0 ? "1" : null;
		}
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		if (ID >= NODES) {
			return null;
		}
		
		long prev = 0, prevD = 0;
		if (ID > 0) {
			prev = flagpoles.GetHeight(from - 1);
			prevD = prev - flagpoles.GetHeight(from - 2);
		}
		boolean cont = true;
		int contSize = 0;
		int localSize = from == 0 ? 0 : 1;
		int best = localSize;
		for (int i = from; i < to; i++) {
			long v = flagpoles.GetHeight(i);
			if (cont) {
				if (v == prev + prevD) {
					contSize++;
				} else {
					cont = false;
				}
			}
			if (v == prev + prevD) {
				localSize++;
			} else {
				localSize = 2;
				if (i == 0) {
					localSize = 1;
				}
			}
			best = Math.max(best, localSize);
			prevD = v - prev;
			prev = v;
		}
		
		int left = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			left = message.GetInt(ID - 1);
			best = Math.max(best, message.GetInt(ID - 1));
		}
		left += contSize;
		best = Math.max(best, left);
		int right = localSize;
		if (contSize == to - from) {
			right = left;
		}
		
		if (ID < NODES - 1) {
			message.PutInt(ID + 1, right);
			message.PutInt(ID + 1, best);
			message.Send(ID + 1);
			return null;
		}
		return "" + best;
	}
	
	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : Math.min(message.NumberOfNodes(), (int) flagpoles.GetNumFlagpoles() / 2);
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new A_worse().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

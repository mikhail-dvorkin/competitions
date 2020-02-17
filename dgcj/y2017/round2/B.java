package dgcj.y2017.round2;

import dgcj.message;

public class B {
	static final Object PROBLEM = new number_bases(); // PROBLEM NAME goes here
	String NO = "IMPOSSIBLE";
	String MANY = "NON-UNIQUE";

	public String run() {
		int n = (int) number_bases.GetLength();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		int low = 2;
		int[] move = new int[2];
		int[] poss = new int[2];
		move[1] = 1;
		
		for (int i = from; i < to; i++) {
			int x = (int) number_bases.GetDigitX(i);
			int y = (int) number_bases.GetDigitY(i);
			int z = (int) number_bases.GetDigitZ(i);
			low = Math.max(low, x + 1);
			low = Math.max(low, y + 1);
			low = Math.max(low, z + 1);
			for (int t = 0; t < 2; t++) {
				int m = move[t];
				int p = poss[t];
				int b = x + y + m - z;
				if (b == 0) {
					move[t] = 0;
					continue;
				}
				move[t] = 1;
				if (b <= 1) {
					p = -1;
				}
				if (p == 0) {
					p = b;
				} else if (p > 0 && p != b) {
					p = -1;
				}
				poss[t] = p;
			}
		}
		
		int t = 0;
		int p = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			t = message.GetInt(ID - 1);
			p = message.GetInt(ID - 1);
			low = Math.max(low, message.GetInt(ID - 1));
		}
		if (poss[t] == -1 || p == -1) {
			p = -1;
		} else if (poss[t] == 0 || p == 0) {
			p = poss[t] + p;
		} else if (poss[t] != p) {
			p = -1;
		}
		t = move[t];
		if (ID < NODES - 1) {
			message.PutInt(ID + 1, t);
			message.PutInt(ID + 1, p);
			message.PutInt(ID + 1, low);
			message.Send(ID + 1);
			return null;
		}
		if (t != 0 || p == -1) {
			return NO;
		}
		if (p == 0) {
			return MANY;
		}
		if (p < low) {
			return NO;
		}
		return "" + p;
	}
	
	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

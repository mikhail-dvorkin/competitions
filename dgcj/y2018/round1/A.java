package dgcj.y2018.round1;

import dgcj.message;

public class A {
	static final Object PROBLEM = new stones(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) (stones.GetNumberOfStones() + 1);
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		int len = to - from;
		int[] bestHere = new int[len];
		int bestHereInt = -1;
		for (int i = from; i < to; i++) {
			int x = i + (int) stones.GetJumpLength(i);
			if (x >= n) {
				x = n;
			}
			bestHereInt = Math.max(bestHereInt, x);
			bestHere[i - from] = bestHereInt;
		}
		
		int bestPrev = -1;
		if (ID > 0) {
			message.Receive(ID - 1);
			bestPrev = message.GetInt(ID - 1);
		}
		
		bestHereInt = Math.max(bestHereInt, bestPrev);
		if (ID + 1 < NODES) {
			message.PutInt(ID + 1, bestHereInt);
			message.Send(ID + 1);
		}
		
		for (int i = 0; i < len; i++) {
			bestHere[i] = Math.max(bestHere[i], bestPrev);
		}
		
		int[] ops = new int[len];
		int[] where = new int[len];
		for (int i = len - 1; i >= 0; i--) {
			if (bestHere[i] >= to) {
				ops[i] = 1;
				where[i] = bestHere[i];
				continue;
			}
			ops[i] = ops[bestHere[i] - from] + 1;
			where[i] = where[bestHere[i] - from];
		}
		
		int x = 0;
		int ans = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			x = message.GetInt(ID - 1);
			ans = message.GetInt(ID - 1);
		}
		
		if (x < to) {
			ans += ops[x - from];
			x = where[x - from];
		}
		
		if (ID + 1 < NODES) {
			message.PutInt(ID + 1, x);
			message.PutInt(ID + 1, ans);
			message.Send(ID + 1);
			return null;
		}
		return "" + ans;
	}

	static int NODES;
	static int ID;

	/**
	 * Arguments:
	 * ""	= as for submit
	 * "0"	= run multi node infrastructure
	 * "1"	= run single node
	 */
	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		boolean single = args.length == 1 && args[0].equals("1");
		NODES = single ? 1 : message.NumberOfNodes();
		ID = single ? 0 : message.MyNodeId();
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

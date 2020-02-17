package dgcj.y2016.round1;

import dgcj.message;

public class B {
	static final Object PROBLEM = new rps(); // PROBLEM NAME goes here
	static final String GAME = "PRS";
	static final int BEAT = 1;
	int[] a;

	public String run() {
		int n = (int) rps.GetN();
		int m = 1 << n;
		
		int nodes = Integer.highestOneBit(NODES);
		nodes = Math.min(nodes, m);
		
		if (ID < nodes) {
			int from = (int) (1L * m * ID / nodes);
			int to = (int) (1L * m * (ID + 1) / nodes);
			a = new int[to - from];
			for (int i = from; i < to; i++) {
				a[i - from] = GAME.indexOf(rps.GetFavoriteMove(i));
			}
			int res = play();
			message.PutInt(NODES - 1, from + res);
			message.PutInt(NODES - 1, a[res]);
			message.Send(NODES - 1);
		}
		if (ID < NODES - 1) {
			return null;
		}
		a = new int[nodes];
		int[] num = new int[nodes];
		for (int i = 0; i < nodes; i++) {
			message.Receive(i);
			num[i] = message.GetInt(i);
			a[i] = message.GetInt(i);
		}
		int res = play();
		return "" + num[res];

	}
	
	int play() {
		return play(0, a.length);
	}

	int play(int from, int to) {
		if (to - from == 1) {
			return from;
		}
		int mid = (from + to) / 2;
		int p = play(from, mid);
		int q = play(mid, to);
		if (a[p] == a[q] || (a[p] + BEAT) % 3 == a[q]) {
			return p;
		}
		return q;
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

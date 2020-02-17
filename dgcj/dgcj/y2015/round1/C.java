package dgcj.y2015.round1;

import dgcj.message;

public class C {
	static final Object PROBLEM = new johnny(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) johnny.NumberOfCards();
		int from = n * ID / NODES;
		int to = n * (ID + 1) / NODES;
		for (int i = from; i < to; i++) {
			int deg = 0;
			for (int j = 0; j < n; j++) {
				if (johnny.IsBetter(i, j)) {
					deg++;
				}
			}
			message.PutInt(0, deg);
		}
		message.Send(0);
		if (ID > 0) {
			return null;
		}
		
		int[] amount = new int[n];
		for (int id = 0; id < NODES; id++) {
			int low = n * id / NODES;
			int high = n * (id + 1) / NODES;
			message.Receive(id);
			for (int i = low; i < high; i++) {
				int deg = message.GetInt(id);
				amount[deg]++;
			}
		}
		
		int size = 0;
		int out = 0;
		for (int deg = 0; deg < n; deg++) {
			size += amount[deg];
			out += amount[deg] * deg;
			if (size > 0 && size < n && out == size * (size - 1) / 2) {
				return n - size + "";
			}
		}
		return "IMPOSSIBLE";
	}
	
	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

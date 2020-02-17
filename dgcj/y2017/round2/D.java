package dgcj.y2017.round2;

import dgcj.message;
import java.util.*;

public class D {
	static final Object PROBLEM = new nanobots(); // PROBLEM NAME goes here
	static final int M = 1000000007;
	static final int WALLS_DESIRED = 1 << 16;
	
	long m;

	public String run() {
		m = nanobots.GetRange();
		
		Random rnd = new Random(566);
		TreeSet<Long> wallsSet = new TreeSet<>();
		wallsSet.add(0L);
		wallsSet.add(m);
		for (int i = 0; i < WALLS_DESIRED; i++) {
			wallsSet.add((rnd.nextLong() % m + m) % m);
		}
		long[] walls = new long[wallsSet.size()];
		int[] who = new int[walls.length];
		{
			int i = 0;
			for (long x : wallsSet) {
				walls[i] = x;
				who[i] = rnd.nextInt(NODES);
				i++;
			}
		}
		
		long ans = 0;
		for (int k = 0; k < walls.length - 1; k++) {
			if (who[k] != ID) {
				continue;
			}
			ans += calc(walls[k], walls[k + 1]);
			ans %= M;
		}
		
		if (ID > 0) {
			message.PutLL(0, ans);
			message.Send(0);
			return null;
		}

		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			ans += message.GetLL(id);
			ans %= M;
		}
		return "" + ans;
	}

	long calc(long from, long to) {
		long y1 = searchY(from);
		long y2 = searchY(to - 1, -1, y1);
		return calc(from, to - 1, y1, y2);
	}
	
	long calc(long x1, long x2, long y1, long y2) {
		if (y1 == y2) {
			return y1 % M * ((x2 - x1 + 1) % M) % M;
		}
		if (x1 + 1 == x2) {
			return (y1 + y2) % M;
		}
		long mid = (x1 + x2) / 2;
		long yc = searchY(mid, y2 - 1, y1);
		long res = -yc;
		res += calc(x1, mid, y1, yc);
		res += calc(mid, x2, yc, y2);
		return (res % M + M) % M;
	}

	long searchY(long x) {
		return searchY(x, -1, m);
	}
	
	long searchY(long x, long low, long high) {
		while (low + 1 < high) {
			long mid = (low + high) / 2;
			if (exam(x, mid)) {
				low = mid;
			} else {
				high = mid;
			}
		}
		return high;
	}
	
	boolean exam(long x, long y) {
		return nanobots.Experiment(x + 1, y + 1) == 'T';
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new D().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

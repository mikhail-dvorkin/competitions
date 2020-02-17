package dgcj.y2016.finals;

import dgcj.message;
import java.util.*;

public class D_wip {
	static final Object PROBLEM = new gold(); // PROBLEM NAME goes here
	static final int WALLS_DESIRED = 1 << 16;

	public String run() {
		long road = gold.RoadLength();
		
		Random rnd = new Random(566);
		TreeSet<Long> wallsSet = new TreeSet<>();
		wallsSet.add(0L);
		wallsSet.add(road);
		for (int i = 0; i < WALLS_DESIRED; i++) {
			wallsSet.add((rnd.nextLong() % road + road) % road);
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
			ans ^= calc(walls[k], walls[k + 1]);
		}
		
		if (ID > 0) {
			message.PutLL(0, ans);
			message.Send(0);
			return null;
		}

		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			ans ^= message.GetLL(id);
		}
		return "" + ans;
	}

	long calc(long from, long to) {
		if (from == to) {
			return 0;
		}
		char left = gold.Search(from);
		if (left == 'X') {
			return from ^ calc(from + 1, to);
		}
		if (left == '=') {
			return calc(from + 1, to);
		}
		char right = gold.Search(to - 1);
		if (right == 'X') {
			return (to - 1) ^ calc(from, to - 1);
		}
		if (right == '=') {
			return calc(from, to - 1);
		}
		
		if (left == '>' && right == '<') {
			// rewrite?
			long center = (from + to) / 2;
			long res = (gold.Search(center) == 'X') ? center : 0;
			return res ^ calc(from, center) ^ calc(center + 1, to);
		}
		
		if (left == '<') {
			long x = from;
			long step = 1;
			for (;;) {
				x += step;
				if (x >= to) {
					return 0;
				}
				char c = gold.Search(x);
				long res = (c == 'X') ? x : 0;
				if (c != '<') {
					return res ^ calc(x + 1, to);
				}
				step *= 2;
			}
		}
		
		long x = to - 1;
		long step = 1;
		for (;;) {
			x -= step;
			if (x < from) {
				return 0;
			}
			char c = gold.Search(x);
			long res = (c == 'X') ? x : 0;
			if (c != '>') {
				return res ^ calc(from, x);
			}
			step *= 2;
		}
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new D_wip().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

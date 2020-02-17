package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class C_map {
	static final Object PROBLEM = new shhhh(); // PROBLEM NAME goes here

	public String run() {
		int prevId = (ID + NODES - 1) % NODES;
		int nextId = (ID + 1) % NODES;
		int n = (int) shhhh.GetN();
		int step = n / NODES / 4 + 10;
		Map<Integer, Integer> index = new HashMap<>();
		int a = (ID == 0) ? 0 : new Random(ID + 566).nextInt(n);
		index.put(a, 0);
		boolean init = (ID == 0);
		int ans = -1;
		for (;;) {
			for (int i = 0; i < step; i++) {
				int b = (int) shhhh.GetRightNeighbour(a);
				if (index.containsKey(b)) {
					break;
				}
				a = b;
				index.put(a, index.size());
			}
			System.err.println(index.size());
			int v, dist;
			if (init) {
				v = 0;
				dist = 0;
				init = false;
			} else {
				message.Receive(prevId);
				v = message.GetInt(prevId);
				dist = message.GetInt(prevId);
			}
			if (ans != -1) {
				break;
			}
			if (v != 1 && index.containsKey(v)) {
				int j = index.get(v);
				if (index.containsKey(1) && j < index.get(1)) {
					v = 1;
					dist += index.get(1) - j;
					ans = dist;
				} else {
					v = a;
					dist += index.get(a) - j;
				}
			}
			message.PutInt(nextId, v);
			message.PutInt(nextId, dist);
			message.Send(nextId);
			if (v == 1 && ans == -1) {
				break;
			}
		}
		if (ans != -1) {
			if (ans * 2 < n) {
				return "RIGHT " + ans;
			} else if (ans * 2 > n) {
				return "LEFT " + (n - ans);
			} else {
				return "WHATEVER " + ans;
			}
		}
		return null;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C_map().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

package dgcj.y2017.round1;

import java.util.*;
import dgcj.message;

public class D_worse {
	static final Object PROBLEM = new query_of_death(); // PROBLEM NAME goes here

	public String run() {
		if (ID >= NODES) {
			return null;
		}
		int n = (int) query_of_death.GetLength();
		
		int from = 0;
		int to = n;
		ArrayList<Integer> alive = new ArrayList<>();
		for (int id = 0; id < NODES; id++) {
			alive.add(id);
		}
		
		int MAIN = 0;
		while (from + 1 < to) {
			int num = alive.indexOf(ID);
			int numPair = num / 2;
			int pairs = alive.size() / 2;
			int low = (int) (from + 1L * (to - from) * numPair / pairs);
			int high = (int) (from + 1L * (to - from) * (numPair + 1) / pairs);
			int found = 0;
			if (num >= 0 && low < high) {
				long hash = 0;
				for (int i = low; i < high; i++) {
					hash = 3 * hash + (int) query_of_death.GetValue(i);
				}
				for (int i = 0; i < 64; i++) {
					hash = 3 * hash + (int) query_of_death.GetValue(low + i % (high - low));
				}
				message.PutLL(ID ^ 1, hash);
				message.Send(ID ^ 1);
				message.Receive(ID ^ 1);
				long hashThat = message.GetLL(ID ^ 1);
				found = hash == hashThat ? 0 : 1;
			}
			
			message.PutInt(MAIN, found);
			message.Send(MAIN);
			
			if (ID == MAIN) {
				int dead = -1;
				for (int id = 0; id < NODES; id++) {
					message.Receive(id);
					if (message.GetInt(id) != 0) {
						dead = id;
					}
				}
				numPair = alive.indexOf(dead) / 2;
				low = (int) (from + 1L * (to - from) * numPair / pairs);
				high = (int) (from + 1L * (to - from) * (numPair + 1) / pairs);
				from = low;
				to = high;
				for (int id = 0; id < NODES; id++) {
					message.PutInt(id, from);
					message.PutInt(id, to);
					message.PutInt(id, dead);
					message.Send(id);
				}
			}
			
			message.Receive(MAIN);
			from = message.GetInt(MAIN);
			to = message.GetInt(MAIN);
			int dead = message.GetInt(MAIN);
			alive.remove((Integer) (dead));
			alive.remove((Integer) (dead ^ 1));
			
			MAIN++;
			MAIN = MAIN % NODES;
		}
		
		int num = alive.indexOf(ID);
		int sum = 0;
		if (num >= 0) {
			int low = (int) (1L * n * num / alive.size());
			int high = (int) (1L * n * (num + 1) / alive.size());
			boolean todo = false;
			for (int i = low; i < high; i++) {
				if (i == from) {
					todo = true;
					continue;
				}
				sum += (int) query_of_death.GetValue(i);
			}
			if (todo) {
				sum += (int) query_of_death.GetValue(from);
			}
		}
		message.PutInt(MAIN, sum);
		message.Send(MAIN);
		if (ID != MAIN) {
			return null;
		}

		int ans = 0;
		for (int id = 0; id < NODES; id++) {
			message.Receive(id);
			ans += message.GetInt(id);
		}

		return "" + ans;
	}

	final int NODES = message.NumberOfNodes() / 2 * 2;
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new D_worse().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

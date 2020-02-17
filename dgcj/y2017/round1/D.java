package dgcj.y2017.round1;

import dgcj.message;
import java.util.*;

public class D {
	static final Object PROBLEM = new query_of_death(); // PROBLEM NAME goes here

	public String run() {
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
			int low = (int) (from + 1L * (to - from) * num / alive.size());
			int high = (int) (from + 1L * (to - from) * (num + 1) / alive.size());
			boolean found = false;
			if (num >= 0 && low < high) {
				for (int i = low; i < high; i++) {
					query_of_death.GetValue(i);
				}
				int v = (int) query_of_death.GetValue(low);
				for (int i = 0; i < 64; i++) {
					if (v != (int) query_of_death.GetValue(low)) {
						found = true;
					}
				}
			}
			
			message.PutInt(MAIN, found ? 1 : 0);
			message.Send(MAIN);
			
			if (ID == MAIN) {
				int dead = -1;
				for (int id = 0; id < NODES; id++) {
					message.Receive(id);
					if (message.GetInt(id) != 0) {
						dead = id;
					}
				}
				num = alive.indexOf(dead);
				low = (int) (from + 1L * (to - from) * num / alive.size());
				high = (int) (from + 1L * (to - from) * (num + 1) / alive.size());
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

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new D().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class C_msglimit {
	static final Object PROBLEM = new shhhh(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) shhhh.GetN();
		int path = 3 * n / NODES + 10;
		path = Math.min(path, n);
		int[] a = new int[path];
		Map<Integer, Integer> index = new TreeMap<>();
		boolean found = false;
		int foundIndex = -1;
		for (int i = 0; i < path; i++) {
			if (i == 0) {
				if (ID == 0) {
					a[i] = 0;
				} else {
					a[i] = new Random(ID + 566).nextInt(n);
				}
			} else {
				a[i] = (int) shhhh.GetRightNeighbour(a[i - 1]);
			}
			index.put(a[i], i);
			if (a[i] == 1) {
				found = true;
				foundIndex = i;
			}
		}
		if (ID > 0) {
			for (;;) {
				message.Receive(0);
				int f = message.GetInt(0);
				if (f == -1) {
					return null;
				}
				if (!index.containsKey(f)) {
					message.PutInt(0, -1);
					message.PutInt(0, -1);
					message.Send(0);
					continue;
				}
				int j = index.get(f);
				if (found && j <= foundIndex) {
					message.PutInt(0, foundIndex - j);
					message.PutInt(0, 1);
					message.Send(0);
					continue;
				}
				message.PutInt(0, path - 1 - foundIndex);
				message.PutInt(0, a[path - 1]);
				message.Send(0);
			}
		}
		int ans = -1;
		if (found) {
			ans = foundIndex;
		}
		int far = a[path - 1];
		int dist = path - 1;
		while (ans == -1) {
			for (int i = 1; i < NODES; i++) {
				message.PutInt(i, far);
				message.Send(i);
			}
			int bestDist = -1;
			int bestId = -1;
			for (int i = 1; i < NODES; i++) {
				message.Receive(i);
				int curDist = message.GetInt(i);
				int curId = message.GetInt(i);
				if (curId == 1) {
					ans = dist + curDist;
					continue;
				}
				if (curDist > bestDist) {
					bestDist = curDist;
					bestId = curId;
				}
			}
			if (ans != -1) {
				break;
			}
			far = bestId;
			dist += bestDist;
		}
		for (int i = 1; i < NODES; i++) {
			message.PutInt(i, -1);
			message.Send(i);
		}
		if (ans * 2 < n) {
			return ("RIGHT " + ans);
		}  if (ans * 2 > n) {
			return ("LEFT " + (n - ans));
		} else {
			return ("WHATEVER " + ans);
		}
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C_msglimit().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

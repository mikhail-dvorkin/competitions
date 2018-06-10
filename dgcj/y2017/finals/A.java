package dgcj.y2017.finals;

import java.util.*;
import dgcj.message;

public class A {
	static final Object PROBLEM = new baby_blocks(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) baby_blocks.GetNumberOfBlocks();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		long sum = 0;
		for (int i = from; i < to; i++) {
			int v = (int) baby_blocks.GetBlockWeight(i);
			sum += v;
		}

		message.PutLL(0, sum);
		message.Send(0);
		long[] cumSum = new long[NODES + 1];
		if (ID == 0) {
			for (int id = 0; id < NODES; id++) {
				message.Receive(id);
				cumSum[id + 1] = cumSum[id] + message.GetLL(id);
			}
			for (int id = 1; id < NODES; id++) {
				for (int i = 1; i <= NODES; i++) {
					message.PutLL(id, cumSum[i]);
				}
				message.Send(id);
			}
		} else {
			message.Receive(0);
			for (int i = 1; i <= NODES; i++) {
				cumSum[i] = message.GetLL(0);
			}
		}
		
		long total = cumSum[NODES];
		ArrayList<Long> affixes = new ArrayList<>();
		for (long v : cumSum) {
			affixes.add(Math.min(v, total - v));
		}
		affixes.add(total / 2);
		Collections.sort(affixes);
		affixes.remove(0);
		
		long low = affixes.get(ID);
		long high = affixes.get(ID + 1);
		
		int left = 0;
		long sumLeft = 0;
		while (sumLeft + cumSum[left + 1] - cumSum[left] <= low) {
			sumLeft += cumSum[left + 1] - cumSum[left];
			left++;
		}
		int right = NODES - 1;
		long sumRight = 0;
		while (sumRight + cumSum[right + 1] - cumSum[right] <= low) {
			sumRight += cumSum[right + 1] - cumSum[right];
			right--;
		}
		
		left = (int) (1L * n * left / NODES);
		right = (int) (1L * n * (right + 1) / NODES);
		int ans = 0;
		for (;;) {
			if (sumLeft == sumRight && sumLeft > low) {
				ans++;
			}
			if (sumLeft <= sumRight) {
				sumLeft += baby_blocks.GetBlockWeight(left);
				left++;
			} else {
				right--;
				sumRight += baby_blocks.GetBlockWeight(right); 
			}
			if (sumLeft > high || sumRight > high) {
				break;
			}
		}

		if (ID > 0) {
			message.PutInt(0, ans);
			message.Send(0);
			return null;
		}
		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			ans += message.GetInt(id);
		}
		return "" + ans;
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new A().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

package dgcj.y2016.round1;
import dgcj.message;
import java.util.*;

public class D {
	static final Object PROBLEM = new winning_move(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) winning_move.GetNumPlayers();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);

		Map<Long, Integer> countMap = new TreeMap<>();
		for (int i = from; i < to; i++) {
			long value = winning_move.GetSubmission(i);
			increase(countMap, value, 1);
		}
		for (Map.Entry<Long, Integer> entry : countMap.entrySet()) {
			long value = entry.getKey();
			int count = entry.getValue();
			int J = ((int) (fmix64(value) % NODES) + NODES) % NODES;
			message.PutInt(J, count);
			message.PutLL(J, value);
		}
		for (int J = 0; J < NODES; J++) {
			message.PutInt(J, 0);
			message.Send(J);
		}

		countMap.clear();
		for (int J = 0; J < NODES; J++) {
			message.Receive(J);
			for (;;) {
				int count = message.GetInt(J);
				if (count == 0) {
					break;
				}
				long value = message.GetLL(J);
				increase(countMap, value, count);
			}
		}

		long best = Long.MAX_VALUE;
		for (Map.Entry<Long, Integer> entry : countMap.entrySet()) {
			long value = entry.getKey();
			int count = entry.getValue();
			if (count > 1) {
				continue;
			}
			best = value;
			break;
		}

		if (ID > 0) {
			message.PutLL(0, best);
			message.Send(0);
			return null;
		}
		for (int J = 1; J < NODES; J++) {
			message.Receive(J);
			long value = message.GetLL(J);
			best = Math.min(best, value);
		}
		if (best == Long.MAX_VALUE) {
			best = 0;
		}
		return "" + best;
	}

	static void increase(Map<Long, Integer> countMap, long value, int plus) {
		if (countMap.containsKey(value)) {
			countMap.put(value, countMap.get(value) + plus);
		} else {
			countMap.put(value, plus);
		}
	}

	static long fmix64(long x) {
		x ^= x >>> 33;
		x *= 0xff51afd7ed558ccdL;
		x ^= x >>> 33;
		x *= 0xc4ceb9fe1a85ec53L;
		x ^= x >>> 33;
		return x;
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

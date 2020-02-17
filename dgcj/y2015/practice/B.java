package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class B {
	static final Object PROBLEM = new majority(); // PROBLEM NAME goes here
	static final int SAMPLE = 1024;

	public String run() {
		int n = (int) majority.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);

		Random rnd = new Random(566);
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < SAMPLE; i++) {
			int v = (int) majority.GetVote(rnd.nextInt(n));
			if (!map.containsKey(v)) {
				map.put(v, 1);
			} else {
				map.put(v, map.get(v) + 1);
			}
		}
		ArrayList<Integer> oftenList = new ArrayList<>();
		for (int v : map.keySet()) {
			if (map.get(v) > SAMPLE / 4) {
				oftenList.add(v);
			}
		}
		int[] often = new int[oftenList.size()];
		for (int i = 0; i < often.length; i++) {
			often[i] = oftenList.get(i);
		}

		int[] count = new int[often.length];
		for (int i = from; i < to; i++) {
			int v = (int) majority.GetVote(i);
			for (int j = 0; j < often.length; j++) {
				if (often[j] == v) {
					count[j]++;
				}
			}
		}

		if (ID > 0) {
			for (int j = 0; j < often.length; j++) {
				message.PutInt(0, count[j]);
			}
			message.Send(0);
			return null;
		}

		for (int i = 1; i < NODES; i++) {
			message.Receive(i);
			for (int j = 0; j < often.length; j++) {
				count[j] += message.GetInt(i);
			}
		}

		for (int j = 0; j < often.length; j++) {
			if (count[j] * 2 > n) {
				return "" + often[j];
			}
		}
		return "NO WINNER";
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new B().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

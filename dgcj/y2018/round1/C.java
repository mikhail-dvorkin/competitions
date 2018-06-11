package dgcj.y2018.round1;

import java.util.*;
import dgcj.message;

public class C {
	static final Object PROBLEM = new kenneth(); // PROBLEM NAME goes here
	
	final static int X = 566239;
	final static int M = 1000000007;

	public String run() {
		int[] lens = new int[NODES];
		int[] lenCum = new int[NODES + 1];
		for (int i = 0; i < NODES; i++) {
			lens[i] = (int) kenneth.GetPieceLength(i);
			lenCum[i + 1] = lenCum[i] + lens[i];
		}
		int from = lenCum[ID];
		int to = lenCum[ID + 1];
		int len = to - from;
		int total = lenCum[NODES];
		
		int[] h = new int[len + 1];
		int[] t = new int[len + 1];
		t[0] = 1;
		for (int i = 0; i < len; i++) {
			h[i + 1] = (int) ((h[i] * (long) X + kenneth.GetSignalCharacter(from + i)) % M);
			t[i + 1] = (int) ((t[i] * (long) X) % M);
		}
		
		int hPrev = 0;
		if (ID > 0) {
			message.Receive(ID - 1);
			hPrev = message.GetInt(ID - 1);
		}
		int hNext = (int) ((hPrev * (long) t[len] + h[len]) % M);
		if (ID + 1 < NODES) {
			message.PutInt(ID + 1, hNext);
			message.Send(ID + 1);
		}
		
		for (int i = 0; i <= len; i++) {
			h[i] = (int) ((h[i] + hPrev * (long) t[i]) % M);
		}
		
		TreeSet<Integer> pSet = new TreeSet<>();
		for (int p = 1; p * p <= total; p++) {
			if (total % p == 0) {
				pSet.add(p);
				pSet.add(total / p);
			}
		}

		TreeSet<Integer> queries = new TreeSet<>();
		for (int p : pSet) {
			queries.add(p);
			queries.add(total - p);
		}

		ArrayList<Integer> toSend = new ArrayList<>();
		for (int q : queries) {
			if (q < from || q >= to) {
				continue;
			}
			toSend.add(h[q - from]);
		}
		
		if (ID < NODES - 1) {
			message.PutInt(NODES - 1, toSend.size());
			for (int x : toSend) {
				message.PutInt(NODES - 1, x);
			}
			message.Send(NODES - 1);
			return null;
		}
		
		ArrayList<Integer> got = new ArrayList<>();
		for (int id = 0; id < NODES - 1; id++) {
			message.Receive(id);
			int count = message.GetInt(id);
			for (int i = 0; i < count; i++) {
				got.add(message.GetInt(id));
			}
		}
		got.addAll(toSend);
		ArrayList<Integer> queriesList = new ArrayList<Integer>(queries);
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 0; i < got.size(); i++) {
			map.put(queriesList.get(i), got.get(i));
		}
		map.put(0, 0);
		map.put(total, h[len]);
		
		int ans = total;
		for (int p : pSet) {
			int pw = power(total - p);
			int h1 = map.get(total - p);
			int h2 = (int) ((((h[len] - map.get(p) * (long) pw) % M) + M) % M);
			if (h1 == h2) {
				ans = Math.min(ans, p);
			}
		}
		return "" + ans;
	}

	static int power(int p) {
		if (p == 0)
			return 1;
		if (p == 1)
			return X;
		long v = power(p / 2);
		v = (v * v) % M;
		return (int) ((p & 1) == 0 ? v : (v * X) % M);
	}
	
	static int NODES;
	static int ID;

	/**
	 * EXECUTE with args:
	 * 0	= multi node run with infrastructure
	 * 1	= single node run
	 */
	public static void main(String[] args) {
		boolean single = args.length == 1 && args[0].equals("1");
		NODES = single ? 1 : message.NumberOfNodes();
		ID = single ? 0 : message.MyNodeId();
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

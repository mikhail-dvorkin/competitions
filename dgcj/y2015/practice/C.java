package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class C {
	static final Object PROBLEM = new shhhh(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) shhhh.GetN();
		int specials = Math.min(64 * NODES + 10, n);

		Random rnd = new Random(566);
		TreeSet<Integer> specialSet = new TreeSet<>();
		specialSet.add(0);
		specialSet.add(1);
		while (specialSet.size() < specials) {
			specialSet.add(rnd.nextInt(n));
		}
		int[] special = new int[specials];
		{
			int i = 0;
			for (int x : specialSet) {
				special[i++] = x;
			}
		}

		int from = specials * ID / NODES;
		int to = specials * (ID + 1) / NODES;
		int[] next = new int[specials];
		int[] dist = new int[specials];
		for (int k = from; k < to; k++) {
			int x = special[k];
			int i = 0;
			for (;;) {
				i++;
				x = (int) shhhh.GetRightNeighbour(x);
				int j = Arrays.binarySearch(special, x);
				if (j >= 0) {
					next[k] = j;
					dist[k] = i;
					break;
				}
			}
		}

		if (ID > 0) {
			for (int k = from; k < to; k++) {
				message.PutInt(0, next[k]);
				message.PutInt(0, dist[k]);
			}
			message.Send(0);
			return null;
		}

		for (int id = 1; id < NODES; id++) {
			from = specials * id / NODES;
			to = specials * (id + 1) / NODES;
			message.Receive(id);
			for (int k = from; k < to; k++) {
				next[k] = message.GetInt(id);
				dist[k] = message.GetInt(id);
			}
		}

		int x = 0;
		int d = 0;
		while (x != 1) {
			d += dist[x];
			x = next[x];
		}
		if (d * 2 < n) {
			return "RIGHT " + d;
		} else if (d * 2 > n) {
			return "LEFT " + (n - d);
		} else {
			return "WHATEVER " + d;
		}
	}

	static long[] index = new long[]{Long.MAX_VALUE / 3};
	static int size = 1;

	static void register(int[] keys) {
		int j = index.length;
		index = Arrays.copyOf(index, index.length + keys.length);
		for (int i = 0; i < keys.length; i++) {
			long val = ((long) keys[i] << 32L) | (size - 1 + i);
			index[j + i] = val;
		}
		Arrays.sort(index);
		size = index.length;
	}

	static int get(int key) {
		long k = ((long) key << 32L) - 1;
		int p = - 1 - Arrays.binarySearch(index, 0, size, k);
		long a = index[p];
		if ((a >> 32L) != key) {
			return -1;
		}
		return (int) a;
	}

	static int size() {
		return size - 1;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();

	public static void main(String[] args) {
		PROBLEM.equals(args); // Local testing framework invocation
		String ans = new C().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

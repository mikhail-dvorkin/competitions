package dgcj.y2015.practice;

import dgcj.message;
import java.util.*;

public class C_tl {
	static final Object PROBLEM = new shhhh(); // PROBLEM NAME goes here

	public String run() {
		int prevId = (ID + NODES - 1) % NODES;
		int nextId = (ID + 1) % NODES;
		int n = (int) shhhh.GetN();
		int step = 2 * n / NODES + 10;
		int a = (ID == 0) ? 0 : new Random(ID + 566).nextInt(n);
		register(new int[]{a});
		boolean init = (ID == 0);
		int ans = -1;
		for (;;) {
			{
				int[] keys = new int[Math.min(step, n - size())];
				for (int i = 0; i < keys.length; i++) {
					a = (int) shhhh.GetRightNeighbour(a);
					keys[i] = a;
				}
				register(keys);
			}
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
			int getV = get(v);
			if (v != 1 && getV != -1) {
				int get1 = get(1);
				if (get1 != -1 && getV < get1) {
					v = 1;
					dist += get1 - getV;
					ans = dist;
				} else {
					v = a;
					dist += get(a) - getV;
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
		String ans = new C_tl().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}
}

package dgcj.y2017.finals;

import java.util.*;
import dgcj.message;

public class C_wip {
	static final Object PROBLEM = new median(); // PROBLEM NAME goes here

	public String run() {
		int shift = 1000_000;
		int samples = 1000;
		long[] hash = new long[samples];
		if (ID == 0) {
			for (int i = 0; i < samples; i++) {
				hash[i] = hash(i * shift);
			}
		} else {
			message.Receive(ID - 1);
			for (int i = 0; i < samples; i++) {
				hash[i] = message.GetLL(ID - 1);
			}
		}
		if (ID + 1 < NODES) {
			for (int i = 0; i < samples; i++) {
				message.PutLL(ID + 1, hash[i]);
			}
			message.Send(ID + 1);
		}
		HashMap<Long, Integer> hashMap = new HashMap<>();
		for (int k = 0; k < samples; k++) {
			hashMap.put(hash[k], k);
		}
		
		long h = hash(0);
		for (int i = 0;; i++) {
			if (hashMap.containsKey(h)) {
				int k = hashMap.get(h);
				if (ID > 0) {
					message.PutInt(0, i);
					message.PutInt(0, k);
					message.Send(0);
					break;
				}
				if (i == k * shift) {
					continue;
				}
			}
			h = hashMove(h, i);
		}
		if (ID == 0) {
			int[] delta = new int[NODES];
			for (int id = 1; id < NODES; id++) {
				message.Receive(id);
				int i = message.GetInt(id);
				int k = message.GetInt(id);
				delta[id] = k * shift + i;
			}
			/*****/return "" + Arrays.toString(delta);
		}
		/*****/if (ID > 0) return null;
		
		int n = (int) median.GetN();
		int from = (int) (1L * n * ID / NODES);
		int to = (int) (1L * n * (ID + 1) / NODES);
		
		int low = 1;
		int high = (int) (1e9 + 1);
		int bins = 1000;
		int desired = n / 2;
		while (low + 1 < high) {
			int binSize = (high - low) / bins;
			int[] size = new int[bins];
			for (int i = from; i < to; i++) {
				int v = median.GetData(i);
				if (v < low || v >= high) {
					continue;
				}
				int bin = (v - low) / binSize;
				size[bin]++;
			}
			
			if (ID > 0) {
				for (int i = 0; i < bins; i++) {
					message.PutInt(0, size[i]);
				}
				message.Send(0);
				message.Receive(ID - 1);
				low = message.GetInt(ID - 1);
			} else {
				for (int id = 1; id < NODES; id++) {
					message.Receive(id);
					for (int i = 0; i < bins; i++) {
						size[i] += message.GetInt(id);
					}
				}
				int i = 0;
				while (desired >= size[i]) {
					desired -= size[i];
					i++;
				}
				low += i * binSize;
			}
			
			high = low + binSize;
			if (ID + 1 < NODES) {
				message.PutInt(ID + 1, low);
				message.Send(ID + 1);
			}
		}
		
		if (ID == 0) {
			return "" + low;
		}
		return null;
	}
	
	static final int P = 566239;
	static final int W = 1000;
	static final int Q;
	static {
		int q = 1;
		for (int i = 0; i < W; i++) {
			q *= P;
		}
		Q = q;
	}
	
	long hash(int from) {
		long hash = 0;
		for (int i = 0; i < W; i++) {
			hash = hash * P + median.GetData(from + i);
		}
		return hash;
	}
	
	long hashMove(long hash, int from) {
		hash = hash * P + median.GetData(from + W);
		hash -= median.GetData(from) * Q;
		return hash;
	}

	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new C_wip().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

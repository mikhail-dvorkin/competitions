package dgcj.y2017.round2;

import dgcj.message;
import java.util.*;

public class C_slow {
	static final Object PROBLEM = new broken_memory(); // PROBLEM NAME goes here
	int M = 0x85ebca6b;

	public String run() {
		int n = (int) broken_memory.GetLength();
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			int j = Math.abs(i * M) % (i + 1);
			p[i] = p[j];
			p[j] = i;
		}
//		log(Arrays.toString(p));
//		int[] q = new int[n];
//		for (int i = 0; i < n; i++) {
//			q[p[i]] = i;
//		}
		long[] hash = new long[24];
		for (int i = 0; i < n; i++) {
			int pi = p[i];
			long v = mix(broken_memory.GetValue(pi));
			v = mix(M * v + pi);
			int ii = i;
			for (int j = 0; j < hash.length; j++) {
				if ((ii & 1) != 0) {
					hash[j] += v;
				}
				ii >>= 1;
			}
		}
//		log(Arrays.toString(hash));
		if (ID > 0) {
			for (int j = 0; j < hash.length; j++) {
				message.PutLL(0, hash[j]);
			}
			message.Send(0);
			return null;
		}
		long[][] h = new long[NODES][hash.length];
		h[0] = hash;
		for (int id = 1; id < NODES; id++) {
			message.Receive(id);
			for (int j = 0; j < hash.length; j++) {
				h[id][j] = message.GetLL(id);
			}
		}
		long[] maj = new long[hash.length];
		for (int j = 0; j < hash.length; j++) {
			Map<Long, Integer> map = new TreeMap<>();
			for (int i = 0; i < NODES; i++) {
				long v = h[i][j];
				Integer c = map.get(v);
				if (c == null) c = 0;
				map.put(v, c + 1);
			}
			long best = map.keySet().iterator().next();
			for (long v : map.keySet()) {
				if (map.get(v) >= map.get(best)) {
					best = v;
				}
			}
//			log("" + map);
			maj[j] = best;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < NODES; i++) {
//			log(Arrays.toString(h[i]));
			int pos = 0;
			for (int j = 0; j < hash.length; j++) {
				if (h[i][j] != maj[j]) {
					pos |= 1 << j;
				}
			}
			pos = p[pos];
			sb.append(" " + pos);
		}
		return sb.toString().trim();
	}

	int mix(int x) {
		x ^= x >>> 16;
		x *= 0x85ebca6b;
		x ^= x >>> 13;
		x *= 0xc2b2ae35;
		x ^= x >>> 16;
		return x;
	}
	
	long mix(long x) {
		x ^= x >>> 33;
		x *= 0xff51afd7ed558ccdL;
		x ^= x >>> 33;
		x *= 0xc4ceb9fe1a85ec53L;
		x ^= x >>> 33;
		return x;
	}

	
	final static boolean SINGLE = false;
	final int NODES = SINGLE ? 1 : message.NumberOfNodes();
	final int ID = SINGLE ? 0 : message.MyNodeId();

	// EXECUTE with non-empty args
	public static void main(String[] args) {
		if (!SINGLE) {
			PROBLEM.equals(args); // Local testing framework invocation
		}
		String ans = new C_slow().run();
		if (ans != null) {
			System.out.println(ans);
		}
	}

	public void log(String msg) {
		PROBLEM.equals(ID + ": " + msg); // Local testing framework log
	}
}

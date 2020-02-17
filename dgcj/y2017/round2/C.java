package dgcj.y2017.round2;

import dgcj.message;
import java.util.*;

public class C {
	static final Object PROBLEM = new broken_memory(); // PROBLEM NAME goes here

	public String run() {
		int n = (int) broken_memory.GetLength();

		long[] cum = new long[n + 1];
		for (int i = 0; i < n; i++) {
			long x = mix(broken_memory.GetValue(i));
			x = mix(x + i);
			cum[i + 1] = (cum[i] + x);
		}

		ArrayList<Segment> segments = new ArrayList<>();
		segments.add(new Segment(0, n));

		do {
			ArrayList<Segment> next = new ArrayList<>();
			for (Segment segment : segments) {
				if (segment.length() > 1) {
					int m = (segment.from + segment.to) / 2;
					next.add(new Segment(segment.from, m));
					next.add(new Segment(m, segment.to));
				} else {
					next.add(segment);
				}
			}
			for (Segment segment : next) {
				message.PutLL(P, segment.sum(cum));
			}
			message.Send(P);
			message.Receive(P);
			segments.clear();
			for (Segment segment : next) {
				long s = message.GetLL(P);
				if (s != segment.sum(cum)) {
					segments.add(segment);
				}
			}
		} while (segments.size() != 2 || segments.get(0).length() != 1 || segments.get(1).length() != 1);

		int ans = -1;
		for (int i = 0; i < 2; i++) {
			message.PutInt(RIGHT, segments.get(i).from);
			message.Send(RIGHT);
			message.Receive(LEFT);
			int x = message.GetInt(LEFT);
			long v = new Segment(x, x + 1).sum(cum);
			message.PutLL(LEFT, v);
			message.Send(LEFT);
			message.Receive(RIGHT);
			v = message.GetLL(RIGHT);
			if (v != segments.get(i).sum(cum)) {
				ans = segments.get(i).from;
			}
		}

		message.PutInt(0, ans);
		message.Send(0);

		if (ID > 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (int id = 0; id < NODES; id++) {
			message.Receive(id);
			sb.append(message.GetInt(id)).append(" ");
		}
		return sb.toString().trim();
	}

	static class Segment {
		int from, to;

		public Segment(int from, int to) {
			this.from = from;
			this.to = to;
		}

		public int length() {
			return to - from;
		}

		public long sum(long[] cum) {
			return cum[to] - cum[from];
		}
	}

	static long mix(long x) {
		x ^= x >>> 33;
		x *= 0xff51afd7ed558ccdL;
		x ^= x >>> 33;
		x *= 0xc4ceb9fe1a85ec53L;
		x ^= x >>> 33;
		return x;
	}

	final int NODES = message.NumberOfNodes();
	final int ID = message.MyNodeId();
	final int P = ID ^ 1;
	final int RIGHT = (ID + 2) % NODES;
	final int LEFT = (ID + NODES - 2) % NODES;

	// EXECUTE with non-empty args
	public static void main(String[] args) {
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
